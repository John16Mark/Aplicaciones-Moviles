package com.example.proyecto3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Grafica extends View {

    private Paint axisPaint, textPaint, titlePaint, linePaint, pointPaint, gridPaint;
    private List<float[]> points = new ArrayList<>();
    private float xMax = 60; // Máximo valor del eje t
    private float yMax = 40; // Máximo valor del eje y
    private int xPaso = 4;
    private int yPaso = 8;
    private String yEtiqueta = "Hz";
    private float currentT = 0; // Tiempo actual en la gráfica

    public Grafica(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(4f);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);

        titlePaint = new Paint();
        titlePaint.setColor(Color.rgb(0, 127, 219));
        titlePaint.setTextSize(30f);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5f);

        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);

        gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(2f);
    }

    public void setPoints(List<float[]> points) {
        this.points = points;
        invalidate();
    }

    public void updateGraph(float currentT) {
        this.currentT = currentT;
        invalidate();
    }

    public void setEjeY(int max, int paso) {
        this.yMax = max;
        this.yPaso = paso;
        this.yEtiqueta = "A";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float padding = 100;

        float graphWidth = width - padding * 2;
        float graphHeight = height - padding * 2;

        // Dibuja la cuadrícula
        for (int i = 0; i <= xMax; i += xPaso/2) {
            float x = padding + (i / xMax) * graphWidth;
            canvas.drawLine(x, height - padding, x, padding, gridPaint); // Líneas verticales
        }
        for (int i = 0; i <= yMax; i += yPaso/2) {
            float y = height - padding - (i / yMax) * graphHeight;
            canvas.drawLine(padding, y, width - padding, y, gridPaint); // Líneas horizontales
        }

        // Dibuja los ejes
        canvas.drawLine(padding, height - padding, width - padding, height - padding, axisPaint); // Eje t
        canvas.drawLine(padding, height - padding, padding, padding, axisPaint); // Eje Hz

        // Etiquetas de los ejes
        for (int i = 0; i <= xMax; i += xPaso) {
            float x = padding + (i / xMax) * graphWidth;
            canvas.drawText(String.valueOf(i), x - 15, height - padding + 40, textPaint);
        }

        for (int i = 0; i <= yMax; i += yPaso) {
            float y = height - padding - (i / yMax) * graphHeight;
            canvas.drawText(String.valueOf(i), padding - 50, y + 10, textPaint);
        }

        // Nombres de los ejes
        canvas.drawText("t", width - padding + 20, height - padding + 20, titlePaint);
        canvas.drawText(yEtiqueta, padding - 50, padding - 20, titlePaint);

        // Dibuja las líneas y puntos
        if (points != null && points.size() > 1) {
            float prevX = padding;
            float prevY = height - padding;

            for (float[] point : points) {
                float x = padding + (point[0] / xMax) * graphWidth;
                float y = height - padding - (point[1] / yMax) * graphHeight;

                if (point[0] <= currentT) {
                    canvas.drawLine(prevX, prevY, x, y, linePaint);
                    prevX = x;
                    prevY = y;
                }

                //canvas.drawCircle(x, y, 10, pointPaint);
            }
        }
    }
}