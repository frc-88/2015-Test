package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveEncoder extends Command {
	private double targetLeft, targetRight;
	
    public DriveEncoder(double left, double right) {
    	requires(Robot.drive);

    	targetLeft = left;
    	targetRight = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setClosedLoopPositionStraight();
    	Robot.drive.resetEncoders();
    	Robot.drive.driveSimple(targetLeft, targetRight, 0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.drive.getLeftPosition()) >= (Math.abs(targetLeft) - Drive.BUFFER) &&
    			Math.abs(Robot.drive.getRightPosition()) >= (Math.abs(targetRight) - Drive.BUFFER);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
