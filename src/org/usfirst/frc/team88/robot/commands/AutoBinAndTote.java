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
    	//addSequential(new LiftToPosition(Lift.POS_PICKUPBIN));
    	
    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// wait for the grabber to completely close before moving the lift
    	addSequential(new Delay(0.3));
    	
    	// raise lift to one tote position
    	addSequential(new LiftToPosition(Lift.POS_BINONTOTE));
    	
    	// drive forward to tote
    	addSequential(new DriveStraight(0.85));

    	// drop the bin
    	addSequential(new LiftGrabberOpen());
    	
    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// close grabber
    	addSequential(new LiftGrabberClose());
    	
    	// wait for the grabber to completely close before moving the lift
    	addSequential(new Delay(0.3));
    	
    	// raise lift to travel position
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	// turn left 90 degrees
    	addSequential(new DriveTurnLeft90());
    	
    	// drive forward to autozone
    	addSequential(new DriveStraight(3.6));

    	// lower lift to bottom limit
    	addSequential(new LiftDown());
    	
    	// open grabber
    	addSequential(new LiftGrabberOpen());
    }
}
