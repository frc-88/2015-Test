package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSSS;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 *
 */
public class Drive extends Subsystem {
    public final static double ENC_CYCLES_PER_REV = 360.0;
    public final static double GEAR_RATIO = 28.0 / 22.0;
    public final static double WHEEL_DIAMETER = 6.0;
    
    private final static double FAST_SPEED = 400.0;
    private final static double SLOW_SPEED = 200.0;
    
    // Speed PID constants
    private final static double SPEED_P = 0.5;
    private final static double SPEED_I = 0.002;
    private final static double SPEED_D = 1.0;
    private final static double SPEED_F = 0.5;
    private final static int SPEED_PROFILE = 0;
    private final static int SPEED_IZONE = 0;
    private final static double SPEED_RAMPRATE = 6.0;
    
    // Position PID constants 
    private final static double POSITION_P = 0.5;
    private final static double POSITION_I = 0.002;
    private final static double POSITION_D = 1.0;
    private final static double POSITION_F = 0.5;
    private final static int POSITION_PROFILE = 1;
    private final static int POSITION_IZONE = 0;
    private final static double POSITION_RAMPRATE = 6.0;
    
    private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave, mTalon;
    private DoubleSolenoid suspension;
    private double maxSpeed;
    private boolean positionMode;
    
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
    	rTalonMaster.reverseSensor(false);
    	rTalonMaster.reverseOutput(false);
    	
    	// set up drive slaves
    	lTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	lTalonSlave.set(lTalonMaster.getDeviceID());
    	
    	rTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	rTalonSlave.set(rTalonMaster.getDeviceID());

    	maxSpeed = FAST_SPEED;
    	
    	setSpeedMode();
    }
    
    public void driveSimple(double left, double right, double middle) {
    	if (middle == 0.0) {
    		suspensionUp();
    	} else {
    		suspensionDown();
    	}

    	if (positionMode) {
    		lTalonMaster.set(left);
    		rTalonMaster.set(right);
    	} else {
	    	lTalonMaster.set(-left * maxSpeed);
	        rTalonMaster.set(right * maxSpeed);
    	}

    	mTalon.set(middle);

        SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getEncPosition());
        SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getEncPosition());
        SmartDashboard.putNumber("Left Encoder Velocity: ", lTalonMaster.getEncVelocity());
        SmartDashboard.putNumber("Right Encoder Velocity: ", rTalonMaster.getEncVelocity());
    }
    
    public void toggleMaxSpeed(){
    	if (maxSpeed == FAST_SPEED) {
    		maxSpeed = SLOW_SPEED;
    	} else {
    		maxSpeed = FAST_SPEED;
    	}
    }
    
    public void setSpeedMode() {
    	lTalonMaster.setProfile(SPEED_PROFILE);
    	lTalonMaster.changeControlMode(ControlMode.Speed);

    	rTalonMaster.setProfile(SPEED_PROFILE);
    	rTalonMaster.changeControlMode(ControlMode.Speed);
    	
    	positionMode = false;
    }
    
    public void setPositionMode() {
    	lTalonMaster.setProfile(POSITION_PROFILE);
    	lTalonMaster.changeControlMode(ControlMode.Position);
    	
    	rTalonMaster.setProfile(POSITION_PROFILE);
    	rTalonMaster.changeControlMode(ControlMode.Position);
    	
    	positionMode = true;
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
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
    
    // private functions
    private void suspensionUp() {
    	suspension.set(Value.kReverse);
    }
    
    private void suspensionDown() {
    	suspension.set(Value.kForward);
    }
}
