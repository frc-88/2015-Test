package org.usfirst.frc.team88.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTest extends CommandGroup {
    
    public  AutoTest() {
    	addSequential(new DriveStraight(1.0));
    	addSequential(new DriveTurnLeft90());
    }
}
