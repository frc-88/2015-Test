package org.usfirst.frc.team88.robot;

public class Wiring {
	// Practice bot flag for practice bot overrides
	// true = practice bot
	// false = competition bot
	public static boolean practiceRobot = true;
	
	// Drive
	public static final int leftMotorController = 7;
    public static final int leftMotorController2 = 2;
    public static final int rightMotorController = 3;
    public static final int rightMotorController2 = 4;
    public static final int middleMotorController = 5;
    public static final int suspensionSolenoidDown = 0;
    public static final int suspensionSolenoidUp = 1;
    
    // Lift
    public static final int liftMotorController = 8;
    public static final int liftSolenoidIn = 2;
    public static final int liftSolenoidOut = 3;
    public static final int liftLowerLimit = 4;
    public static final int liftUpperLimit = 5;
    
    // Arminator
    public static final int arminatorMotor = 0;

    // Schtick
	public static final int schtickSolenoidIn = 4;
	public static final int schtickSolenoidOut = 5;
	// Light
	public static final int lightDigitalOutPin1 = 0;
	public static final int lightDigitalOutPin2 = 2;
	public static final int lightDigitalOutPin3 = 3;
	public static final int lightPWMOutput = 1;

}
