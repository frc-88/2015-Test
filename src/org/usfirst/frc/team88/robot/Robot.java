
package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team88.robot.commands.AutoBin;
import org.usfirst.frc.team88.robot.commands.AutoBinAndTote;
import org.usfirst.frc.team88.robot.commands.AutoDrive;
import org.usfirst.frc.team88.robot.commands.AutoTest;
import org.usfirst.frc.team88.robot.commands.AutoTote;
import org.usfirst.frc.team88.robot.commands.DriveEncoder;
import org.usfirst.frc.team88.robot.commands.DriveStraight;
import org.usfirst.frc.team88.robot.commands.DriveTurnLeft90;
import org.usfirst.frc.team88.robot.commands.DriveTurnRight90;
import org.usfirst.frc.team88.robot.commands.LiftToPosition;
import org.usfirst.frc.team88.robot.subsystems.Drive;
import org.usfirst.frc.team88.robot.subsystems.Arminator;
import org.usfirst.frc.team88.robot.subsystems.Lift;
import org.usfirst.frc.team88.robot.subsystems.Schtick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive drive;
	public static Lift lift;
	public static Arminator arminator;
	public static Schtick schtick;
	public static OI oi;
    private static SendableChooser autoSelector;
	private static Command autoCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		drive = new Drive();
		lift = new Lift();
		arminator = new Arminator();
		schtick = new Schtick();
		
		// do this last so OI can reference Robot subsystems
		oi = new OI();

		// set up the SmartDashboard
		// set up SendableChooser to select autonomous mode
		autoSelector = new SendableChooser();
		//autoSelector.addDefault("Testing", new AutoTest());
		autoSelector.addDefault("Drive", new AutoDrive());
		//autoSelector.addObject("Bin and Tote", new AutoBinAndTote());
		//autoSelector.addObject("Bin Only", new AutoBin());
		autoSelector.addObject("Tote Only", new AutoTote());
		SmartDashboard.putData("Autonomous Mode",autoSelector);
		
    	// Testing commands for auto drive
		SmartDashboard.putData("Forward 1m", new DriveStraight(1.0,1.0));
    	SmartDashboard.putData("Forward 2m",new DriveStraight(2.0,2.0));
    	SmartDashboard.putData("Forward 1000 cycles", new DriveEncoder(1000,-1000));
    	SmartDashboard.putData("Forward 2000 cycles",new DriveEncoder(2000,-2000));
    	SmartDashboard.putData("Left 90",new DriveTurnLeft90());
    	SmartDashboard.putData("Right 90",new DriveTurnRight90());

    	// Testing commands for auto lift
    	SmartDashboard.putData("Lift Bottom",new LiftToPosition(Lift.POS_BOTTOM));
    	SmartDashboard.putData("Lift Travel",new LiftToPosition(Lift.POS_TRAVEL));
    	SmartDashboard.putData("Lift One",new LiftToPosition(Lift.POS_ONETOTE));
    	SmartDashboard.putData("Lift Two",new LiftToPosition(Lift.POS_TWOTOTES));
    	SmartDashboard.putData("Lift Three",new LiftToPosition(Lift.POS_THREETOTES));
    	SmartDashboard.putData("Lift Top",new LiftToPosition(Lift.POS_TOP));

    	// Testing auto command groups
    	SmartDashboard.putData("Auto Test", new AutoTest());
    	SmartDashboard.putData("Auto Drive", new AutoDrive());
    	SmartDashboard.putData("Auto Bin and Tote", new AutoBinAndTote());
    	SmartDashboard.putData("Auto Bin Only", new AutoBin());
    	SmartDashboard.putData("Auto Tote Only", new AutoTote());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command
    	autoCommand = (Command) autoSelector.getSelected();
        if (autoCommand != null) autoCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autoCommand != null) autoCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
