package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.LiftWithController;

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
    	lowerLimit = new DigitalInput(Wiring.liftLowerLimit);
    	upperLimit = new DigitalInput(Wiring.liftUpperLimit);
    }

    public void moveLift(double liftSpeed) {
    	if (atLowerLimit() && (liftSpeed < 0.0)) {
			liftSpeed = 0.0;
    	} else if (atUpperLimit() && (liftSpeed > 0.0)) {
			liftSpeed = 0.0;
    	}
    	
    	liftTalon.set(liftSpeed);

    	SmartDashboard.putBoolean("LimitSwitch Lower" , lowerLimit.get());
    	SmartDashboard.putBoolean("LimitSwitch Upper" , upperLimit.get());
        SmartDashboard.putNumber("Lift Encoder: ", liftTalon.getEncPosition());
        SmartDashboard.putNumber("Lift Position: ", liftTalon.getPosition());
    }
    
    public int getPosition() {
    	return liftTalon.getEncPosition();
    }
    
    public boolean atLowerLimit() {
    	if (!lowerLimit.get()) {
    		liftTalon.setPosition(0);
    		return true;
    	}
    	return false;
    }
    
    public boolean atUpperLimit() {
    	return !upperLimit.get();
    }
    
    public void liftGrab() {
    	liftSolenoid.set(Value.kForward);
    }
    
    public void liftRelease() {
    	liftSolenoid.set(Value.kReverse);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new LiftWithController());
    }
}

