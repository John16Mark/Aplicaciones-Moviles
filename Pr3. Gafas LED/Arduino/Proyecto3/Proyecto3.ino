#include <SoftwareSerial.h>

// Objetos
SoftwareSerial miBT(10, 11);

const int ledPin = 9; // Pin PWM donde está conectado el LED
int brightness = 0;   // Nivel de brillo inicial (0-255)
int fadeAmount = 5;   // Cantidad de cambio en cada paso

int LED = 0;
int valorMaxLED = 200 // No debe superar 255

void setup() {
  pinMode(ledPin, OUTPUT); // Configura el pin del LED como salida
  miBT.begin(38400);
  Serial.begin(9600);
}

void loop() {
  if (miBT.available()) {
    String receivedValue = miBT.readStringUntil('\n'); // Leer hasta un salto de línea
    Serial.print("Valor recibido: ");
    Serial.println(receivedValue); // Mostrar el valor en el monitor serial

    int valorRecibido =  receivedValue.toInt(); 
    LED = (valorMaxLED*valorRecibido)/100;
    Serial.print("Valor LED: ");
    Serial.println(LED); // Mostrar el valor en el monitor serial
  }

  // Ajusta el brillo del LED
  analogWrite(ledPin, LED);
/*
  // Cambia el brillo para el próximo ciclo
  brightness += fadeAmount;

  // Invierte la dirección del cambio cuando alcanza los límite                                          s
  if (brightness <= 0 || brightness >= 255) {
    fadeAmount = -fadeAmount; // Cambia la dirección
  }
*/
}
