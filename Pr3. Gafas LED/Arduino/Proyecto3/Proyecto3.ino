#include <SoftwareSerial.h>

// Objetos
SoftwareSerial miBT(10, 11);

const int LED1 = 9;
int brightness = 0;   // Nivel de brillo inicial (0-255)
int LED = 0;
int valorMaxLED = 255; // No debe superar 255

unsigned long previousMillis = 0; // Para manejar el parpadeo
int blinkInterval = 1000; // Intervalo de parpadeo en ms (por defecto 1 Hz)
bool ledState = false;

void setup() {
  pinMode(LED1, OUTPUT); // Configura el pin del LED como salida
  miBT.begin(38400);
  Serial.begin(9600);
}

void loop() {
  // Leer datos del Bluetooth
  if (miBT.available()) {
    String receivedValue = miBT.readStringUntil('\n'); // Leer hasta un salto de línea
    Serial.print("Valor recibido: ");
    Serial.println(receivedValue);

    // Separar los valores de frecuencia y amplitud
    int commaIndex = receivedValue.indexOf(','); // Encontrar la posición de la coma
    if (commaIndex > 0) { // Si hay una coma
      String frecuenciaStr = receivedValue.substring(0, commaIndex);
      String amplitudStr = receivedValue.substring(commaIndex + 1);

      int frecuencia = frecuenciaStr.toInt(); // Convertir frecuencia a entero
      int amplitud = amplitudStr.toInt();     // Convertir amplitud a entero

      // Calcular el intervalo de parpadeo
      if (frecuencia > 0) {
        blinkInterval = 1000 / frecuencia; // Convertir Hz a milisegundos
      }

      // Calcular el brillo del LED
      LED = map(amplitud, 0, 100, 0, valorMaxLED); // Mapear amplitud (0-100) a brillo (0-255)
      
      Serial.print("Frecuencia (Hz): ");
      Serial.println(frecuencia);
      Serial.print("Amplitud (%): ");
      Serial.println(amplitud);
      Serial.print("Brillo mapeado (0-255): ");
      Serial.println(LED);
    }
  }

  // Manejo del parpadeo del LED
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= blinkInterval) {
    previousMillis = currentMillis; // Actualizar tiempo previo
    ledState = !ledState;           // Cambiar el estado del LED
    if (ledState) {
      analogWrite(LED1, LED);       // Encender el LED con la intensidad calculada
    } else {
      analogWrite(LED1, 0);         // Apagar el LED
    }
  }
}
