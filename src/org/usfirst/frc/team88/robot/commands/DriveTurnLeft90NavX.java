package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnLeft90NavX extends Command {
	private static final double TIMEOUT = 5;
	private static final double SPEED = 0.5;

	private double prevLeftPosition = 0.0;
	private double prevRightPosition = 0.0;
	private int leftStillCount = 0;
	private int rightStillCount = 0;

	public DriveTurnLeft90NavX() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.setClosedLoopSpeed();
		Robot.drive.zeroYaw();
		// 90 degree spin to the left
		Robot.drive.driveMove(-SPEED, SPEED, 0.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.drive.getYaw() < -90) {
			Robot.drive.resetEncoders();
			Robot.drive.setClosedLoopPosition();
			Robot.drive.driveMove(0.0, 0.0, 0.0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double leftPosition = Robot.drive.getLeftPosition();
		double rightPosition = Robot.drive.getRightPosition();
		boolean done = false;

		// start counting cycles as long as the encoder position doesn't change.
		// If we aren't moving for long enough, we are done.
		if (leftPosition == prevLeftPosition) {
			leftStillCount++;
		} else {
			leftStillCount = 0;
			prevLeftPosition = leftPosition;
		}
		if (rightPosition == prevRightPosition) {
			rightStillCount++;
		} else {
			rightStillCount = 0;
			prevRightPosition = rightPosition;
		}

		if ((leftStillCount > TIMEOUT) && (rightStillCount > TIMEOUT)) {
			done = true;
		}

		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.setClosedLoopSpeed();
		//Robot.drive.driveMove(0.0, 0.0, 0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
