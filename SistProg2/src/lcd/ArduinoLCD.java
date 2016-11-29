/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcd;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Comuncacion para escribir datos en la tabla
 *
 * @author Rangel
 */
public class ArduinoLCD {

    private final SerialPort serialPort;

    public ArduinoLCD(String puerto) {
        serialPort = new SerialPort(puerto);
    }

    public void mandarCadena(String cadena) {
        try{
        serialPort.openPort();//Open serial port
        Thread.sleep(1000);
        serialPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
//                    serialPort.writeBytes("b".getBytes());//Write data to port
//                    serialPort.writeBytes("c".getBytes());//Write data to port
        serialPort.writeString(cadena);
        serialPort.closePort();//Close serial port
        }catch(SerialPortException e){
            System.out.println("Error en comunicacion con puerto: ["+serialPort.getPortName()+"]");
        } catch (InterruptedException ex) {
            System.out.println("Error al interrumpir el codigo");
        }
    }
}
