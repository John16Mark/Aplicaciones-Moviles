#include <SoftwareSerial.h>

// Objetos
SoftwareSerial miBT(10, 11);

const int pin_LED1 = 3; // LED izquierdo 1
const int pin_LED2 = 5; // LED izquierdo 2
const int pin_LED3 = 6; // LED derecho 1
const int pin_LED4 = 9; // LED derecho 2

int brightness = 0;   // Nivel de brillo inicial (0-255)
int LED_izq = 0;
int LED_der = 0;
int valorMaxLED = 80; // No debe superar 255
int codigoAnterior = -1;
int c5 = 0;
int c8 = 0;
int c9 = 0;

unsigned long previousMillis = 0; // Para manejar el parpadeo
int blinkInterval = 1000;         // Intervalo de parpadeo en ms (por defecto 1 Hz)
bool ledState = false;
int codigo = 0;                   // Código recibido desde la aplicación

void setup() {
  pinMode(pin_LED1, OUTPUT);
  pinMode(pin_LED2, OUTPUT);
  pinMode(pin_LED3, OUTPUT);
  pinMode(pin_LED4, OUTPUT);
  miBT.begin(38400);
  Serial.begin(9600);
  c5 = 0;
  c8 = 0;
  c9 = 0;
}

void loop() {
  // Leer datos del Bluetooth
  if (miBT.available()) {
    String receivedValue = miBT.readStringUntil('\n'); // Leer hasta un salto de línea
    Serial.print("Valor recibido: ");
    Serial.println(receivedValue);

    // Separar los valores
    int firstCommaIndex = receivedValue.indexOf(',');
    int secondCommaIndex = receivedValue.indexOf(',', firstCommaIndex + 1);

    if (firstCommaIndex > 0 && secondCommaIndex > firstCommaIndex) {
      String frecuenciaStr = receivedValue.substring(0, firstCommaIndex);
      String amplitudStr = receivedValue.substring(firstCommaIndex + 1, secondCommaIndex);
      String codigoStr = receivedValue.substring(secondCommaIndex + 1);

      int frecuencia = frecuenciaStr.toInt();
      int amplitud = amplitudStr.toInt();
      codigo = codigoStr.toInt();

      // Calcular el intervalo de parpadeo
      if (frecuencia > 0) {
        blinkInterval = 1000 / frecuencia;
      }

      // Calcular el brillo de los LEDs
      LED_izq = map(amplitud, 0, 100, 0, valorMaxLED);
      LED_der = map(amplitud, 0, 100, 0, valorMaxLED);

      Serial.print("Frecuencia (Hz): ");
      Serial.println(frecuencia);
      Serial.print("Amplitud (%): ");
      Serial.println(amplitud);
      Serial.print("Código: ");
      Serial.println(codigo);
    }
  }

  // Manejo del parpadeo basado en el código
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= blinkInterval) {
    previousMillis = currentMillis; // Actualizar tiempo previo
    ledState = !ledState;           // Cambiar el estado del LED

    if(codigo == 5 && codigo != codigoAnterior) {
      c5 = 0;
    }
    if(codigo == 8 && codigo != codigoAnterior) {
      c5 = 0;
    }
    if(codigo == 9 && codigo != codigoAnterior) {
      c5 = 0;
    }

    codigoAnterior = codigo;

    if (codigo == 2 || codigo == 4 || codigo == 7) {
      // Secuencia alternada: LEDs derechos y luego izquierdos
      if (ledState) {
        analogWrite(pin_LED1, 0);
        analogWrite(pin_LED2, 0);
        analogWrite(pin_LED3, LED_der);
        analogWrite(pin_LED4, LED_der);
      } else {
        analogWrite(pin_LED1, LED_izq);
        analogWrite(pin_LED2, LED_izq);
        analogWrite(pin_LED3, 0);
        analogWrite(pin_LED4, 0);
      }
    } else if (codigo == 3) {
      // Secuencia alternada: LEDs izquierdos y luego derechos
      if (!ledState) {
        analogWrite(pin_LED1, LED_izq);
        analogWrite(pin_LED2, LED_izq);
        analogWrite(pin_LED3, 0);
        analogWrite(pin_LED4, 0);
      } else {
        analogWrite(pin_LED1, 0);
        analogWrite(pin_LED2, 0);
        analogWrite(pin_LED3, LED_der);
        analogWrite(pin_LED4, LED_der);
      }
    } else if (codigo == 1 || codigo == 6) {
      // Secuencia sincronizada: todos los LEDs se encienden y apagan a la vez
      if (ledState) {
        analogWrite(pin_LED1, LED_izq);
        analogWrite(pin_LED2, LED_izq);
        analogWrite(pin_LED3, LED_der);
        analogWrite(pin_LED4, LED_der);
      } else {
        analogWrite(pin_LED1, 0);
        analogWrite(pin_LED2, 0);
        analogWrite(pin_LED3, 0);
        analogWrite(pin_LED4, 0);
      }
    } else if (codigo == 5) {
      if(c5 == 0) {
        if (ledState) {
          analogWrite(pin_LED1, LED_izq);
          analogWrite(pin_LED2, LED_izq);
          analogWrite(pin_LED3, LED_der);
          analogWrite(pin_LED4, LED_der);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c5 = 1;
        }
      } else if(c5 == 1) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c5 = 2;
        }
      } else if(c5 == 2) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c5 = 3;
        }
      } else {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c5 = 0;
        }
      }
    }
    // -------------------------------
    //                 8
    // -------------------------------
    else if (codigo == 8 || codigo == 10) {
      if(c8 == 0) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 1;
        }
      } else if(c8 == 1) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 2;
        }
      } else if(c8 == 2) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 3;
        }
      } else if(c8 == 3) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 4;
        }
      } else if(c8 == 4) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, LED_der);
          analogWrite(pin_LED4, LED_der);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 5;
        }
      } else if(c8 == 5) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 6;
        }
      } else if(c8 == 6) {
        if (ledState) {
          analogWrite(pin_LED1, LED_izq);
          analogWrite(pin_LED2, LED_izq);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 7;
        }
      } else {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c8 = 0;
        }
      }
    }
    // -------------------------------
    //                 9
    // -------------------------------
    else if (codigo == 9) {
      if(c9 == 0) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 1;
        }
      } else if(c9 == 1) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 2;
        }
      } else if(c9 == 2) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 3;
        }
      } else if(c9 == 3) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 4;
        }
      } else if(c9 == 4) {
        if (ledState) {
          analogWrite(pin_LED1, LED_izq);
          analogWrite(pin_LED2, LED_izq);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 5;
        }
      } else if(c9 == 5) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 6;
        }
      } else if(c9 == 6) {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, LED_der);
          analogWrite(pin_LED4, LED_der);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 7;
        }
      } else {
        if (ledState) {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
        } else {
          analogWrite(pin_LED1, 0);
          analogWrite(pin_LED2, 0);
          analogWrite(pin_LED3, 0);
          analogWrite(pin_LED4, 0);
          c9 = 0;
        }
      }
    }
    else {
      // Apagar todos los LEDs si el código no es válido
      analogWrite(pin_LED1, 0);
      analogWrite(pin_LED2, 0);
      analogWrite(pin_LED3, 0);
      analogWrite(pin_LED4, 0);
    }
  }
}
