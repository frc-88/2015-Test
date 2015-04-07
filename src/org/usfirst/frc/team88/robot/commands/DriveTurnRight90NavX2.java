package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnRight90NavX2 extends Command {
	private static final double MAXSPEED = 0.1;
	private boolean done;

	public DriveTurnRight90NavX2() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.setClosedLoopSpeed();
		Robot.drive.zeroYaw();
		Robot.drive.enableBrakeMode(true);
		done = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double speed;
		double currentYaw = Robot.drive.getYaw();

		if (currentYaw < 90 ) {
			// the equation for a line is
			// y = m * x + b
			//  m = slope, b = y-intercept
			// we want a straight line from x=45 degrees and y = 100% MAXSPEED
			// to x = 90 degrees and y = 0% MAXSPEED
			speed = (-1/90 * currentYaw + 1) * MAXSPEED;

			// or, maybe we need to keep power to make sure we get past 90
			// this line gives us 20% MAXSPEED at 90 degrees
			//speed = (-0.04 * currentYaw + 3.8) * MAXSPEED;
			Robot.drive.driveMove(speed, -speed, 0);
		} else {
			Robot.drive.driveMove(0, 0, 0);
			done = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.enableBrakeMode(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
