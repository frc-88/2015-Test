package org.usfirst.frc.team88.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDrive extends CommandGroup {
    
    public  AutoDrive() {
    	addSequential(new DriveStraight(2.5,1.0));
    }
}
