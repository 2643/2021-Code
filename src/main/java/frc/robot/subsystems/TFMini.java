/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

public class TFMini extends SubsystemBase {
  byte[] setExtTrigger = hexStringToByteArray("4257020000000040");
  byte[] extTrigger = hexStringToByteArray("4257020000000041");
  byte[] setInternalTrigger = hexStringToByteArray("4257020000000140");
  byte[] reset = hexStringToByteArray("4257020000000140");
  SerialPort port = new SerialPort(115200, Port.kMXP, 8, Parity.kNone, StopBits.kOne);
  private int holdCount;
  private int holdNum;

  /**
   * Creates a new TFMini.
   */
  public TFMini() {
    // Set the buffer to 2 frames
    port.setReadBufferSize(18);

  }

  private Object[] takeMeasurement() {
    Boolean headerOne = false;
    Boolean headerTwo = false;
    byte[] bytes = new byte[9];
    Object[] returnArray = new Object[5];
    returnArray[0] = false;
    returnArray[1] = -1;
    returnArray[2] = -1;
    returnArray[3] = "NULL";

    /**
     * Check that there are enough bytes to update the readout value.
     * Otherwise, hold off for up to 3 cycles (60ms).
     */
    if (port.getBytesReceived() < 9) {
      System.out.print("Not enough bytes using previous: " + holdNum + ", Recieved: ");
      // System.out.println(port.getBytesReceived());
      if (holdCount > 0) {
        holdCount -= 1;
        returnArray[0] = true;
        returnArray[1] = holdNum;
      }
      else {
        holdNum = -1;
      }
      return returnArray;
    }
    holdCount = 3;
    

    /**
     * If the buffer somehow has more than 18 bytes, discard excess.
     */
    if ((port.getBytesReceived()-18) >= 0) {
      port.read(port.getBytesReceived()-18);
    }

    /**
     * Read the buffer and find start of frame
     * Then extract the frame into "bytes" array
     */

    byte[] outputFrame = port.read(18);
    for (int index = 0; index < 18; index++) {
      if (headerOne && headerTwo) {
        for (int indexTwo = 0; indexTwo < 7; indexTwo++) {
          bytes[indexTwo + 2] = outputFrame[index + indexTwo];
        }
        break;
      }
      if (headerOne && !headerTwo) {
        if (outputFrame[index] == (byte) 89) {
          headerTwo = true;
          bytes[1] = outputFrame[index];
        } else {
          headerOne = false;
        }
      }
      if (!headerOne && outputFrame[index] == (byte) 89) {
        headerOne = true;
        bytes[0] = outputFrame[index];
      }
    }

    // System.out.println("frame " + String.join(",",
    // ByteArrayToStringList(outputFrame)));
    // System.out.println("bytes " + String.join(",",
    // ByteArrayToStringList(bytes)));i

    /**
     * Sum first 8 bytes for checksum
     */
    int accumulator = 0;
    for (int index = 0; index < 8; index++) {
      accumulator += bytes[index];
    }
    /**
     * Cast accumulator to remove excess data above lower 8 bits.
     */
    accumulator = (byte) accumulator;
    /**
     * Verify checksum
     */
    if (!(accumulator == bytes[8])) {
      System.out.print(String.format("%02x", accumulator) + " ");
      System.out.println(String.format("%02x", bytes[8]));
      System.out.println("CHECKSUM ERROR");
      return returnArray;
    }

    /**
     * Check that there's no erroneous data.
     */
    if (bytes[3] == (byte) 255 && bytes[2] == (byte) 255) {
      return returnArray;
    }

    /** 
     * Calculate distance and strength by bitshift and conversions
     */
    int distance = ((Byte.toUnsignedInt(bytes[3]) << 8) | (Byte.toUnsignedInt(bytes[2])));
    int strength = ((Byte.toUnsignedInt(bytes[5]) << 8) | (Byte.toUnsignedInt(bytes[4])));
    long mode = bytes[6];
    
    /**
     * Assemble return array
     */
    holdNum = distance;
    returnArray[0] = true;
    returnArray[1] = distance;
    returnArray[2] = strength;
    returnArray[3] = (int) mode;

    return returnArray;
  }

  /**
   * Tries to take up to five measurements returns if it finds it.
   * @return distance (cm) else -1
   */
  public int getDistance() {
    for (int tries = 0; tries < 5; tries++) {
      Object[] measurement = takeMeasurement();
      if ((boolean) measurement[0]) {
        return (int) measurement[1];
      }
    }
    return -1;
  }

  /**
   * Sends external trigger command
   */
  public void setExternalTrigger() {
    port.write(setExtTrigger, 8);
  }

  /**
   * Sends internal trigger command
   */
  public void setInternalTrigger() {
    port.write(setInternalTrigger, 8);
  }

  /**
   * Sends reset command
   */
  public void reset() {
    port.write(reset, 8);
  }

  /**
   * Sends trigger command
   */
  public void trigger() {
    port.write(extTrigger, 8);
  }

  /**
   * Converts from a hexstring to a byte array
   * @param s Hexstring to convert
   * @return byte array from param s
   */
  private static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
