package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.MovePole;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FishingPole extends Subsystem {
	private Talon poleMotor;
	
	public FishingPole() {
		poleMotor = new Talon(Wiring.poleMotor);
	}
	
    public void movePole(double speed){
    	poleMotor.set(speed);
    }
	
    public void initDefaultCommand() {
        setDefaultCommand(new MovePole());
    }
}

