package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {
	private double target;
	private double time;
	
    public DriveStraight(double distance, double seconds) {
    	requires(Robot.drive);

    	// 1000 count ~= 68cm
    	// so 1400 counts should be amount 1m
    	target = Drive.CYCLES_PER_METER * distance;
    	time = seconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(time);
    	Robot.drive.resetEncoders();
    	Robot.drive.setClosedLoopPosition();
    	Robot.drive.setP(Drive.POSITION_P_STRAIGHT);
    	Robot.drive.driveSimple(target, -target, 0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(Robot.drive.getLeftPosition()) >= (Math.abs(target) - Drive.BUFFER) &&
    			Math.abs(Robot.drive.getRightPosition()) >= (Math.abs(target) - Drive.BUFFER)) ||
    			this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
