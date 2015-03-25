package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBin extends CommandGroup {
    
    public  AutoBin() {
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	addSequential(new DriveTurnLeft90NavX());
    	addSequential(new DriveStraight(3.4));
    }
}
