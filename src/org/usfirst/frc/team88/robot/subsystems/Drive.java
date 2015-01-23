package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Gyro ;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 */
public class Drive extends Subsystem {
    private final CanTalonSRX lTalon, rTalon, mTalon; 
    private final Gyro gyro;
//    private final Ultrasonic ultrasonic;
//    private DigitalOutput ping;
//    private DigitalInput echo;

    public Drive() {
    	lTalon = new CanTalonSRX(Wiring.leftMotorController);
    	rTalon = new CanTalonSRX(Wiring.rightMotorController);
    	mTalon = new CanTalonSRX(Wiring.middleMotorController);
    	gyro = new Gyro(Wiring.GYRO);
    	gyro.initGyro();
    }
    
    public void driveSimple(double left, double right, double middle) {        
        lTalon.Set(left);
        rTalon.Set(right);
        mTalon.Set(middle);        
        
        System.out.println("Left: " + left);
        System.out.println("Right: " + right);
        System.out.println("Middle: "+ middle);
        System.out.println(gyro.getAngle()+ " degrees");
    }
    
    public double getFacing() {
    	return gyro.getAngle();
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
}

