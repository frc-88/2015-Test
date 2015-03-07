package org.usfirst.frc.team88.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTest extends CommandGroup {
    
    public  AutoTest() {
    	addSequential(new DriveAngle(2));
/*
    	addSequential(new DriveStraight(2.11));
    	addSequential(new Delay(3));
    	addSequential(new DriveStraight(2.11));
    	*/
    }
}
