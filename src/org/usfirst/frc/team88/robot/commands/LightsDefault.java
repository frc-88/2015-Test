package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command responsible for sequencing the lights
 *
 */
public class LightsDefault extends Command {
	private boolean busy = false;
	private boolean blinking = false;

	public LightsDefault() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.lights);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//most of the logic here needs to be edited but the structure is fine.
		if (DriverStation.getInstance().isAutonomous()) {
			if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
				// We're in auto and we're on the blue alliance.
				Robot.lights.setMode(Robot.lights.MODE_BLUE_ALLIANCE);
			}
			else {
				// Otherwise, we must be on the red alliance.
				Robot.lights.setMode(Robot.lights.MODE_RED_ALLIANCE);
			}
		} else if (DriverStation.getInstance().isOperatorControl()) {
			// We're in teleop
			if (DriverStation.getInstance().getMatchTime() > 30) {
				if (Math.abs(Robot.oi.getOperatorLeftZAxis()) > 0.0  || Robot.oi.getOperatorRightZAxis() > 0.0 && !busy) {
					Robot.lights.setMode(Robot.lights.MODE_LIFT_FILL);
					Robot.lights.setAnalog(Robot.lift.getPosition()/Robot.lift.POS_TOP);
				}
				else if (Robot.oi.getOperatorXButton()) {
					Robot.lights.setMode(Robot.lights.MODE_BLINKY);
				}else if (Robot.oi.getOperatorYButton()) {
					Robot.lights.setMode(Robot.lights.MODE_RAINBOW);
				}else {
					Robot.lights.setMode(Robot.lights.MODE_RAINBOW_MISC);
				}
				
			} else {
				Robot.lights.setMode(Robot.lights.MODE_ENDGAME);
			}
		} else if (DriverStation.getInstance().isDisabled()) {
			// We're disabled.  This should trigger at the end of the match.
			Robot.lights.setMode(Robot.lights.MODE_FLASH_GREEN);
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