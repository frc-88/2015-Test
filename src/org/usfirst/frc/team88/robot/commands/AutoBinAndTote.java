package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBinAndTote extends CommandGroup {
    
    public  AutoBinAndTote() {
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	
    	addSequential(new LiftToPosition(Lift.POS_ONETOTE));
    	
    	addSequential(new DriveStraight(0.85));

    	addSequential(new LiftGrabberOpen());
    	
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	addSequential(new DriveTurnLeft90());
    	
    	addSequential(new DriveStraight(3.4));
    }
}
