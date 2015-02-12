package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBinAndTote extends CommandGroup {
    
    public  AutoBinAndTote() {
    	// open grabber
    	addSequential(new GrabberOpen());

    	// lower lift to bottom limit, zeroes lifter encoder
    	addSequential(new LifterDown());
    	
    	// close grabber
    	addSequential(new GrabberClose());
    	
    	// raise lift to position 2
    	addSequential(new LifterToPosition(2));
    	
    	// drive forward to tote
    	addSequential(new DriveDistance(100, 100));
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// raise lift to position 1
    	addSequential(new LifterToPosition(1));
    	
    	// turn left 90 degrees
    	addSequential(new DriveDistance(-100, 100));
    	
    	// drive forward to autozone
    	addSequential(new DriveDistance(100, 100));
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// open grabber
    	addSequential(new GrabberOpen());
    }
}
