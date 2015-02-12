package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBin extends CommandGroup {
    
    public  AutoBin() {
    	// open grabber
    	addSequential(new grabberOpen());

    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// close grabber
    	addSequential(new grabberClose());
    	
    	// raise lift to position 1
    	addSequential(new LifterToPosition(1));
    	
    	// turn left 90 degrees
    	// TODO: Write turning command
    	
    	// drive forward to autozone
    	addSequential(new DriveDistance(100, 100));
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// open grabber
    	addSequential(new grabberOpen());
    }
}
