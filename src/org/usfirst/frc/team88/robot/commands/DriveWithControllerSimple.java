package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithControllerSimple extends Command {

	public DriveWithControllerSimple() {
		super("DriveWithControllerSimple");
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.setSpeedMode();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double left = Robot.oi.getDriverLeftVerticalAxis();
		double right = Robot.oi.getDriverRightVerticalAxis();
		double middle = Robot.oi.getDriverRightZAxis() - Robot.oi.getDriverLeftZAxis();

        SmartDashboard.putNumber("Left before: ", left);
        SmartDashboard.putNumber("Right before: ", right);
        SmartDashboard.putNumber("Middle before: ", middle);
        
        left = Robot.oi.applyDeadZone(left);
		right = Robot.oi.applyDeadZone(right);
		middle = Robot.oi.applyDeadZone(middle);
		
        SmartDashboard.putNumber("Left after: ", left);
        SmartDashboard.putNumber("Right after: ", right);
        SmartDashboard.putNumber("Middle after: ", middle);
        
		Robot.drive.driveSimple(left, right, middle);
		
		// Robot.drive.ultrasonic.ping();
		// SmartDashboard.putNumber("inches from detected object:", Robot.drive.ultrasonic.getRangeInches());
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
