package com.example.proyecto3;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Grafica graphView;
    private Handler handler = new Handler();
    private List<float[]> points = new ArrayList<>();
    private List<float[]> newPoints = new ArrayList<>();
    private float currentT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphView = findViewById(R.id.grafica);

        // Inicializar los puntos
        points.add(new float[]{0, 15});
        points.add(new float[]{13, 3});
        points.add(new float[]{18, 5});
        points.add(new float[]{25, 3});
        points.add(new float[]{30, 6});
        points.add(new float[]{40, 3});
        points.add(new float[]{50, 5});
        points.add(new float[]{60, 30});

        float x = 0;
        float y = 0;
        float[] punto_previo = {0,0};
        float[] punto_siguiente = {0,0};
        while(x<=points.get(points.size()-1)[0]) {
            int newIndex = getIndexX(points, x);
            if(newIndex != -1) {
                punto_previo = points.get(newIndex);
                if(newIndex + 1 >= points.size())
                    punto_siguiente = points.get(points.size()-1);
                else
                    punto_siguiente = points.get(newIndex+1);
                y = punto_previo[1];
            } else {
                float pendiente = (punto_siguiente[1]-punto_previo[1])/(punto_siguiente[0]-punto_previo[0]);
                y += pendiente;
            }
            newPoints.add(new float[]{x,y});
            //Log.d("x,y", "("+x+", "+y+")");
            x++;
        }

        graphView.setPoints(newPoints);

        // Actualizar la gráfica cada segundo
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentT <= 60) {
                    graphView.updateGraph(currentT);
                    currentT += 1; // Incrementa el tiempo
                    handler.postDelayed(this, 500);
                }
            }
        }, 1000);
    }

    int getIndexX(List<float[]> lista, float x) {
        for(int i=0; i<lista.size(); i++) {
            if(lista.get(i)[0] == x){
                return i;
            }
        }
        return -1;
    }
}