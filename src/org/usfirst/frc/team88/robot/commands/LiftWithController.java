package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftWithController extends Command {

    public LiftWithController() {
    	super("lift");
    	requires(Robot.lift);
    	requires(Robot.schtick);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double liftSpeed = Robot.oi.getOperatorRightZAxis() - Robot.oi.getOperatorLeftZAxis();
        Robot.lift.moveLift(liftSpeed);
        
        if ((liftSpeed < 0) && 
            (Robot.lift.getPosition() < Lift.POS_BEWAREOFSCHTICK) && 
            (Robot.schtick.isSchtickIn()) && 
            (Robot.lift.isLiftArmsClosed())) {
        	Robot.schtick.schtickOut();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
