package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnLeft90 extends Command {
	public DriveTurnLeft90(double distance) {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setClosedLoopPosition();
    	Robot.drive.resetEncoders();
    	// 90 degree spin to the left
    	Robot.drive.driveSimple(-Drive.CYCLES_PER_90DEGREES, -Drive.CYCLES_PER_90DEGREES, 0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.drive.getLeftPosition()) >= (Drive.CYCLES_PER_90DEGREES - Drive.BUFFER) &&
    			Math.abs(Robot.drive.getRightPosition()) >= (Drive.CYCLES_PER_90DEGREES - Drive.BUFFER);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
