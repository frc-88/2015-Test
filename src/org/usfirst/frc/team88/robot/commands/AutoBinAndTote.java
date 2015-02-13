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
    	
    	// raise lift to one tote position
    	addSequential(new LifterToPosition(LifterToPosition.ONETOTE));
    	
    	// drive forward to tote
    	addSequential(new DriveDistance(100, 100));
    	
    	// drop the bin
    	addSequential(new GrabberOpen());
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// raise lift to travel position
    	addSequential(new LifterToPosition(LifterToPosition.TRAVEL));
    	
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
