package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoOneBinandTwoTote extends CommandGroup {
    
    public  AutoOneBinandTwoTote() {
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.3));
    	
    	addSequential(new LiftToPosition(Lift.POS_ONETOTE));
    	
    	addSequential(new DriveStraight(0.85));

    	addSequential(new LiftGrabberOpen());
    	
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.3));
    	
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	addSequential(new DriveTurnLeft90NavX());
    	addSequential(new DriveTurnLeft90NavX());
    	addSequential(new Delay(0.3));
    	addSequential(new DriveStraight(1.4));
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.3));
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	addSequential(new DriveTurnRight90NavX());
    	addSequential(new DriveStraight(3.4));
    	addSequential(new DriveTurnRight90NavX());
    	addParallel(new LiftDown());
    }
}
