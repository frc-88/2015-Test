package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *    Schtick! Schtick! Schtick! Schtick! Schtick!
 *    The TJ fans chant and then
 *                              Dream of St. Louis
 */
public class Schtick extends Subsystem {
    private DoubleSolenoid schtick;

	public Schtick() {
    	schtick = new DoubleSolenoid(Wiring.schtickSolenoidIn, Wiring.schtickSolenoidOut);
	}
	
    public void schtickIn(){
    	schtick.set(Value.kReverse);
    }
    
    public void schtickOut(){
    	schtick.set(Value.kForward);
    }
 
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}	
