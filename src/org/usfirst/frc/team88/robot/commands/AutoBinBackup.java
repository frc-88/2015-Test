package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBinBackup extends CommandGroup {
    
    public  AutoBinBackup() {
    	addSequential(new Delay(3));

    	addSequential(new DriveStraight(1.0));

    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// wait for the grabber to completely close before moving the lift
    	addSequential(new Delay(0.3));
    	
    	// raise lift to position 1
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	addSequential(new DriveStraight(-2.0));
    	
    }
}
