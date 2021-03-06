package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will maintain the robot facing while allowing the driver to
 * drive forward or sideways. We store the initial angle returned by the gyro
 * when we start and adjust left and right power to compensate when the gyro
 * reading drifts from that value.
 */
public class DriveWithGyro extends Command {

	private static final double MAX_FORWARD_POWER = 0.5;
	private static final double FACING_THRESHOLD = 10.0;
	private double initialFacing;
	
    public DriveWithGyro() {
        requires(Robot.drive);
    }

    protected void initialize() {
    	// store initial facing
    	initialFacing = Robot.drive.getFacing();
    }

    protected void execute() {
    	double left, right, scale;
        double forward = Robot.oi.getDriverLeftVerticalAxis() * MAX_FORWARD_POWER;
        double sideways = Robot.oi.getDriverRightHorizontalAxis();
        double facingAdjustment = Math.max(Math.min((Robot.drive.getFacing() - initialFacing) / FACING_THRESHOLD, 1.0), -1.0);
        
        left = forward + facingAdjustment;
        right = forward - facingAdjustment;

        // scale values of left and right so they are between -1.0 and 1.0
        if ((Math.abs(left) > 1.0) || (Math.abs(right) > 1.0)) {
        	if (Math.abs(left)>Math.abs(right)) {
        		scale = 1.0 / Math.abs(left);
        	} else {
        		scale = 1.0 / Math.abs(right);
        	}
        	left *= scale;
        	right *= scale;
        }
        
        Robot.drive.driveSimple(left, right, sideways);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
