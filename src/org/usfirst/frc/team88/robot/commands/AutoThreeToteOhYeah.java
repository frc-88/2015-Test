package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoThreeToteOhYeah extends CommandGroup {
    
    public  AutoThreeToteOhYeah() {
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	addParallel(new LiftToPosition(Lift.POS_ONETOTE));
    	addSequential(new Delay(0.6));
    	addSequential(new DriveStraight(2.2));
    	
    	addSequential(new LiftToPosition(Lift.POS_TOTEONTOTE));
    	
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	addParallel(new LiftToPosition(Lift.POS_ONETOTE));
    	addSequential(new Delay(0.6));
    	addSequential(new DriveStraight(2.3));

    	addSequential(new LiftToPosition(Lift.POS_TOTEONTOTE));
    	
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	addSequential(new DriveTurnRight90());
    	addSequential(new DriveStraight(3.0));

    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    }
}
