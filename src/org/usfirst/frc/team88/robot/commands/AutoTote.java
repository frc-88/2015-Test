package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTote extends CommandGroup {
    
    public  AutoTote() {
    	// open grabber
    	addSequential(new LiftGrabberOpen());

    	// lower lift to bottom limit, zeroes lifter encoder
    	addSequential(new LiftDown());
    	
    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// raise lift to position 1
    	addSequential(new LiftToPosition(1));
    	
    	// turn right 90 degrees
    	addSequential(new DriveEncoder(100, -100));
    	
    	// drive forward to autozone
    	addSequential(new DriveEncoder(100, 100));
    	
    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// open grabber
    	addSequential(new LiftGrabberOpen());
    }
}
