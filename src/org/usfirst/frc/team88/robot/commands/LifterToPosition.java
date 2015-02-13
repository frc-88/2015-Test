package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will move the lifter to specific positions based on encoder
 * counts from the bottom limit. An array of encoded counts is used to 
 * specify five positions:
 * 
 * 	Position	Meaning
 * 		0		Bottom limit
 * 		1		Just off the ground...driving position
 * 		2		Above one tote...position to stack on one tote
 * 		3		Above two totes...position to stack on two totes
 * 		4		Top limit
 * 
 * NOTE: This command does rely on the position of the bottom limit being
 * captured by the Lift subsystem, which should happen the first time the
 * life hits the lower limit. Run the "LifterDown" command to initialize.
 * 
 */
public class LifterToPosition extends Command {
	public static final double SPEED = .5;
	public static final int BOTTOM = 0;
	public static final int TRAVEL = 1;
	public static final int ONETOTE = 2;
	public static final int TWOTOTES = 3;
	public static final int THREETOTES = 4;
	public static final int TOP = 5;
	
	private static final int [] POSITIONS = new int[] {0, 200, 400, 600, 800, 99999};
	
	private int target;
	private boolean moveDown;
	private boolean done = false;
	
    public LifterToPosition(int position) {
    	super("LifterToPosition");
    	requires(Robot.lift);
    	
    	target = POSITIONS[position];
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	int position = Robot.lift.getPosition();
    	
    	if (position > target) {
    		moveDown = true;
        	Robot.lift.moveLift(-SPEED);
    	} else if (position < target) {
    		moveDown = false;
        	Robot.lift.moveLift(SPEED);
    	} else {
    		done = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	int position = Robot.lift.getPosition();

    	if (!done && moveDown && (position <= target)) {
    		done = true;
    	}
    	
    	if (!done && !moveDown && (position >= target)) {
    		done = true;
    	}
    	
    	// if we hit a limit stop no matter what
    	return done || Robot.lift.atLowerLimit() || Robot.lift.atUpperLimit();
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