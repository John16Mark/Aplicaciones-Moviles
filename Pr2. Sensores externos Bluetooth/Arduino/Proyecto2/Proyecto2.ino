#include <SoftwareSerial.h>

SoftwareSerial miBT(10, 11);

const int pinPot = A0;    // Pin del potenciómetro
const int pinTemp = A2;   // Pin del sensor LM35
const int pinHall = A5;   // Pin del sensor de efecto Hall
const int pinIR = 2;      // Pin del sensor IR (debe ser el Digital 2 o el 3)

volatile unsigned int counter = 0;  // Contador de revoluciones
unsigned long previousMillis = 0;   // Variable para almacenar el tiempo anterior
unsigned int rpm = 0;               // Almacena el valor de RPM

void IRinterrupt() {
  counter++;
}

void setup() {
  miBT.begin(38400);
  pinMode(pinIR, INPUT_PULLUP);
  Serial.begin(9600);
}

void loop() {
  // Leer y calcular el voltaje del potenciómetro
  int lecturaPot = analogRead(pinPot);
  float voltajePot = lecturaPot * (5.0 / 1023.0);
  // Leer y calcular la temperatura del sensor LM35
  int lecturaTemp = analogRead(pinTemp);
  float temperatura = lecturaTemp * (5.0 / 1023.0) * 100.0; // Convierte la lectura en grados Celsius
  // Leer el sensor de efecto Hall (interpretado como voltaje de salida)
  int lecturaHall = analogRead(pinHall);
  float voltajeHall = lecturaHall * (5.0 / 1023.0); // Conversión a voltaje

  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= 1000) {
    detachInterrupt(digitalPinToInterrupt(pinIR));
    rpm = (counter / 2) * 60;  // Calculate RPM
    counter = 0;
    attachInterrupt(digitalPinToInterrupt(pinIR), IRinterrupt, FALLING);
    previousMillis = currentMillis;

    Serial.print("Voltaje Potenciometro: ");
    Serial.print(voltajePot);
    Serial.println(" V");

    Serial.print("Temperatura LM35: ");
    Serial.print(temperatura);
    Serial.println(" °C");

    Serial.print("Voltaje Sensor Hall: ");
    Serial.print(voltajeHall);
    Serial.println(" V");

    Serial.print("RPM: ");
    Serial.println(rpm);

    // Enviar datos al módulo Bluetooth
    // Crear una cadena con todos los datos
    String datos = String(voltajePot) + "," +
                   String(temperatura) + "," +
                   String(voltajeHall) + "," +
                   String(rpm) + "\n";

    // Enviar todos los datos por Bluetooth de una vez
    miBT.print(datos);
  }
}
