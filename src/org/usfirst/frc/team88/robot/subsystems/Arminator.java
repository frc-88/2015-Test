package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.ArminatorWithController;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *           The Arminator
 *    It might give you a wedgie!
 *        Can it pick up bins?
 */
public class Arminator extends Subsystem {
	private Talon arminatorMotor;
	
	public Arminator() {
		arminatorMotor = new Talon(Wiring.arminatorMotor);
	}
	
    public void move(double speed){
    	arminatorMotor.set(speed);
    }
	
    public void initDefaultCommand() {
        setDefaultCommand(new ArminatorWithController());
    }
}

