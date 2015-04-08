package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *    Schtick! Schtick! Schtick! Schtick! Schtick!
 *    The TJ fans chant and then
 *                              Dream of St. Louis
 */
public class Schtick extends Subsystem {
    private DoubleSolenoid schtick;
    private DoubleSolenoid superSchtick;
    
	public Schtick() {
    	schtick = new DoubleSolenoid(Wiring.schtickSolenoidIn, Wiring.schtickSolenoidOut);
    	superSchtick = new DoubleSolenoid(6,7);
	}
	
    public void schtickIn(){
    	schtick.set(Value.kReverse);
    }
    
    public void schtickOut(){
    	schtick.set(Value.kForward);
    }
 
    public void superSchticksUp() {
    	superSchtick.set(Value.kReverse);
    }
    
    public void superSchticksDown() {
    	superSchtick.set(Value.kForward);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}	
