package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSSS;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerClosed;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerOpen;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *               This is the Drive code
 *    Forwards, backwards, and sideways
 *    Point A to Point B
 */
public class Drive extends Subsystem {

	// DRIVE_MODE sets the default command
	// 	0 = Drive, open loop
	//  1 = Drive, closed loop, tank controls
	//  2 = Dirve, closed loop, SSS controls
	public static final int DRIVE_MODE = 1;

	public static final double CYCLES_PER_METER = 1400.0;
	public static final double CYCLES_PER_10DEGREES = 250.0;
	public static final double CYCLES_PER_90DEGREES = 1400.0;

	private final static double FAST_SPEED = 300.0;
	private final static double SLOW_SPEED = 150.0;

	private final static int SUSPENSION_TIMEOUT = 15;
	private static final double ANGLE_MULTIPLIER = 0.05;

	// Speed PID constants
	public final static double SPEED_P = 1.0;
	private final static double SPEED_I = 0.002;
	private final static double SPEED_D = 0.0;
	private final static double SPEED_F = 0.0;

	private final static int SPEED_PROFILE = 0;
	private final static int SPEED_IZONE = 0;
	private final static double SPEED_RAMPRATE = 36.0;

	// Position PID constants 
	public final static double POSITION_P = Wiring.practiceRobot ? 0.3 : 0.2;
	private final static double POSITION_I = 0.0;
	private final static double POSITION_D = 0.0;
	private final static double POSITION_F = 0.0;
	private final static int POSITION_PROFILE = 1;
	private final static int POSITION_IZONE = 0;
	private final static double POSITION_RAMPRATE = 0.05;

	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave, mTalon;
	private final Ultrasonic lUltrasonic, rUltrasonic;
	private AHRS imu; 
	private SerialPort serial_port;
	private CANTalon.ControlMode controlMode;
	private double maxSpeed;
	private DoubleSolenoid suspension;
	private boolean isSuspensionDown = false;
	private int middleStillCount = 0;

	public Drive() {
		lTalonMaster = new CANTalon(Wiring.leftMotorController);
		rTalonMaster = new CANTalon(Wiring.rightMotorController);
		lTalonSlave = new CANTalon(Wiring.leftMotorController2);
		rTalonSlave = new CANTalon(Wiring.rightMotorController2);
		mTalon = new CANTalon(Wiring.middleMotorController);
		suspension = new DoubleSolenoid(Wiring.suspensionSolenoidDown, Wiring.suspensionSolenoidUp);
		
		// set up drive masters
		lTalonMaster.setPID(SPEED_P, SPEED_I, SPEED_D, SPEED_F, SPEED_IZONE, SPEED_RAMPRATE, SPEED_PROFILE);
		lTalonMaster.setPID(POSITION_P, POSITION_I, POSITION_D, POSITION_F, POSITION_IZONE, POSITION_RAMPRATE, POSITION_PROFILE);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		lTalonMaster.reverseSensor(false);
		lTalonMaster.reverseOutput(false);

		rTalonMaster.setPID(SPEED_P, SPEED_I, SPEED_D, SPEED_F, SPEED_IZONE, SPEED_RAMPRATE, SPEED_PROFILE);
		rTalonMaster.setPID(POSITION_P, POSITION_I, POSITION_D, POSITION_F, POSITION_IZONE, POSITION_RAMPRATE, POSITION_PROFILE);
		rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rTalonMaster.reverseSensor(true);
		rTalonMaster.reverseOutput(true);

		// set up drive slaves
		lTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
		lTalonSlave.set(lTalonMaster.getDeviceID());

		rTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

		// set up ultrasonics
		lUltrasonic = new Ultrasonic(Wiring.leftUltrasonicPing, Wiring.leftUltrasonicEcho);
		rUltrasonic = new Ultrasonic(Wiring.rightUltrasonicPing, Wiring.rightUltrasonicEcho);
		lUltrasonic.setAutomaticMode(true);
		rUltrasonic.setAutomaticMode(true);
		lUltrasonic.setEnabled(true);
		rUltrasonic.setEnabled(true);
		
		// set up NavX
		try {
	    	// Use SerialPort.Port.kMXP if connecting navX MXP to the RoboRio MXP port
	    	serial_port = new SerialPort(57600,SerialPort.Port.kMXP);
			
			// You can add a second parameter to modify the 
			// update rate (in hz) from.  The minimum is 4.  
	    	// The maximum (and the default) is 100 on a nav6, 60 on a navX MXP.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.
	    	// The recommended maximum update rate is 50Hz
			
			// You can also use the IMUAdvanced class for advanced
			// features on a nav6 or a navX MXP.
	    	
	    	// You can also use the AHRS class for advanced features on 
	    	// a navX MXP.  This offers superior performance to the
	    	// IMU Advanced class, and also access to 9-axis headings
	    	// and magnetic disturbance detection.  This class also offers
	    	// access to altitude/barometric pressure data from a
	    	// navX MXP Aero.
			
			byte update_rate_hz = 50;
			//imu = new IMU(serial_port,update_rate_hz);
			//imu = new IMUAdvanced(serial_port,update_rate_hz);
			imu = new AHRS(serial_port,update_rate_hz);
    	} catch( Exception ex ) {
    		
    	}
        if ( imu != null ) {
            LiveWindow.addSensor("IMU", "Gyro", imu);
        }

        // When calibration has completed, zero the yaw
        // Calibration is complete approaximately 20 seconds
        // after the robot is powered on.  During calibration,
        // the robot should be still
        
		double timeout = 0;
        while (imu.isCalibrating() && timeout++ < 200) {
            Timer.delay( 0.3 );
        }
        
        imu.zeroYaw();
		
		maxSpeed = FAST_SPEED;
		resetEncoders();
		setClosedLoopSpeed();
	}

	public void driveMove(double left, double right, double middle) {
		if (middle == 0.0) {
			if (isSuspensionDown) {
				if (middleStillCount++ > SUSPENSION_TIMEOUT) {
					suspension.set(Value.kReverse);
					isSuspensionDown = false;
				}
			}
		} else {
			if(!isSuspensionDown) {
				suspension.set(Value.kForward);
				isSuspensionDown = true;
			} 
			middleStillCount = 0;
		}
		mTalon.set(middle);

		switch (controlMode) {
		case PercentVbus:
		case Position:
			lTalonMaster.set(left);
			rTalonMaster.set(right);
			break;

		case Speed:
			lTalonMaster.set(left * maxSpeed);
			rTalonMaster.set(right * maxSpeed);
			break;

		default:
			break;
		}

		updateSmartDashboard();
	}

	public void driveMoveSteadyStrafe(double left, double right, double middle) {
		if (middle == 0.0) {
			if (isSuspensionDown) {
				if (middleStillCount++ > SUSPENSION_TIMEOUT) {
					suspension.set(Value.kReverse);
					isSuspensionDown = false;
					setClosedLoopSpeed();
				}
			}
		} else {
			if(!isSuspensionDown) {
				suspension.set(Value.kForward);
				isSuspensionDown = true;
				resetEncoders();
				setClosedLoopPosition();
				lTalonMaster.set(0.0);
				rTalonMaster.set(0.0);
			} 
			middleStillCount = 0;
		}
		mTalon.set(middle);

		if (!isSuspensionDown) {
			switch (controlMode) {
			case PercentVbus:
			case Position:
				lTalonMaster.set(left);
				rTalonMaster.set(right);
				break;

			case Speed:
				lTalonMaster.set(left * maxSpeed);
				rTalonMaster.set(right * maxSpeed);
				break;

			default:
				break;
			}
		}

		updateSmartDashboard();
	}

	public void driveMoveSteadyStrafeNavX(double left, double right, double middle) {
		double angle;
		double scale;
		
		if (middle == 0.0) {
			if (isSuspensionDown) {
				if (middleStillCount++ > SUSPENSION_TIMEOUT) {
					suspension.set(Value.kReverse);
					isSuspensionDown = false;
				}
			}
		} else {
			if(!isSuspensionDown) {
				suspension.set(Value.kForward);
				zeroYaw();
				isSuspensionDown = true;
			} 
			middleStillCount = 0;
		}
		mTalon.set(middle);

		if (isSuspensionDown) {
			angle = ANGLE_MULTIPLIER * getYaw();
			left = - angle;
			right = angle;
			
	        // scale values of left and right so they are between -1.0 and 1.0
	        if ((Math.abs(left) > 1.0) || (Math.abs(right) > 1.0)) {
	            if (Math.abs(left) > Math.abs(right)) {
	                scale = 1.0 / Math.abs(left);
	            } else {
	                scale = 1.0 / Math.abs(right);
	            }
	            left *= scale;
	            right *= scale;
	        }
	        
			lTalonMaster.set(left * maxSpeed);
			rTalonMaster.set(right * maxSpeed);
		} else {
			switch (controlMode) {
			case PercentVbus:
			case Position:
				lTalonMaster.set(left);
				rTalonMaster.set(right);
				break;

			case Speed:
				lTalonMaster.set(left * maxSpeed);
				rTalonMaster.set(right * maxSpeed);
				break;

			default:
				break;
			}
		}

		updateSmartDashboard();
	}

	public void toggleMaxSpeed(){
		if (maxSpeed == FAST_SPEED) {
			maxSpeed = SLOW_SPEED;
		} else {
			maxSpeed = FAST_SPEED;
		}
	}

	public void setClosedLoopSpeed() {
		controlMode = ControlMode.Speed;

		lTalonMaster.setProfile(SPEED_PROFILE);
		rTalonMaster.setProfile(SPEED_PROFILE);

		lTalonMaster.changeControlMode(controlMode);
		rTalonMaster.changeControlMode(controlMode);
	}

	public void setClosedLoopPosition() {
		controlMode = ControlMode.Position;

		lTalonMaster.setProfile(POSITION_PROFILE);
		rTalonMaster.setProfile(POSITION_PROFILE);

		lTalonMaster.changeControlMode(controlMode);
		rTalonMaster.changeControlMode(controlMode);
	}

	public void setP(double p) {
		lTalonMaster.setP(p);
		rTalonMaster.setP(p);
	}
	
	public void setOpenLoop() {
		controlMode = ControlMode.PercentVbus;

		lTalonMaster.changeControlMode(controlMode);
		rTalonMaster.changeControlMode(controlMode);
	}

	public void resetEncoders() {
		lTalonMaster.setPosition(0);
		rTalonMaster.setPosition(0);
	}

	public double getLeftPosition() {
		return lTalonMaster.getPosition();
	}

	public double getRightPosition() {
		return rTalonMaster.getPosition();
	}

	public void zeroYaw() {
		imu.zeroYaw();
	}
	
	public double getYaw() {
		return imu.getYaw();
	}
	
	public boolean isNavXOn() {
		return imu.isConnected() && !imu.isCalibrating();
	}
	
	public void initDefaultCommand() {
		switch (DRIVE_MODE) {
		case 0:
			setDefaultCommand(new DriveWithControllerOpen());
			break;
		case 1:
			setDefaultCommand(new DriveWithControllerClosed());
			break;
		case 2:
			setDefaultCommand(new DriveWithControllerSSS());
			break;
		}
	}
	
	private void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
		SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
		SmartDashboard.putNumber("Left Encoder Velocity: ", lTalonMaster.getSpeed());
		SmartDashboard.putNumber("Right Encoder Velocity: ", rTalonMaster.getSpeed());

		SmartDashboard.putNumber("Left Echo: ", lUltrasonic.getRangeInches());
		SmartDashboard.putNumber("Right Echo: ", rUltrasonic.getRangeInches());
		
		SmartDashboard.putBoolean(  "IMU_Connected",        imu.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    imu.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              imu.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            imu.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             imu.getRoll());
        SmartDashboard.putNumber(   "IMU_CompassHeading",   imu.getCompassHeading());
        
        SmartDashboard.putNumber(   "IMU_Accel_X",          imu.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          imu.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         imu.isMoving());
        SmartDashboard.putNumber(   "IMU_Temp_C",           imu.getTempC());
        
        SmartDashboard.putNumber(   "Velocity_X",       	imu.getVelocityX() );
        SmartDashboard.putNumber(   "Velocity_Y",       	imu.getVelocityY() );
        SmartDashboard.putNumber(   "Displacement_X",       imu.getDisplacementX() );
        SmartDashboard.putNumber(   "Displacement_Y",       imu.getDisplacementY() );
	}
	
}
