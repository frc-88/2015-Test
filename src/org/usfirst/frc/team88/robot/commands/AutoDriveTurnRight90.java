package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveTurnRight90 extends CommandGroup {
    
    public  AutoDriveTurnRight90() {
    	addSequential(new DriveStraight(3.4));
    	addSequential(new Delay(1));
		addSequential(new DriveTurnRight90NavX());
    }
}
