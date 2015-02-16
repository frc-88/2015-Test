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
 *    Lifting totes and bins
 *    Pick things up and put them down
 *                 Oooo, a yellow one!
 */
public class Lift extends Subsystem {
	public static final double AUTO_SPEED = .5;
	public static final double POS_TOP = 9999;
	public static final double POS_THREETOTES = 400;
	public static final double POS_TWOTOTES = 300;
	public static final double POS_ONETOTE = 1000;
	public static final double POS_TRAVEL = 9000;
	public static final double POS_BOTTOM = 0;
        
    private final CANTalon liftTalon;
    private final DoubleSolenoid liftSolenoid;
    private final DigitalInput upperLimit, lowerLimit;
    
    public Lift() {
    	liftTalon = new CANTalon(Wiring.liftMotorController);
    	liftTalon.enableBrakeMode(true);
    	liftTalon.reverseSensor(true);
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
    
    public double getPosition() {
    	return liftTalon.getPosition();
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

