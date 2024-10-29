#include <SoftwareSerial.h>
#include <Wire.h>
#include <Adafruit_MLX90614.h>

// Objetos
SoftwareSerial miBT(10, 11);
Adafruit_MLX90614 mlx = Adafruit_MLX90614();

// Pines
const int pinPot = A0;    // Pin del potenciómetro
const int pinHall = A1;   // Pin del sensor de efecto Hall
const int pinIR = 3;      // Pin del sensor IR (debe ser el Digital 2 o el 3)

// Variables
volatile unsigned int cont = 0;  // Contador de revoluciones
unsigned long previousMillis = 0;   // Variable para almacenar el tiempo anterior
unsigned int rpm = 0;               // Almacena el valor de RPM

// Interrupción que cuenta revoluciones
void IRinterrupt() {
  cont++;
}

void setup() {
  pinMode(pinIR, INPUT_PULLUP);
  mlx.begin();
  miBT.begin(38400);
  Serial.begin(9600);
}

void loop() {

  // Potenciómetro
  int lecturaPot = analogRead(pinPot);
  float voltajePot = lecturaPot * (5.0 / 1023.0);

  // Temperatura del objeto (MLX)
  float temperatura = mlx.readObjectTempC();

  // Sensor de efecto Hall
  int lecturaHall = analogRead(pinHall);
  float voltajeHall = lecturaHall * (5.0 / 1023.0); // Conversión a voltaje

  // Lectura cada segundo
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= 1000) {
    // Tacómetro
    detachInterrupt(digitalPinToInterrupt(pinIR));
    rpm = (cont / 2) * 60;  // Calcular RPM en un motor de dos aspas
    cont = 0;               // Resetar el contador
    attachInterrupt(digitalPinToInterrupt(pinIR), IRinterrupt, FALLING);
    previousMillis = currentMillis;

    // Imprimir datos en el Monitor Serial
    Serial.print("Voltaje Potenciometro: ");
    Serial.print(voltajePot);
    Serial.println(" V");

    Serial.print("Temperatura MLX: ");
    Serial.print(temperatura);
    Serial.println(" °C");

    Serial.print("Voltaje Sensor Hall: ");
    Serial.print(voltajeHall);
    Serial.println(" V");

    Serial.print("RPM: ");
    Serial.println(rpm);

    // Enviar datos al módulo Bluetooth
    String datos = String(voltajePot) + "," +
                   String(temperatura) + "," +
                   String(voltajeHall) + "," +
                   String(rpm) + "\n";
    miBT.print(datos);
  }
}
