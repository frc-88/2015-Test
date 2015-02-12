
package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team88.robot.commands.AutoBin;
import org.usfirst.frc.team88.robot.commands.AutoBinAndTote;
import org.usfirst.frc.team88.robot.commands.AutoTest;
import org.usfirst.frc.team88.robot.commands.AutoTote;
import org.usfirst.frc.team88.robot.subsystems.Drive;
import org.usfirst.frc.team88.robot.subsystems.FishingPole;
import org.usfirst.frc.team88.robot.subsystems.Lift;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Drive drive;
	public static Lift lift;
	public static FishingPole pole;
    private static SendableChooser autoSelector;
	private static Command autoCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		drive = new Drive();
		lift = new Lift();
//		pole = new FishingPole();
		
		// do this last so OI can reference Robot subsystems
		oi = new OI();

		// set up SendableChooser to select autonomous mode
		autoSelector = new SendableChooser();
		autoSelector.addDefault("test", new AutoTest());
		autoSelector.addObject("Bin and Tote", new AutoBinAndTote());
		autoSelector.addObject("Bin Only", new AutoBin());
		autoSelector.addObject("Tote Only", new AutoTote());
		
        SmartDashboard.putData(drive);
        SmartDashboard.putData(lift);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command
    	//autoCommand = (Command) autoSelector.getSelected();
    	autoCommand = new AutoTest();
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
