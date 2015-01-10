package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

/**
 *
 */
public class Drive extends Subsystem {
    private final CanTalonSRX lTalon, rTalon;

    public Drive() {
    	lTalon = new CanTalonSRX(Wiring.leftMotorController);
    	rTalon = new CanTalonSRX(Wiring.rightMotorController);
    }
    
    public void driveSimple(double left, double right) {        
        lTalon.set(left);
        rTalon.set(right);
        System.out.println("Left: " + left);
        System.out.println("Right: " + right);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
}

