package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.Wiring;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithControllerClosed extends Command {

	public DriveWithControllerClosed() {
		super("DriveWithControllerSimple");
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.setClosedLoopSpeed();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double left = Robot.oi.getDriverLeftVerticalAxis();
		double right = Robot.oi.getDriverRightVerticalAxis();
		double middle = (Robot.oi.getDriverLeftZAxis() - Robot.oi.getDriverRightZAxis()) * (Wiring.practiceRobot ? -1 : 1);

		SmartDashboard.putNumber("Left stick: ", left);
		SmartDashboard.putNumber("Right stick: ", right);
		
        left = Robot.oi.applyDeadZone(left);
		right = Robot.oi.applyDeadZone(right);
		middle = Robot.oi.applyDeadZone(middle);

		//Robot.drive.driveMove(left, right, middle);
//		Robot.drive.driveMoveSteadyStrafe(left, right, middle);
		Robot.drive.driveMoveSteadyStrafeNavX(left, right, middle);
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
