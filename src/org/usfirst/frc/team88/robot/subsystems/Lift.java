package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
    private final CANTalon liftTalon;
    private final DoubleSolenoid liftSolenoid;
    
    private final static double LIFT_SPEED = 0.5;
    
    public Lift() {
    	liftTalon = new CANTalon(Wiring.liftMotorController);
    	liftSolenoid = new DoubleSolenoid(Wiring.liftSolenoidIn, Wiring.liftSolenoidOut);
    	// TODO - Add code for limit switches
    }

    public void liftUp() {
    	liftTalon.set(LIFT_SPEED);
    }
    
    public void liftDown() {
    	liftTalon.set(-LIFT_SPEED);
    }
    
    public void liftGrab() {
    	liftSolenoid.set(Value.kForward);
    }
    
    public void liftRelease() {
    	liftSolenoid.set(Value.kReverse);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

