package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBinBackup extends CommandGroup {
    
    public  AutoBinBackup() {
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	
    	addSequential(new Delay(5));

    	addSequential(new DriveStraight(1.0));
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	addSequential(new DriveStraight(-3.0));
    	
    }
}
