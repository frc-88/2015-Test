package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnLeft90NavX extends Command {
	private static final double MAXSPEED = 0.7;
	private boolean done;
	
	public DriveTurnLeft90NavX() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.setClosedLoopSpeed();
		Robot.drive.zeroYaw();
		done = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double speed;
		double currentYaw = Robot.drive.getYaw();
		
		if (currentYaw > -70) {
			speed = MAXSPEED;
		} else if (currentYaw > -90 ) {
			// the equation for a line is
			// y = m * x + b
			//  m = slope, b = y-intercept
			// we want a straight line from x=-70 degrees and y = 100% MAXSPEED
			// to x = -90 degrees and y = 0% MAXSPEED
			speed = (0.05 * currentYaw + 4.5) * MAXSPEED;
			
			// or, maybe we need to keep power to make sure we get past 90
			// this line gives us 20% MAXSPEED at -90 degrees
			//speed = (0.04 * currentYaw + 3.8) * MAXSPEED;
		} else {
			speed = 0;
			done = true;
		}
		Robot.drive.driveMove(-speed, speed, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
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
