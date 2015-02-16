package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command combines driving using speed control to get us close to the target
 * with driving using position control to get us exactly to the target. We check
 * to see if the command is done by counting the number of cycles where the 
 * encode values don't change. If we don't move for long enough, we're done.
 * 
 */
public class DriveStraight extends Command {

	private static final double SPEED = 0.5;
	private static final double RANGE = 500;
	private static final double TIMEOUT = 10;

	private double speed;
	private double firstTarget;
	private double finalTarget;
	private boolean inSpeedMode;
	private double prevLeftPosition = 0.0;
	private double prevRightPosition = 0.0;
	private int leftStillCount = 0;
	private int rightStillCount = 0;

	public DriveStraight(double distance) {
		requires(Robot.drive);

		speed = (distance > 0.0) ? SPEED : -SPEED;
		finalTarget = Drive.CYCLES_PER_METER * distance;
		firstTarget = Math.abs(finalTarget) - RANGE;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		inSpeedMode = true;
		Robot.drive.setClosedLoopSpeed();
		Robot.drive.resetEncoders();
		Robot.drive.driveMove(speed, speed, 0.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double leftPosition = Robot.drive.getLeftPosition();
		double rightPosition = Robot.drive.getRightPosition();

		if (inSpeedMode) {
			if ( (Math.abs(leftPosition) > firstTarget) || (Math.abs(rightPosition) > firstTarget) ) {
				inSpeedMode = false;
				Robot.drive.setClosedLoopPosition();
				Robot.drive.driveMove(finalTarget, finalTarget, 0.0);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double leftPosition = Robot.drive.getLeftPosition();
		double rightPosition = Robot.drive.getRightPosition();
		boolean done = false;

		// if we are not in speed mode, start counting cycles 
		// as long as the encoder position doesn't change.
		// If we aren't moving for long enough, we are done.
		if (!inSpeedMode) {
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
		}

		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
