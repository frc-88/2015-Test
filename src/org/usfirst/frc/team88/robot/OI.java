package org.usfirst.frc.team88.robot;

import org.usfirst.frc.team88.robot.commands.DriveDistance;
import org.usfirst.frc.team88.robot.commands.SchtickIn;
import org.usfirst.frc.team88.robot.commands.SchtickOut;
import org.usfirst.frc.team88.robot.commands.GrabberClose;
import org.usfirst.frc.team88.robot.commands.GrabberOpen;
import org.usfirst.frc.team88.robot.commands.ToggleMaxSpeed;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private static final int LEFT_HORIZ_AXIS = 0;
    private static final int LEFT_VERT_AXIS = 1;
    private static final int RIGHT_HORIZ_AXIS = 4;
    private static final int RIGHT_VERT_AXIS = 5;
    private static final int LEFT_Z_AXIS = 3;
    private static final int RIGHT_Z_AXIS = 2;

    private static final double DEADZONE = 0.4;

    //driver controller setup
    private Joystick driverController = new Joystick(0);
    private Button driverButtonA = new JoystickButton(driverController, 1);
    private Button driverButtonB = new JoystickButton(driverController, 2);
    private Button driverButtonX = new JoystickButton(driverController, 3);
    private Button driverButtonY = new JoystickButton(driverController, 4);
    private Button driverButtonLeftBumper = new JoystickButton(driverController, 5);
    private Button driverButtonRightBumper = new JoystickButton(driverController, 6);
    
    //operator controller setup
    private Joystick operatorController = new Joystick(1);
    private Button operatorButtonA = new JoystickButton(operatorController, 1);
    private Button operatorButtonB = new JoystickButton(operatorController, 2);
    private Button operatorButtonX = new JoystickButton(operatorController, 3);
    private Button operatorButtonY = new JoystickButton(operatorController, 4);
    private Button operatorButtonLeftBumper = new JoystickButton(operatorController, 5);
    private Button operatorButtonRightBumper = new JoystickButton(operatorController, 6);

    public OI () {
    	// test driver button configuration
    	//driverButtonA.whenPressed(new DriveDistance(0,0));
    	// 90 degree spin to the right
        //driverButtonB.whenPressed(new DriveDistance(1050,1050));
        driverButtonA.whenPressed(new SchtickIn());
        driverButtonB.whenPressed(new SchtickOut());
        driverButtonX.whenPressed(new ToggleMaxSpeed());
        
        operatorButtonA.whenPressed(new GrabberOpen());
        operatorButtonB.whenPressed(new GrabberClose());
    }
    
    // driver joysticks
    public double getDriverRightVerticalAxis() {
        return driverController.getRawAxis(RIGHT_VERT_AXIS);
    }
    
    public double getDriverRightHorizontalAxis() {
        return driverController.getRawAxis(RIGHT_HORIZ_AXIS);
    }
    
    public double getDriverLeftVerticalAxis() {
        return driverController.getRawAxis(LEFT_VERT_AXIS);
    }     
    
    public double getDriverLeftHorizontalAxis() {
        return driverController.getRawAxis(LEFT_HORIZ_AXIS);
    }

    public double getDriverLeftZAxis() {
        return driverController.getRawAxis(LEFT_Z_AXIS);
    }
    
    public double getDriverRightZAxis() {
        return driverController.getRawAxis(RIGHT_Z_AXIS);
    }
    
    // operator joysticks
    public double getOperatorRightVerticalAxis() {
        return operatorController.getRawAxis(RIGHT_VERT_AXIS);
    }
    
    public double getOperatorRightHorizontalAxis() {
        return operatorController.getRawAxis(RIGHT_HORIZ_AXIS);
    }
    
    public double getOperatorLeftVerticalAxis() {
        return operatorController.getRawAxis(LEFT_VERT_AXIS);
    }     
    
    public double getOperatorLeftHorizontalAxis() {
        return operatorController.getRawAxis(LEFT_HORIZ_AXIS);
    }
    
    public double getOperatorLeftZAxis() {
        return operatorController.getRawAxis(LEFT_Z_AXIS);
    }
    
    public double getOperatorRightZAxis() {
        return operatorController.getRawAxis(RIGHT_Z_AXIS);
    }
   
    // Utilities
    public double applyDeadZone(double value) {
    	if (Math.abs(value) < DEADZONE) {
    		return 0.0;
    	}

    	return value;
    }
}

