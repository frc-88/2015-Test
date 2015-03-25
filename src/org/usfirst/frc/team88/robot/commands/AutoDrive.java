package org.usfirst.frc.team88.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDrive extends CommandGroup {
    
    public  AutoDrive(double distance) {
    	addSequential(new DriveStraight(distance));
    }

    
}
