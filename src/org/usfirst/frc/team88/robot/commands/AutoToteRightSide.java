package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoToteRightSide extends CommandGroup {
    
    public  AutoToteRightSide() {
    	addSequential(new LiftGrabberOpen());
    	addSequential(new LiftDown());
    	addSequential(new LiftGrabberClose());
    	addSequential(new Delay(0.2));
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	
    	addSequential(new DriveTurnRight90());
    	
    	addSequential(new DriveStraight(3.4));
    	
    	addSequential(new DriveTurnLeft90());
    	
    	addSequential(new LiftToPosition(Lift.POS_ONETOTE));
    }
}
