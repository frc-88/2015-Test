package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;
import org.usfirst.frc.team88.robot.commands.lift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends Subsystem {
    private final CANTalon liftTalon;
    private final DoubleSolenoid liftSolenoid;
    private final DigitalInput upperLimit, lowerLimit;
    
    //private final static double LIFT_SPEED = 0.5;
    
    public Lift() {
    	liftTalon = new CANTalon(Wiring.liftMotorController);
    	liftTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	liftSolenoid = new DoubleSolenoid(Wiring.liftSolenoidIn, Wiring.liftSolenoidOut);
    	lowerLimit = new DigitalInput(Wiring.lowerLimit);
    	upperLimit = new DigitalInput(Wiring.upperLimit);
    	// TODO - Add code for limit switches
    	//update David is doing this through DIO
    }

    public void moveLift(double liftSpeed) {
    	//logic probably needs to be inverted
    	if (!getLowerLimit()) {
    		if (liftSpeed <0) {
    			liftSpeed = 0;
    		}
    	}else if (!getUpperLimit()) {
    		if (liftSpeed >0) {
    			liftSpeed = 0;
    		}	
    	}
    	SmartDashboard.putBoolean("LimitSwitch Lower" , lowerLimit.get());
    	SmartDashboard.putBoolean("LimitSwitch Upper" , upperLimit.get());
    	
    	liftTalon.set(liftSpeed);
    	
    }
    public double getEncoderCount() {
    	return liftTalon.getEncPosition();
    }
    
    public boolean getLowerLimit() {
    	return lowerLimit.get();
    }
    
    public boolean getUpperLimit() {
    	return upperLimit.get();
    }
    
    
    public void liftGrab() {
    	liftSolenoid.set(Value.kForward);
    }
    
    public void liftRelease() {
    	liftSolenoid.set(Value.kReverse);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new lift());
    }
}

