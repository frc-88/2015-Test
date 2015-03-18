package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPickup extends CommandGroup {
    
    public  AutoPickup() {
       requires(Robot.lift);
       
       addSequential(new LiftToPosition(Lift.POS_TOTEONTWOTOTES));
       addSequential(new LiftGrabberOpen());
       addSequential(new LiftToPosition(Lift.POS_TOTEONTOTE));
       addSequential(new LiftGrabberClose());
       addSequential(new Delay(0.2));
       addSequential(new LiftToPosition(Lift.POS_ABOVETHECHUTE));
    }
}
