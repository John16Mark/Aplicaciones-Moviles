#include <SoftwareSerial.h>

SoftwareSerial miBT(10, 11);

void setup() {
  Serial.begin(9600);
  Serial.println("Listo");
  miBT.begin(38400);
}

void loop() {
  if (miBT.available()) // Leer BT y envía a Arduino
    Serial.write(miBT.read());

  if (Serial.available()) // Leer Arduino y envía a BT
    miBT.write(Serial.read());
}
