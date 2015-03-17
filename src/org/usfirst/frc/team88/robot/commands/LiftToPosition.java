package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will move the lifter to specific positions based on encoder
 * counts from the bottom limit. 
 * 
 * NOTE: This command does rely on the position of the bottom limit being
 * zeroed by the Lift subsystem, which should happen the first time the
 * lift hits the lower limit. Run the "LiftDown" command to initialize.
 * 
 */
public class LiftToPosition extends Command {
	private double target;
	private boolean moveDown;
	private double stillCount;
	private double lastPosition;
	private boolean done = false;
	
    public LiftToPosition(double position) {
    	requires(Robot.lift);
    	
    	target = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double position = Robot.lift.getPosition();

    	stillCount=0;
    	done = false;
    	
    	if (position > target) {
    		moveDown = true;
        	Robot.lift.moveLift(-Lift.AUTO_SPEED);
    	} else if (position < target) {
    		moveDown = false;
        	Robot.lift.moveLift(Lift.AUTO_SPEED);
    	} else {
    		done = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double position = Robot.lift.getPosition();

    	if (!done && moveDown && ((position <= target) || Robot.lift.atLowerLimit())) {
    		done = true;
    	}
    	
    	if (!done && !moveDown && ((position >= target) || Robot.lift.atUpperLimit())) {
    		done = true;
    	}
    	
		if (position == lastPosition) {
			if (++stillCount > 5) {
				done = true;
			}
		} else {
			lastPosition = position;
		}
    	
    	// if we hit a limit stop no matter what
    	return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.moveLift(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
