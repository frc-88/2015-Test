package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
    private final CANTalon lTalon, rTalon;

    public Drive() {
    	lTalon = new CANTalon(Wiring.leftMotorController);
    	rTalon = new CANTalon(Wiring.rightleftMotorController);
    }
    
    public void driveSimple(double left, double right) {        
        lTalon.set(left);
        rTalon.set(right);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
}

