package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will maintain the robot facing while allowing the driver to
 * drive forward or sideways. We store the initial angle returned by the gyro
 * when we start and adjust left and right power to compensate when the gyro
 * reading drifts from that value. The driver can also adjust the desired 
 * facing, causing the robot to rotate, using the triggers on the controller.
 */
public class DriveWithGyroAndRotate extends Command {

	private static final double MAX_FORWARD_POWER = 0.5;
	private static final double FACING_THRESHOLD = 10.0;
	private static final double DEADZONE = 0.1;

	private double currentFacing;

	public DriveWithGyroAndRotate() {
		requires(Robot.drive);
	}

	protected void initialize() {
		// store initial facing
		currentFacing = Robot.drive.getFacing();
	}

	protected void execute() {
		double left, right, scale, facingAdjustment;
		double desiredFacing = currentFacing;
		double forward = Robot.oi.getDriverLeftVerticalAxis() * MAX_FORWARD_POWER;
		double sideways = Robot.oi.getDriverRightHorizontalAxis();
		double rotation = Robot.oi.getDriverLeftZAxis() - Robot.oi.getDriverRightZAxis();

		// apply deadzones to forward and sideways inputs
		if (Math.abs(forward) < DEADZONE) {
			forward = 0.0;
		}

		if (Math.abs(sideways) < DEADZONE) {
			sideways = 0.0;
		}
		
		// if rotation outside of deadzone, adjust desired facing
		if (Math.abs(rotation) > DEADZONE) {
			desiredFacing += rotation * FACING_THRESHOLD;
		}

		// calculate any adjustment needed in order to rotate to desired facing
		facingAdjustment = Math.max(Math.min((Robot.drive.getFacing() - desiredFacing) / FACING_THRESHOLD, 1.0), -1.0);

		// apply the rotation adjustment. This may cause values outside of -1 to 1, so we scale next
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

		// if we adjusted our desired facing in order to rotate, update currentFacing
		if (Math.abs(rotation) > DEADZONE) {
			currentFacing = Robot.drive.getFacing();
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
