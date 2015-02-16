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
	public static final double CYCLES_PER_90DEGREES = 1050.0;
    
    private final static double FAST_SPEED = 400.0;
    private final static double SLOW_SPEED = 200.0;
    
    private final static int SUSPENSION_TIMEOUT = 50;
    
    // Speed PID constants
    private final static double SPEED_P = 0.5;
    private final static double SPEED_I = 0.002;
    private final static double SPEED_D = 1.0;
    private final static double SPEED_F = 0.5;
    private final static int SPEED_PROFILE = 0;
    private final static int SPEED_IZONE = 0;
    private final static double SPEED_RAMPRATE = 6.0;
    
    // Position PID constants 
    private final static double POSITION_P = 0.9;
    private final static double POSITION_I = 0.0;
    private final static double POSITION_D = 0.0;
    private final static double POSITION_F = 0.0;
    private final static int POSITION_PROFILE = 1;
    private final static int POSITION_IZONE = 0;
    private final static double POSITION_RAMPRATE = 0.1;
    
    private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave, mTalon;
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
    	rTalonMaster.reverseOutput(false);
    	
    	// set up drive slaves
    	lTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	lTalonSlave.set(lTalonMaster.getDeviceID());
    	
    	rTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	rTalonSlave.set(rTalonMaster.getDeviceID());

    	maxSpeed = FAST_SPEED;
    	
    	setClosedLoopSpeed();
    }
    
    public void driveMove(double left, double right, double middle) {
    	if (middle == 0.0 && isSuspensionDown) {
    		if (middleStillCount++ > SUSPENSION_TIMEOUT) {
        		suspensionUp();
			}
    	} else if(!isSuspensionDown) {
    		suspensionDown();
    	}

    	switch (controlMode) {
    	case PercentVbus:
    	case Position:
    		lTalonMaster.set(left);
    		rTalonMaster.set(right);
    		break;
    		
    	case Speed:
    		lTalonMaster.set(-left * maxSpeed);
	        rTalonMaster.set(right * maxSpeed);
	        break;
	        
		default:
			break;
    	}
    	
    	mTalon.set(middle);

        SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
        SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
        SmartDashboard.putNumber("Left Encoder Velocity: ", lTalonMaster.getSpeed());
        SmartDashboard.putNumber("Right Encoder Velocity: ", rTalonMaster.getSpeed());
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
    
    public double getLeftPosition() {
    	return lTalonMaster.getPosition();
    }

    public double getRightPosition() {
    	return rTalonMaster.getPosition();
    }
    
    public void suspensionUp() {
    	suspension.set(Value.kReverse);
    	isSuspensionDown = false;
    }
    
    public void suspensionDown() {
    	suspension.set(Value.kForward);
    	isSuspensionDown = true;
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
