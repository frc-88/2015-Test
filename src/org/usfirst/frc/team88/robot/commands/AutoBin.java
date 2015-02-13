package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBin extends CommandGroup {
    
    public  AutoBin() {
    	// open grabber
    	addSequential(new LiftGrabberOpen());

    	// lower lift to bottom limit, zeroes lifter encoder
    	addSequential(new LiftDown());
    	
    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// raise lift to position 1
    	addSequential(new LiftToPosition(1));
    	
    	// turn left 90 degrees
    	addSequential(new DriveDistance(-100, 100));
    	
    	// drive forward to autozone
    	addSequential(new DriveDistance(100, 100));
    	
    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// open grabber
    	addSequential(new LiftGrabberOpen());
    }
}
