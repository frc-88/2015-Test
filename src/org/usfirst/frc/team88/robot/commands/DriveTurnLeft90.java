package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnLeft90 extends Command {
	private static final double TIMEOUT = 10;

	private double prevLeftPosition = 0.0;
	private double prevRightPosition = 0.0;
	private int leftStillCount = 0;
	private int rightStillCount = 0;

	public DriveTurnLeft90() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.setClosedLoopPosition();
		Robot.drive.resetEncoders();
		// 90 degree spin to the left
		Robot.drive.driveMove(-Drive.CYCLES_PER_90DEGREES, -Drive.CYCLES_PER_90DEGREES, 0.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double leftPosition = Robot.drive.getLeftPosition();
		double rightPosition = Robot.drive.getRightPosition();
		boolean done = false;

		// if we are not in speed mode, start counting cycles 
		// where the encoder position doesn't change.
		// If we aren't moving for long enough, we are done.
		if (leftPosition == prevLeftPosition) {
			leftStillCount++;
		} else {
			leftStillCount = 0;
		}
		if (rightPosition == prevRightPosition) {
			rightStillCount++;
		} else {
			rightStillCount = 0;
		}

		if ((leftStillCount > TIMEOUT) && (rightStillCount > TIMEOUT)) {
			done = true;
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
