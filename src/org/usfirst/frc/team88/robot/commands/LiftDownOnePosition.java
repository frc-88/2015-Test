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
public class LiftDownOnePosition extends Command {
	private double stillCount;
	private double lastPosition;
	private double target;
	private boolean done;
	
    public LiftDownOnePosition() {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double position = Robot.lift.getPosition();
    	
    	stillCount=0;
    	done = false;
    	
    	if (position > Lift.POS_FOURTOTES) {
    		target = Lift.POS_FOURTOTES;
    	} else if (position > Lift.POS_THREETOTES) {
        		target = Lift.POS_THREETOTES;
    	} else if (position > Lift.POS_TWOTOTES) {
    		target = Lift.POS_TWOTOTES;
    	} else if (position > Lift.POS_ONETOTE) {
    		target = Lift.POS_ONETOTE;
    	} else if (position > Lift.POS_TRAVEL) {
    		target = Lift.POS_TRAVEL;
    	} else if (position > Lift.POS_BOTTOM) {
    		target = Lift.POS_BOTTOM;
    	} else {
    		done = true;
    	}
    	
    	if (!done) {
    		Robot.lift.moveLift(-Lift.AUTO_SPEED);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double position = Robot.lift.getPosition();

    	if (!done && ((position <= target) || Robot.lift.atLowerLimit())) {
    		done = true;
    	}
		if (position == lastPosition) {
			if (++stillCount > 5) {
				done = true;
			}
		} else {
			lastPosition = position;
		}
    	
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
