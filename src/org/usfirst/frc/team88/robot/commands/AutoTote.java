package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

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
    	
    	// wait for the grabber to completely close before moving the lift
    	addSequential(new Delay(0.3));
    	
    	// raise lift to position 1
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	// turn right 90 degrees
    	addSequential(new DriveTurnRight90());
    	
    	// drive forward to autozone
    	addSequential(new DriveStraight(3.4));
    	
    	// turn right 90 degrees
    	addSequential(new DriveTurnRight90());
    	
    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// open grabber
    	addSequential(new LiftGrabberOpen());
    }
}
