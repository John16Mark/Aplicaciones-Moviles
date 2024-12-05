package com.example.proyecto3;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class RuidoBlanco {
    private final int SAMPLE_RATE = 44100; // Frecuencia de muestreo estándar
    private AudioTrack audioTrack;
    private boolean isPlaying = false;
    private float freq;
    private float volume;

    public RuidoBlanco(float volumen) {
        volume = volumen;
    }

    public void startPlaying(float frecuenciaSonido) {
        freq = frecuenciaSonido;
        isPlaying = true;

        // Genera un array de ruido blanco
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM
        );
        audioTrack.setVolume(volume);

        // Hilo para generar y reproducir el ruido
        new Thread(() -> {
            short[] buffer = new short[bufferSize];
            while (isPlaying) {
                // Generar ruido blanco
                short[] noise = generateWhiteNoise(bufferSize);

                // Ajustar frecuencia según el valor de la tabla
                float cutoffFrequency = mapTableValueToFrequency(freq);
                short[] filteredNoise = applyLowPassFilter(noise, cutoffFrequency, 44100);

                // Escribir el ruido filtrado en el buffer de audio
                audioTrack.write(filteredNoise, 0, filteredNoise.length);
                audioTrack.play();
            }
        }).start();

        audioTrack.play();
    }

    public void stopPlaying() {
        isPlaying = false;
        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

    private short[] generateWhiteNoise(int size) {
        short[] noise = new short[size];
        for (int i = 0; i < size; i++) {
            noise[i] = (short) ((Math.random() * 2 - 1) * Short.MAX_VALUE);
        }
        return noise;
    }

    private short[] applyLowPassFilter(short[] buffer, float cutoffFrequency, float sampleRate) {
        int size = buffer.length;
        short[] filteredBuffer = new short[size];
        float alpha = (float) (2 * Math.PI * cutoffFrequency / sampleRate);

        float y = 0; // Valor inicial del filtro
        for (int i = 0; i < size; i++) {
            y += alpha * (buffer[i] - y);
            filteredBuffer[i] = (short) y;
        }
        return filteredBuffer;
    }

    private float mapTableValueToFrequency(float value) {
        float minFrequency = 100.0f;  // Grave
        float maxFrequency = 5000.0f; // Agudo
        return minFrequency + (maxFrequency - minFrequency) * (value / 40.0f);
    }


    public void updateFrequency(float nuevaFrecuencia) {
        freq = nuevaFrecuencia;
    }

    public void updateVolume(float volume) {
        if (audioTrack != null) {
            audioTrack.setVolume(volume);
        }
    }
}
