package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Suspension extends Subsystem {
    private DoubleSolenoid suspension;
    
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Suspension() {
    	suspension = new DoubleSolenoid(Wiring.suspensionSolenoidDown, Wiring.suspensionSolenoidUp);
   
	}
	
    public void suspensionUp(){
    	suspension.set(Value.kReverse);
    }
    
    public void suspensionDown(){
    	suspension.set(Value.kForward);
    }
 

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

