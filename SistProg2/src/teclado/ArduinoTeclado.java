/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teclado;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lcd.ArduinoLCD;

/**
 *
 * @author Rangel
 */
public class ArduinoTeclado {

    private final SerialPort serialPort;
    private ArduinoLCD lcd;
    private String bufferTeclado = "";

    public ArduinoTeclado(String puerto) {
        serialPort = new SerialPort(puerto);
        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
                    | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
        } catch (SerialPortException ex) {
            System.out.println("Error al asignar Listener al arduino teclado");
        }
    }

    public void setLCD(ArduinoLCD lcd) {
        this.lcd = lcd;
    }

    /**
     * Se activa cuando se recive info del sistema
     *
     * @param informacion
     */
    private void reciviendo(String informacion) {
        if (informacion.length() > 0) {
            char caracter = informacion.charAt(0);
            if (bufferTeclado.length() >= 16) {
                bufferTeclado = "";
            }
            bufferTeclado = caracter + bufferTeclado;
        }
        if (lcd != null) {
            lcd.mandarCadena(bufferTeclado);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Error esperando");
        }
    }

    private class PortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    reciviendo(receivedData);
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }

    }
}
