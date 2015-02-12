package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance extends Command {
	private double targetLeft, targetRight;
	
    public DriveDistance(double left, double right) {
    	requires(Robot.drive);

    	// Possible math to calculate encoder count target based on desired distance
    	//desiredDistance = distance;
        //double wheelRotation = desiredDistance / (Drive.WHEEL_DIAMETER * Math.PI);
        //double gearRotation = wheelRotation / Drive.GEAR_RATIO;
        //targetCount = gearRotation * Drive.ENC_CYCLES_PER_REV * 2.0;

    	// for now, just pass in encoder counts
    	targetLeft = left;
    	targetRight = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setClosedLoopPosition();
    	Robot.drive.resetEncoders();
    	Robot.drive.driveSimple(targetLeft, targetRight, 0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.drive.getLeftPosition()) >= Math.abs(targetLeft) &&
    			Math.abs(Robot.drive.getRightPosition()) >= Math.abs(targetRight);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
