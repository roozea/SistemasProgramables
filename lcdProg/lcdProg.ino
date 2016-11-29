#include <Servo.h>
#include "StepperMotor.h"
#include <LiquidCrystal.h>

Servo servo;

//PINES DEL STEP MOTOR
#define IN1 6
#define IN2 7
#define IN3 8
#define IN4 9

#define TOL_COLORES 25
#define BLANCO 600
#define NEGRO 10
#define VERDE 330
#define AZUL 240
#define AMARILLO 490

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

StepperMotor motor(IN1,IN2,IN3,IN4);

String lectura = "";


int val_foto = 0;
int grado = 0;
void setup() {
  lcd.begin(16, 2);
  lcd.clear();
  Serial.begin(9600);
  servo.attach(9);
  // Motor a pasos
  pinMode(IN1,OUTPUT);
  pinMode(IN2,OUTPUT);
  pinMode(IN3,OUTPUT);
  pinMode(IN4,OUTPUT);
}

void loop() {
  servo.write(grado);
  if (Serial.available()) {
    // Espera a que todo el mensaje llegue
    //lcd.clear();
    //lcd.print("Leyendo info..");
    delay(1500);
    
    // LEE LOS CARACTERES QUE SE LE MANDAN
    lectura = "";
    while (Serial.available() > 0) {
      char caracter = Serial.read();
      lectura += caracter;
    }
    // Verifica si se trata de una isntruccion para el motor
    if(lectura.length() > 1){
      if(lectura.charAt(0) == '-'){
        val_foto = analogRead(A0);
        lcd.clear();
        lcd.setCursor(0,0);
        //lcd.print(val_foto);
        if(val_foto >= (BLANCO-TOL_COLORES)
          && val_foto <= (BLANCO+TOL_COLORES)){
          lcd.print("BLANCO");
        }else if(val_foto >= (NEGRO-TOL_COLORES)
          && val_foto <= (NEGRO+TOL_COLORES)){
          lcd.print("NEGRO");
        }else if(val_foto >= (VERDE-TOL_COLORES)
          && val_foto <= (VERDE+TOL_COLORES)){
          lcd.print("VERDE");
        }else if(val_foto >= (AZUL-TOL_COLORES)
          && val_foto <= (AZUL+TOL_COLORES)){
          lcd.print("AZUL");
        }else if(val_foto >= (AMARILLO-TOL_COLORES)
          && val_foto <= (AMARILLO+TOL_COLORES)){
          lcd.print("AMARILLO");
        }else{
          lcd.print(val_foto);
          lcd.setCursor(0,1);
          lcd.print("No identificado");
        }
        delay(1500);
      }else if(lectura.charAt(0) == '~'){
        String dato = "";
        for(int i=1;i<lectura.length();i++){
          dato += lectura.charAt(i);
        }
        grado = dato.toInt();
        lcd.clear();
        lcd.setCursor(0,0);
        lcd.print("Mov el motor");
        lcd.setCursor(0,1);
        lcd.print(grado);
        lcd.setCursor(5,1);
        lcd.print("Grados");
      }else if(lectura.charAt(0) == '\\'){
        // Leer lumenes
        val_foto = analogRead(A0);
        val_foto -= 185;
        if(val_foto < 0){
          val_foto *= -1;
        }
        val_foto = (val_foto*50)/185;
        lcd.clear();
        lcd.print(val_foto);
        lcd.setCursor(5,0);
        lcd.print("LUMENES");
      }else{
        // Va a imprimir la lectura en el lc con un scroll
        lcd.clear();
        lcd.setCursor(0,0);
        // Imprime la cantidad de caracteres que va a
        // Mandar
        lcd.print("Num car");
        lcd.setCursor(8,0);
        lcd.print(lectura.length());

        delay(1500);
        
        lcd.clear();
        lcd.setCursor(16,0);
        lcd.autoscroll();
        int cont = 0;
        // Comienza la imprecion de los datos
        for(int i = 0;i<lectura.length();i++){
          lcd.print(lectura.charAt(i));
          delay(150);
          if(++cont == 16){
            cont = 0;
            lcd.clear();
            lcd.setCursor(16,0);
            lcd.autoscroll();
          }
        }
          
        delay(300);
      }
    }
    
  }
  
}
