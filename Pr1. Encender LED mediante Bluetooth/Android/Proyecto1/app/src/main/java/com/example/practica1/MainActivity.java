package com.example.practica1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.UUID;

public class MainActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    private ToggleButton tbtn1;
    private ImageView img1;

    // UUID estándar para SPP (Serial Port Profile)
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String HC_05_ADDRESS = "00:23:07:36:3B:CA"; // Dirección MAC del módulo HC-05
    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        // Preguntar si el dispositivo tiene Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth no disponible", Toast.LENGTH_LONG).show();
            finish();
        }

        tbtn1 = (ToggleButton) findViewById(R.id.xtb1);
        img1 = (ImageView) findViewById(R.id.img1);

        tbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estado = preguntarEstado();
                if(estado.equals("1"))
                    sendData("0");
                else if(estado.equals("0"))
                    sendData("1");
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

            // Hilo que pregunte el estado constantemente
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String estado = preguntarEstado();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setInfo(estado);
                            }
                        });
                        try {
                            Thread.sleep(1000); // Esperar 1 segundo antes de leer de nuevo
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se pudo conectar al HC-05", Toast.LENGTH_LONG).show();
        }
    }

    private String preguntarEstado() {
        if (outputStream != null) {
            try {
                outputStream.write("L".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
            }
        }

        if (inputStream != null) {
            try {
                byte[] buffer = new byte[1024];
                int bytes = inputStream.read(buffer);
                return new String(buffer, 0, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al leer datos del serial", Toast.LENGTH_SHORT).show();
            }
        }
        return "";
    }

    private void sendData(String data) {
        if (outputStream != null) {
            try {
                outputStream.write(data.getBytes());
                if(data.equals("1")) {
                    Toast.makeText(this, "Encendiendo LED", Toast.LENGTH_SHORT).show();
                    img1.setImageResource(R.drawable.on);
                }
                if(data.equals("0")) {
                    Toast.makeText(this, "Apagando LED", Toast.LENGTH_SHORT).show();
                    img1.setImageResource(R.drawable.off);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al enviar datos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setInfo(String estado) {
        if (estado.equals("1")) {
            img1.setImageResource(R.drawable.on);
            tbtn1.setChecked(true);
        } else if (estado.equals("0")) {
            img1.setImageResource(R.drawable.off);
            tbtn1.setChecked(false);
        }
    }
}