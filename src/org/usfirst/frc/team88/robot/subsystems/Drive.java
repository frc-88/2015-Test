package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSSS;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerClosed;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerOpen;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Gyro;

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

	// Speed PID constants
	private final static double SPEED_P = 1.0;
	private final static double SPEED_I = 0.002;
	private final static double SPEED_D = 0.0;
	private final static double SPEED_F = 0.0;

	private final static int SPEED_PROFILE = 0;
	private final static int SPEED_IZONE = 0;
	private final static double SPEED_RAMPRATE = 36.0;

	// Position PID constants 
	private final static double POSITION_P = Wiring.practiceRobot ? 0.3 : 0.2;
	private final static double POSITION_I = 0.0;
	private final static double POSITION_D = 0.0;
	private final static double POSITION_F = 0.0;
	private final static int POSITION_PROFILE = 1;
	private final static int POSITION_IZONE = 0;
	private final static double POSITION_RAMPRATE = 0.05;

	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave, mTalon;
	private CANTalon.ControlMode controlMode;
	private Gyro gyro;
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
		gyro = new Gyro(0);
		
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

		// set up gryo
		gyro.reset();
		gyro.initGyro();
		gyro.setSensitivity(0.250);
		
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

		SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
		SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
		SmartDashboard.putNumber("Left Encoder Velocity: ", lTalonMaster.getSpeed());
		SmartDashboard.putNumber("Right Encoder Velocity: ", rTalonMaster.getSpeed());

		SmartDashboard.putNumber("Gyro angle (rad?): ", gyro.getAngle());
		SmartDashboard.putNumber("Gyro angle (deg?): ", gyro.getAngle() * 180 / Math.PI);
		SmartDashboard.putNumber("Gyro rate: ", gyro.getRate());
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

		SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
		SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
		SmartDashboard.putNumber("Left Encoder Velocity: ", lTalonMaster.getSpeed());
		SmartDashboard.putNumber("Right Encoder Velocity: ", rTalonMaster.getSpeed());

		SmartDashboard.putNumber("Gyro angle (rad?): ", gyro.getAngle());
		SmartDashboard.putNumber("Gyro angle (deg?): ", gyro.getAngle() * 180 / Math.PI);
		SmartDashboard.putNumber("Gyro rate: ", gyro.getRate());
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

	public void setOpenLoop() {
		controlMode = ControlMode.PercentVbus;

		lTalonMaster.changeControlMode(controlMode);
		rTalonMaster.changeControlMode(controlMode);
	}

	public void resetEncoders() {
		lTalonMaster.setPosition(0);
		rTalonMaster.setPosition(0);
	}

	public void resetGyro() {
		gyro.reset();
	}

	public double getGyroAngle() {
		return gyro.getAngle();
	}

	public double getLeftPosition() {
		return lTalonMaster.getPosition();
	}

	public double getRightPosition() {
		return rTalonMaster.getPosition();
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
}
