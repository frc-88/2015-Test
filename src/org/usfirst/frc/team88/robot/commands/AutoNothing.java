package org.usfirst.frc.team88.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNothing extends CommandGroup {
    
    public  AutoNothing() {
    	addSequential(new Delay(5));
    }
}
