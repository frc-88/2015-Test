package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double left = Robot.oi.getDriverLeftVerticalAxis();
        double right = Robot.oi.getDriverRightVerticalAxis();
        double middle =Robot.oi.getDriverRightZAxis() - Robot.oi.getDriverLeftZAxis();

        if(Math.abs(left)<0.2){
        	left=0;
        }
        
        if(Math.abs(right)<0.2){
        	right=0;
        }
        if (Math.abs(middle) < 0.2) {
        	middle =0;
        }
        
        Robot.drive.driveSimple(left, right, middle);
        
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
