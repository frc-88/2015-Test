package org.usfirst.frc.team88.robot.subsystems;

/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Utility;

/**
 *
 * @author mallory orig David revised
 */
public class ITG3200_I2C{

	private static final byte kAddress = 0x68;
	private static final byte kPowerCtlRegister = 0x3E;
	private static final byte kPowerCtl_Measure = 0x00;
	private static final byte kDataRegister = 0x1D;
	private static final double kLSBpDpS = 14.375, kGyroConstant = 0.12485;
	private static double lastSystemTime;
	private static double m_xAxis, m_yAxis, m_zAxis, m_heading;
	private byte[] array = new byte[8];
	private final I2C m_i2c;

	public ITG3200_I2C() {
		
		m_i2c = new I2C(Port.kOnboard, kAddress);

		// Turn on the measurements
		m_i2c.write(kPowerCtlRegister, kPowerCtl_Measure);
		// Specify the data format to read
	}

	public void begin() {
		m_xAxis = 0;
		m_yAxis = 0;
		m_zAxis = 0;
		m_heading = 0;
		lastSystemTime = Utility.getFPGATime();
	}

	public void reset() {
		m_xAxis = 0;
		m_yAxis = 0;
		m_zAxis = 0;
		m_heading = 0;
		lastSystemTime = Utility.getFPGATime();
	}

	public void update() {
		byte[] buffer = new byte[6];
		double deltaTime = (Utility.getFPGATime() - lastSystemTime) / 1000;
		m_i2c.read(kDataRegister, buffer.length, buffer);
		int[] intBuffer = new int[6];
		for (int i = 0; i < buffer.length; i++) {
			intBuffer[i] = buffer[i];
		}
		m_xAxis = (((intBuffer[0] << 8)) | (intBuffer[1])) / kLSBpDpS * kGyroConstant;
		m_yAxis = (((intBuffer[2] << 8)) | (intBuffer[3])) / kLSBpDpS * kGyroConstant;
		m_zAxis = (((intBuffer[4] << 8)) | (intBuffer[5])) / kLSBpDpS * kGyroConstant;
		m_heading += m_zAxis * deltaTime;
	}

	int fromByteArray() {
	     return ByteBuffer.wrap(array).getInt();
	}
	
	public double getX() {
		return m_xAxis;
	}

	public double getY() {
		return m_yAxis;
	}

	public double getZ() {
		return m_zAxis;
	}

	public double getHeading() {
		return m_heading;
	}
	
}

