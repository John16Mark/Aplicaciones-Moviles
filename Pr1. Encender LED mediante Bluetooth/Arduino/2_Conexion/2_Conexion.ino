#include <SoftwareSerial.h>

SoftwareSerial miBT(10, 11);

const int LED = 13;
const int boton = 2;

bool estadoLED = false;
bool estadoBotonAnterior = LOW;

char dato = 0;

void setup(){
  miBT.begin(38400);
  pinMode(LED, OUTPUT);
  pinMode(boton, INPUT);
  digitalWrite(LED, estadoLED);
}

void loop(){

  bool estadoBoton = digitalRead(boton);
  if (estadoBoton == HIGH && estadoBotonAnterior == LOW) {
    estadoLED = !estadoLED;
    digitalWrite(LED, estadoLED);
    miBT.write(estadoLED ? '1' : '0');
  }
  estadoBotonAnterior = estadoBoton;
  delay(50);

  if (miBT.available()){
    dato = miBT.read();
    if (dato == '1') {
      estadoLED = true;
      digitalWrite(LED, HIGH);
    }
    else if (dato == '0') {
      estadoLED = false;
      digitalWrite(LED, LOW);
    }
    else if (dato == 'L')  // Si es solicitud de lectura, imprime el estado del LED
      miBT.write(estadoLED ? '1' : '0');
  }
}