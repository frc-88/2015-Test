package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;
import edu.wpi.first.wpilibj.Gyro ;

/**
 *
 */
public class Drive extends Subsystem {
    private final CanTalonSRX lTalon, rTalon, mTalon; 
    private final Gyro gyro;

    public Drive() {
    	lTalon = new CanTalonSRX(Wiring.leftMotorController);
    	rTalon = new CanTalonSRX(Wiring.rightMotorController);
    	mTalon = new CanTalonSRX(Wiring.middleMotorController);
    	gyro = new Gyro(Wiring.GYRO);
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
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
}

