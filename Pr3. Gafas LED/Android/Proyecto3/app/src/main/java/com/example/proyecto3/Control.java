package com.example.proyecto3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Control extends Activity {

    // Comunicación Bluetooth
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    SeekBar barFrecuencia, barLuz;

    // UUID estándar para SPP (Serial Port Profile)
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String HC_05_ADDRESS = "00:23:07:36:3B:CA"; // Dirección MAC del módulo HC-05
    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_control);

        // Preguntar si el dispositivo tiene Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth no disponible", Toast.LENGTH_LONG).show();
            finish();
        }

        barLuz = findViewById(R.id.barLuz);

        // Añadir un listener para detectar cambios en el progreso
        barLuz.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (outputStream != null) {
                    try {
                        String valueToSend = progress + "\n"; // Agrega un salto de línea si Arduino espera líneas completas
                        outputStream.write(valueToSend.getBytes());
                        Log.d("SeekBar", "Valor enviado: " + valueToSend);
                    } catch (IOException e) {
                        Log.e("SeekBar", "Error al enviar datos al HC-05", e);
                    }
                } else {
                    Log.d("SeekBar", "OutputStream no disponible");
                }
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
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se pudo conectar al HC-05", Toast.LENGTH_LONG).show();
        }
    }
}
