/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun;

import java.util.Scanner;
import jssc.SerialPort;
import jssc.SerialPortException;
import lcd.ArduinoLCD;

/**
 *
 * @author Rangel
 */
public class arduino {

    public static void main(String[] args) throws InterruptedException, SerialPortException {
        ArduinoLCD lcd = new ArduinoLCD("COM7");
        lcd.mandarCadena("Anna");
    }
}
