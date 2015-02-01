package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithController extends Command {

    public DriveWithController() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double left, right, scale;
        double speed = Robot.oi.getDriverLeftVerticalAxis();
        double strafe = Robot.oi.getDriverRightHorizontalAxis();
        double spin = Robot.oi.getDriverRightZAxis() - Robot.oi.getDriverLeftZAxis();

        left = speed + spin;
        right = speed - spin;
        
        // scale values of left and right so they are between -1.0 and 1.0
        if ((Math.abs(left) > 1.0) || (Math.abs(right) > 1.0)) {
            if (Math.abs(left) > Math.abs(right)) {
                scale = 1.0 / Math.abs(left);
            } else {
                scale = 1.0 / Math.abs(right);
            }
            left *= scale;
            right *= scale;
        }
        
        Robot.drive.driveSimple(left, right, strafe);
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
