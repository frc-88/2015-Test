package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBinAndTote extends CommandGroup {
    
    public  AutoBinAndTote() {
    	// open grabber
    	addSequential(new LiftGrabberOpen());

    	// lower lift to bottom limit, zeroes lifter encoder
    	addSequential(new LiftDown());
    	
    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// raise lift to one tote position
    	addSequential(new LiftToPosition(Lift.POS_ONETOTE));
    	
    	// drive forward to tote
    	addSequential(new DriveStraight(0.4));
    	
    	// drop the bin
    	addSequential(new LiftGrabberOpen());
    	
    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// raise lift to travel position
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	// turn left 90 degrees
    	addSequential(new DriveTurnLeft90());
    	
    	// drive forward to autozone
    	addSequential(new DriveStraight(3.1));
    	
    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// open grabber
    	addSequential(new LiftGrabberOpen());
    }
}
