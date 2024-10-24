package com.example.proyecto2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Handler handler;

    // UUID estándar para SPP (Serial Port Profile)
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String HC_05_ADDRESS = "00:23:07:36:3B:CA"; // Dirección MAC del módulo HC-05
    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.txt1);
        tv2 = (TextView) findViewById(R.id.txt2);
        tv3 = (TextView) findViewById(R.id.txt3);
        tv4 = (TextView) findViewById(R.id.txt4);
        // Preguntar si el dispositivo tiene Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth no disponible", Toast.LENGTH_LONG).show();
            finish();
        }

        handler = new Handler(Looper.getMainLooper());
        checkBluetoothPermissions();
    }

    // Función para verificar y solicitar permisos
    private void checkBluetoothPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_PERMISSION);
        } else {
            connectToHC05();
        }
    }

    // Sobrescribir el método onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
            new ReadBluetoothData().start();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se pudo conectar al HC-05", Toast.LENGTH_LONG).show();
        }
    }

    // Procesar los datos recibidos desde el Bluetooth
    private void processIncomingData(String data) {
        // Dividir los datos si están en diferentes líneas
        Log.d("process", data);
        String[] lines = data.split(",");
        if(lines.length >=4) {
            String voltaje = lines[0] + " V";
            String temperatura = lines[1] + "°C";
            String Hall = lines[2] + "V";
            String revoluciones = lines[3] + "rpm";
            tv1.setText(voltaje);
            tv2.setText(temperatura);
            tv3.setText(Hall);
            tv4.setText(revoluciones);
        }
    }

    // Hilo para leer los datos desde el InputStream
    private class ReadBluetoothData extends Thread {
        private final StringBuilder dataBuffer = new StringBuilder(); // Buffer para construir el mensaje completo

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;
            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    String incomingData = new String(buffer, 0, bytes);
                    dataBuffer.append(incomingData);
                    int endOfLineIndex = dataBuffer.indexOf("\n");

                    if (endOfLineIndex > 0) {
                        String completeData = dataBuffer.substring(0, endOfLineIndex);
                        dataBuffer.delete(0, endOfLineIndex + 1);
                        Log.d("entrada2",completeData);

                        handler.post(() -> processIncomingData(completeData));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}