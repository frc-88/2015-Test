package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {

	private double leftSpeed;
	private double rightSpeed;
	private double desiredDistance;
	private double initialLeftEncoder;
	private double currentLeftEncoder;
	private double targetCount;
	
    public DriveStraight(double speed,  double counts) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	leftSpeed = speed;
    	rightSpeed = speed;
    	//desiredDistance = distance;
    	
    	//double wheelRotation = desiredDistance / (Drive.WHEEL_DIAMETER * Math.PI);
    	//double gearRotation = wheelRotation / Drive.GEAR_RATIO;
    	//targetCount = gearRotation * Drive.ENC_CYCLES_PER_REV * 2.0;
    	
    	targetCount = counts;
    	
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double middle = 0.0;
    	initialLeftEncoder = Robot.drive.getLeftEncoderPosition();
    	Robot.drive.driveSimple(leftSpeed, rightSpeed, middle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double encoderCount;
    	
    	currentLeftEncoder = Robot.drive.getLeftEncoderPosition();
    	encoderCount = currentLeftEncoder - initialLeftEncoder;
        return (encoderCount >= targetCount);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.driveSimple(0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
