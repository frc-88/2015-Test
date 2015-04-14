package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**Bin Bin Bin Bin Bin Bin Bin
 *Grab from landfill go
 *St.Louis We are coming
 */
public class AutoBinFromLandfill extends CommandGroup {

	public  AutoBinFromLandfill() {
		addParallel(new DriveStraightMaxSpeed(-1.1));
		addSequential(new Delay(0.3));
		addSequential(new SuperSchticksUp());
		addSequential(new Delay(0.8));
		addSequential(new DriveStraightMaxSpeed(1.3));
	}
}
