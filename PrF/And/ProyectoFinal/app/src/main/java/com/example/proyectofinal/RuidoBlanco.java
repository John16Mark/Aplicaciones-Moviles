package com.example.proyectofinal;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class RuidoBlanco {
    private final int SAMPLE_RATE = 22050; // Frecuencia de muestreo estándar
    private AudioTrack audioTrack;
    private boolean isPlaying = false;
    private volatile float freq;
    private float volume;
    private float lowVolume = 0.10f; // Volumen bajo para las transiciones
    private int pattern;
    private boolean isTone = false; // Por defecto, ruido blanco

    public RuidoBlanco(float volumen) {
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
            boolean leftOn = false,
                    rightOn = false;
            int estado_5 = 0;
            int estado_8 = 0;
            int estado_10 = 0;
            int sampleCounter = 0;

            while (isPlaying) {
                long samplesPerToggle = (long) (SAMPLE_RATE / freq);
                for (int i = 0; i < bufferSize; i += 2) { // Stereo buffer: intercalado Left/Right
                    // Actualizar estados según patrón
                    if (sampleCounter >= samplesPerToggle) {
                        sampleCounter = 0;
                        audioTrack.setStereoVolume(leftOn ? volume : lowVolume, rightOn ? volume : lowVolume);
                        switch (pattern) {
                            case 1:
                            case 4:// Ambos canales on/off
                                leftOn = !leftOn;
                                rightOn = leftOn;
                                break;
                            case 2:
                            case 3:// Alternar Left/Right
                                if (leftOn) {
                                    leftOn = false;
                                    rightOn = true;
                                } else {
                                    leftOn = true;
                                    rightOn = false;
                                }
                                break;
                            case 6:
                            case 7:// Alternar Right/Left (invertido de 2)
                                if (rightOn) {
                                    rightOn = false;
                                    leftOn = true;
                                } else {
                                    rightOn = true;
                                    leftOn = false;
                                }
                                break;
                            case 5:
                                if(estado_5 == 0) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_5 = 1;
                                } else if(estado_5 == 1) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_5 = 2;
                                } else if(estado_5 == 2) {
                                    leftOn = true;
                                    rightOn = true;
                                    estado_5 = 3;
                                } else if(estado_5 == 3) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_5 = 4;
                                } else if(estado_5 == 4) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_5 = 5;
                                } else if(estado_5 == 5) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_5 = 6;
                                } else if(estado_5 == 6) {
                                    leftOn = true;
                                    rightOn = true;
                                    estado_5 = 7;
                                } else {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_5 = 0;
                                }
                                break;
                            case 8:
                            case 9:
                                if(estado_8 == 0) {
                                    leftOn = false;
                                    rightOn = true;
                                    estado_8 = 1;
                                } else if(estado_8 == 1) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_8 = 2;
                                } else if(estado_8 == 2) {
                                    leftOn = true;
                                    rightOn = false;
                                    estado_8 = 3;
                                } else if(estado_8 == 3) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_8 = 4;
                                } else if(estado_8 == 4) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_8 = 5;
                                } else if(estado_8 == 5) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_8 = 6;
                                } else if(estado_8 == 6) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_8 = 7;
                                } else {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_8 = 0;
                                }
                                break;
                            case 10:
                                if(estado_10 == 0) {
                                    leftOn = true;
                                    rightOn = false;
                                    estado_10 = 1;
                                } else if(estado_10 == 1) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_10 = 2;
                                } else if(estado_10 == 2) {
                                    leftOn = false;
                                    rightOn = true;
                                    estado_10 = 3;
                                } else if(estado_10 == 3) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_10 = 4;
                                } else if(estado_10 == 4) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_10 = 5;
                                } else if(estado_10 == 5) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_10 = 6;
                                } else if(estado_10 == 6) {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_10 = 7;
                                } else {
                                    leftOn = false;
                                    rightOn = false;
                                    estado_10 = 0;
                                }
                                break;
                            default:
                                leftOn = rightOn = false; // Apagar ambos si patrón no reconocido
                        }
                    }
                    // Generar valores de ruido o silencio
                    if(isTone) {
                        short freq1 = (short) mapTableValueToFrequency(freq);
                        short freq2 = (short) mapTableValueToFrequency(freq);
                        short tono1 = (short)(Math.sin(2 * Math.PI * freq1 * i / SAMPLE_RATE) * Short.MAX_VALUE);
                        short tono2 = (short)(Math.sin(2 * Math.PI * freq2 * i / SAMPLE_RATE) * Short.MAX_VALUE);
                        buffer[i] = (leftOn ? tono1 : 0); // Canal izquierdo
                        buffer[i + 1] = (rightOn ? tono2 : 0); // Canal derecho
                    } else {
                        short noise1 = (short) ((Math.random() * 2 - 1) * Short.MAX_VALUE);
                        short noise2 = (short) ((Math.random() * 2 - 1) * Short.MAX_VALUE);
                        float cutoffFrequency = mapTableValueToFrequency(freq);
                        float alpha = (float) (2 * Math.PI * cutoffFrequency / SAMPLE_RATE);
                        short filteredNoise1 = (short) (alpha*noise1);
                        short filteredNoise2 = (short) (alpha*noise2);
                        //short filteredNoise = applyLowPassFilter(noise, cutoffFrequency, SAMPLE_RATE);
                        buffer[i] = (leftOn ? filteredNoise1 : 0); // Canal izquierdo
                        buffer[i + 1] = (rightOn ? filteredNoise2 : 0); // Canal derecho
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

    private short[] generateWhiteNoise(int size) {
        short[] noise = new short[size];
        for (int i = 0; i < size; i++) {
            noise[i] = (short) ((Math.random() * 2 - 1) * Short.MAX_VALUE);
        }
        return noise;
    }

    private short[] generateTone(float frequency, int size, float sampleRate) {
        short[] tone = new short[size];
        for (int i = 0; i < size; i++) {
            tone[i] = (short) (Math.sin(2 * Math.PI * frequency * i / sampleRate) * Short.MAX_VALUE);
        }
        return tone;
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
        float maxFrequency = 900.0f; // Agudo
        return minFrequency + (maxFrequency - minFrequency) * (value / 40.0f);
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
