package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTote extends CommandGroup {
    
    public  AutoTote() {
    	// open grabber
    	addSequential(new grabberOpen());

    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// close grabber
    	addSequential(new grabberClose());
    	
    	// raise lift to position 1
    	addSequential(new LifterToPosition(1));
    	
    	// turn right 90 degrees
    	// TODO: Write turning command
    	
    	// drive forward to autozone
    	addSequential(new DriveStraight(0.5, 120.0));
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// open grabber
    	addSequential(new grabberOpen());
    }
}
