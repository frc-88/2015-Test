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
	public static final double AUTO_SPEED = 1.0;
	
	public static final double POS_TOP = Wiring.practiceRobot ? 160000 : 44000;
	public static final double POS_FOURTOTES = Wiring.practiceRobot ? 160000 : 39000;
	public static final double POS_THREETOTES = Wiring.practiceRobot ? 115000 : 31000;
	public static final double POS_TWOTOTES = Wiring.practiceRobot ? 80000 : 22000;
	public static final double POS_ONETOTE = Wiring.practiceRobot ? 44000 : 21000;  // 3.24.15
	public static final double POS_TRAVEL = Wiring.practiceRobot ? 10000 : 3000;
	public static final double POS_BOTTOM = 0;
	
	public static final double POS_BINONTOTE = 35000;
	public static final double POS_PICKUPBIN = 21000;
	public static final double POS_HOOKBIN = 75000;
	public static final double POS_TOTEONTOTE = Wiring.practiceRobot ? 29000 : 14000;  // 3.24.15
	public static final double POS_TOTEONTWOTOTES = Wiring.practiceRobot ? 70000 : 19000;
	public static final double POS_ABOVETHECHUTE = Wiring.practiceRobot ? 120000 : 50000;  // 3.24.15

	
    private final CANTalon liftTalon;
    private final DoubleSolenoid liftSolenoid;
    private final DigitalInput upperLimit, lowerLimit;
    
    public Lift() {
    	liftSolenoid = new DoubleSolenoid(Wiring.liftSolenoidIn, Wiring.liftSolenoidOut);
    	lowerLimit = new DigitalInput(Wiring.liftLowerLimit);
    	upperLimit = new DigitalInput(Wiring.liftUpperLimit);
    	liftTalon = new CANTalon(Wiring.liftMotorController);
    	liftTalon.enableBrakeMode(true);
    	liftTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	liftTalon.reverseSensor(true);
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

