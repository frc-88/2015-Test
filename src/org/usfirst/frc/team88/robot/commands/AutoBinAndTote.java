package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBinAndTote extends CommandGroup {
    
    public  AutoBinAndTote() {
    	// open grabber
    	addSequential(new grabberOpen());

    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// close grabber
    	addSequential(new grabberClose());
    	
    	// raise lift to position 2
    	addSequential(new LifterToPosition(2));
    	
    	// drive forward to tote
    	addSequential(new DriveStraight(0.5, 60.0));
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// raise lift to position 1
    	addSequential(new LifterToPosition(1));
    	
    	// turn left 90 degrees
    	// TODO: Write turning command
    	
    	// drive forward to autozone
    	addSequential(new DriveStraight(0.5, 120.0));
    	
    	// lower lift to bottom limit
    	addSequential(new LifterDown());
    	
    	// open grabber
    	addSequential(new grabberOpen());
    }
}
