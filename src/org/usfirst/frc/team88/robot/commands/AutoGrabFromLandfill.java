package org.usfirst.frc.team88.robot.commands;


import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGrabFromLandfill extends CommandGroup {
    
    public  AutoGrabFromLandfill() {
    	//addSequential(new LiftDown());
    	addSequential(new DriveStraight(1.0));
    	addSequential(new LiftToPosition(Lift.POS_TRAVEL));
    	addSequential(new DriveStraight(-4.0));
    }
}
