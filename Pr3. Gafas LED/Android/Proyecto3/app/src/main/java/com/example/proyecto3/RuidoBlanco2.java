package com.example.proyecto3;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class RuidoBlanco2 {
    private final int SAMPLE_RATE = 22050; // Frecuencia de muestreo estándar
    private AudioTrack audioTrack;
    private boolean isPlaying = false;
    private volatile float freq;
    private float volume;
    private float lowVolume = 0.10f; // Volumen bajo para las transiciones
    private int pattern;
    private boolean isTone = false; // Por defecto, ruido blanco

    public RuidoBlanco2(float volumen) {
        volume = volumen;
    }

    public void startPlaying(float frecuenciaSonido, int patron) {
        freq = frecuenciaSonido;
        pattern = patron;
        isPlaying = true;

        // Genera un array de ruido blanco
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT) * 2;
        audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM
        );
        audioTrack.setVolume(volume);

        // Hilo para generar y reproducir el ruido
        startSoundThread(bufferSize);

        audioTrack.play();
    }

    private void startSoundThread(int bufferSize) {
        new Thread(() -> {
            short[] buffer = new short[bufferSize];
            boolean leftOn = false, rightOn = false;
            int sampleCounter = 0;

            while (isPlaying) {
                long samplesPerToggle = (long) (SAMPLE_RATE / freq);
                for (int i = 0; i < bufferSize; i += 2) { // Stereo buffer: intercalado Left/Right
                    // Actualizar estados según patrón
                    if (sampleCounter >= samplesPerToggle) {
                        sampleCounter = 0;
                        switch (pattern) {
                            case 1: // Ambos canales on/off
                                audioTrack.setStereoVolume(leftOn ? volume : lowVolume, rightOn ? volume : lowVolume);
                                leftOn = !leftOn;
                                rightOn = leftOn;
                                break;
                            case 2: // Alternar Left/Right
                                audioTrack.setStereoVolume(leftOn ? volume : lowVolume, rightOn ? volume : lowVolume);
                                if (leftOn) {
                                    leftOn = false;
                                    rightOn = true;
                                } else {
                                    leftOn = true;
                                    rightOn = false;
                                }
                                break;
                            case 6: // Alternar Right/Left (invertido de 2)
                                audioTrack.setStereoVolume(leftOn ? volume : lowVolume, rightOn ? volume : lowVolume);
                                if (rightOn) {
                                    rightOn = false;
                                    leftOn = true;
                                } else {
                                    rightOn = true;
                                    leftOn = false;
                                }
                                break;
                            default:
                                leftOn = rightOn = false; // Apagar ambos si patrón no reconocido
                        }
                    }
                    // Generar valores de ruido o silencio
                    if(isTone) {
                        buffer[i] = (short) (leftOn ? (Math.sin(2 * Math.PI * 500 * i / SAMPLE_RATE) * Short.MAX_VALUE) : 0); // Canal izquierdo
                        buffer[i + 1] = (short) (rightOn ? (Math.sin(2 * Math.PI * 500 * i / SAMPLE_RATE) * Short.MAX_VALUE) : 0); // Canal derecho
                    } else {
                        buffer[i] = (short) (leftOn ? (Math.random() * 2 - 1) * Short.MAX_VALUE : 0); // Canal izquierdo
                        buffer[i + 1] = (short) (rightOn ? (Math.random() * 2 - 1) * Short.MAX_VALUE : 0); // Canal derecho
                    }
                    sampleCounter++;
                }

                // Escribir al audioTrack
                audioTrack.write(buffer, 0, buffer.length);
            }
        }).start();
    }
    public void stopPlaying() {
        isPlaying = false;
        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

    public void updateFrequency(float newFreq) {
        freq = newFreq; // Cambiar frecuencia de manera segura.
    }

    public void updatePattern(int nuevoPatron) {
        pattern = nuevoPatron;
    }

    public void updateVolume(float volume) {
        if (audioTrack != null) {
            audioTrack.setVolume(volume);
        }
    }

    public void toggleSoundType(boolean tone) {
        isTone = tone;
    }
}