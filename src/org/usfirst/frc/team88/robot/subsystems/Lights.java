package org.usfirst.frc.team88.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lights extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private final DigitalOutput digOut;
	
	public Lights() {
		digOut = new DigitalOutput(2);
	}
	
	public void digOutOn() {
		digOut.set(true);
	}
	
	public void digOutOff() {
		digOut.set(false);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

