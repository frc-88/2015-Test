package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithControllerSSS extends Command {

    public DriveWithControllerSSS() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setClosedLoopSpeed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double left, right, scale;
        double speed = Robot.oi.getDriverLeftVerticalAxis();
        double strafe = Robot.oi.getDriverRightZAxis() - Robot.oi.getDriverLeftZAxis();
        double spin = Robot.oi.getDriverRightHorizontalAxis();

        speed = Robot.oi.applyDeadZone(speed);
		strafe = Robot.oi.applyDeadZone(strafe);
		spin = Robot.oi.applyDeadZone(spin);
        
        left = speed - spin;
        right = speed + spin;
        
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
        
        Robot.drive.driveMove(left, right, strafe);
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
