package com.example.proyecto3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // Elementos gráficos
    SeekBar barLuz;
    private Grafica grafica_frecSonido;
    private Grafica grafica_frecLED;
    private Grafica grafica_intensidadLED;
    private ToggleButton btnPausa;
    private ToggleButton btnRuido;

    // Tablas de valores
    private List<float[]> tabla_frecSonido = new ArrayList<>();
    private List<float[]> tabla_frecLED = new ArrayList<>();
    private List<float[]> tabla_intensidadLED = new ArrayList<>();
    
    private List<float[]> tabla_interpolada_frec_sonido = new ArrayList<>();
    private List<float[]> tabla_interpolada_frec_LED = new ArrayList<>();
    private List<float[]> tabla_interpolada_intensidad_LED = new ArrayList<>();

    // Comunicación Bluetooth
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    // UUID estándar para SPP (Serial Port Profile)
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String HC_05_ADDRESS = "00:23:07:36:3B:CA"; // Dirección MAC del módulo HC-05
    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;

    // handler para el hilo
    private Handler handler = new Handler();
    private Runnable runnable;

    int tam_paso = 500;    // Intervalo de muestreo en ms (medio segundo)
    private boolean ejecutar = true;
    private float maxLED = 1;           // Intensidad máxima del LED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Preguntar si el dispositivo tiene Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth no disponible", Toast.LENGTH_LONG).show();
            finish();
        }

        grafica_frecSonido = findViewById(R.id.grafica_frecSonido);
        grafica_frecLED = findViewById(R.id.grafica_frecLED);
        grafica_intensidadLED = findViewById(R.id.grafica_intensidadLED);
        btnPausa = findViewById(R.id.btnPausa);
        btnRuido = findViewById(R.id.btnRuido);

        grafica_intensidadLED.setEjeY(100, 20);

        btnPausa.setChecked(ejecutar);
        btnPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutar = !ejecutar;
                if (ejecutar) {
                    handler.postDelayed(runnable, tam_paso);
                }
            }
        });

        barLuz = findViewById(R.id.barLuz);
        barLuz.setProgress(100);
        barLuz.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxLED = (float)progress/100;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Este método se llama cuando el usuario comienza a interactuar con el SeekBar
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Este método se llama cuando el usuario deja de interactuar con el SeekBar
            }
        });

        // Inicializar los puntos
        tabla_frecSonido.add(new float[]{0, 15});
        tabla_frecSonido.add(new float[]{13, 3});
        tabla_frecSonido.add(new float[]{18, 5});
        tabla_frecSonido.add(new float[]{25, 3});
        tabla_frecSonido.add(new float[]{30, 6});
        tabla_frecSonido.add(new float[]{40, 3});
        tabla_frecSonido.add(new float[]{50, 5});
        tabla_frecSonido.add(new float[]{60, 30});

        // Inicializar los puntos
        tabla_frecLED.add(new float[]{0, 15});
        tabla_frecLED.add(new float[]{13, 3});
        tabla_frecLED.add(new float[]{18, 5});
        tabla_frecLED.add(new float[]{25, 3});
        tabla_frecLED.add(new float[]{30, 6});
        tabla_frecLED.add(new float[]{40, 3});
        tabla_frecLED.add(new float[]{50, 5});
        tabla_frecLED.add(new float[]{60, 30});

        // Inicializar los puntos
        tabla_intensidadLED.add(new float[]{0, 100});
        tabla_intensidadLED.add(new float[]{20, 60});
        tabla_intensidadLED.add(new float[]{50, 60});
        tabla_intensidadLED.add(new float[]{60, 100});

        puntosGrafica(tabla_frecSonido, tabla_interpolada_frec_sonido);
        puntosGrafica(tabla_frecLED, tabla_interpolada_frec_LED);
        puntosGrafica(tabla_intensidadLED, tabla_interpolada_intensidad_LED);

        grafica_frecSonido.setPoints(tabla_interpolada_frec_sonido);
        grafica_frecLED.setPoints(tabla_interpolada_frec_LED);
        grafica_intensidadLED.setPoints(tabla_interpolada_intensidad_LED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkBluetoothPermissions();
        }

        // Inicia la tarea
        iniciarEnvioBluetooth(); // Enviar valores cada segundo
    }

    // --------------------------------------------------------------
    //                      CONEXIÓN BLUETOOTH
    // --------------------------------------------------------------

    // Función para verificar y solicitar permisos
    @RequiresApi(api = Build.VERSION_CODES.S)
    private void checkBluetoothPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_PERMISSION);
        } else {
            connectToHC05();
        }
    }

    // Sobrescribir el método onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                connectToHC05();
            } else {
                Toast.makeText(this, "Sin permisos para Bluetooth", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void connectToHC05() {
        BluetoothDevice hc05 = bluetoothAdapter.getRemoteDevice(HC_05_ADDRESS);
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "No se concedió el permiso", Toast.LENGTH_LONG).show();
                return;
            }
            // Bluetooth
            bluetoothSocket = hc05.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            // Flujos I/O
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            Toast.makeText(this, "Conectado al HC-05", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se pudo conectar al HC-05", Toast.LENGTH_LONG).show();
        }
    }

    // --------------------------------------------------------------
    //                         PUNTOS GRÁFICA
    // --------------------------------------------------------------

    void puntosGrafica(List<float[]> tabla_original, List<float[]> tabla_interpolada) {
        float x = 0;
        float y = 0;
        float[] punto_previo = {0,0};
        float[] punto_siguiente = {0,0};
        while(x<=tabla_original.get(tabla_original.size()-1)[0]) {
            int newIndex = getIndexX(tabla_original, x);
            if(newIndex != -1) {
                punto_previo = tabla_original.get(newIndex);
                if(newIndex + 1 >= tabla_original.size())
                    punto_siguiente = tabla_original.get(tabla_original.size()-1);
                else
                    punto_siguiente = tabla_original.get(newIndex+1);
                y = punto_previo[1];
            } else {
                float pendiente = (punto_siguiente[1]-punto_previo[1])/(punto_siguiente[0]-punto_previo[0]);
                y += pendiente;
            }
            tabla_interpolada.add(new float[]{x,y});
            x++;
        }
    }

    int getIndexX(List<float[]> lista, float x) {
        for(int i=0; i<lista.size(); i++) {
            if(lista.get(i)[0] == x){
                return i;
            }
        }
        return -1;
    }

    // --------------------------------------------------------------
    //                         EJECUCIÓN
    // --------------------------------------------------------------

    // Enviar valores cada 500 ms
    private void iniciarEnvioBluetooth() {
        float segundosPorUnidad = 0.5f;
        float minutosAMilis = segundosPorUnidad * 1000f; // Conversión de minutos a milisegundos

        runnable = new Runnable() {
            int contador_milis = 0; // Tiempo transcurrido en ms
            int index_fsonido = 0;
            int index_fLED = 0;
            int index_int = 0;

            @Override
            public void run() {
                // Tiempo total en milisegundos hasta el último punto
                int milisMax = (int) (tabla_frecLED.get(tabla_frecLED.size() - 1)[0] * minutosAMilis);
                if (contador_milis <= milisMax && ejecutar) {
                    // Convertir el tiempo actual a minutos
                    float tiempoMinutos = contador_milis / minutosAMilis;

                    // ------------------------------------------
                    //             FRECUENCIA SONIDO
                    // ------------------------------------------
                    // Si el tiempo actual supera el próximo punto, avanzar el índice
                    if (index_fsonido < tabla_frecSonido.size() - 1 && tiempoMinutos >= tabla_frecSonido.get(index_fsonido + 1)[0])
                        index_fsonido++;
                    float frecuencia_sonido = calcularFrecuencia(tiempoMinutos, index_fsonido, tabla_frecSonido);

                    // ------------------------------------------
                    //                FRECUENCIA LED
                    // ------------------------------------------
                    if (index_fLED < tabla_frecLED.size() - 1 && tiempoMinutos >= tabla_frecLED.get(index_fLED + 1)[0])
                        index_fLED++;
                    float frecuencia_LED = calcularFrecuencia(tiempoMinutos, index_fLED, tabla_frecLED);

                    // ------------------------------------------
                    //               INTENSIDAD LED
                    // ------------------------------------------
                    if (index_int < tabla_intensidadLED.size() - 1 && tiempoMinutos >= tabla_intensidadLED.get(index_int + 1)[0])
                        index_int++;
                    float intensidad_LED = calcularFrecuencia(tiempoMinutos, index_int, tabla_intensidadLED);

                    Log.d("Muestra", "Tiempo: " + tiempoMinutos + " min");
                    Log.d("Muestra", "frecuencia_sonido: " + frecuencia_sonido);
                    Log.d("Muestra", "frecuencia_LED: " + frecuencia_LED);
                    Log.d("Muestra", "intensidad_LED: " + intensidad_LED);
                    float frecRel = (100*frecuencia_LED)/40;
                    float intRel = maxLED*intensidad_LED;
                    if(intRel > 100)
                        intRel = 100;
                    sendBluetoothValue(frecRel, intRel);

                    grafica_frecSonido.updateGraph((int) tiempoMinutos);
                    grafica_frecLED.updateGraph((int) tiempoMinutos);
                    grafica_intensidadLED.updateGraph((int) tiempoMinutos);

                    // Incrementar el contador y ejecutar el siguiente muestreo
                    contador_milis += tam_paso;
                    handler.postDelayed(this, tam_paso);
                }
            }
        };

        // Iniciar el Runnable
        handler.postDelayed(runnable, tam_paso);
    }

    private float calcularFrecuencia(float tiempoMinutos, int index, List<float[]> tabla) {
        if (index >= tabla.size() - 1) {
            return tabla.get(index)[1]; // Último valor
        } else {
            float[] p0 = tabla.get(index);
            float[] p1 = tabla.get(index + 1);
            float pendiente = (p1[1] - p0[1]) / (p1[0] - p0[0]);
            float b = p0[1] - pendiente * p0[0];
            return pendiente * tiempoMinutos + b; // Valor interpolado
        }
    }

    private void sendBluetoothValue(float frecuencia, float intensidad) {
        if (outputStream != null) {
            try {
                String valueToSend = frecuencia + "," + intensidad + "\n"; // Agrega un salto de línea si Arduino lo requiere
                outputStream.write(valueToSend.getBytes());
                //Log.d("Bluetooth", "Valor enviado: " + valueToSend);
            } catch (IOException e) {
                Log.e("Bluetooth", "Error al enviar datos al HC-05", e);
            }
        } else {
            Log.d("Bluetooth", "OutputStream no disponible");
        }
    }

}