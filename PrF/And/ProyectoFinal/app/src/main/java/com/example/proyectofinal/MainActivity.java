package com.example.proyectofinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private Intent intent;
    private Bundle bundle;

    private ListView lv;

    List<float[]> a0_frecsonido = new ArrayList<>();
    List<float[]> a0_frecled = new ArrayList<>();
    List<float[]> a0_intled = new ArrayList<>();
    List<float[]> a0_patrones = new ArrayList<>();

    List<float[]> a1_frecsonido = new ArrayList<>();
    List<float[]> a1_frecled = new ArrayList<>();
    List<float[]> a1_intled = new ArrayList<>();
    List<float[]> a1_patrones = new ArrayList<>();

    List<float[]> a2_frecsonido = new ArrayList<>();
    List<float[]> a2_frecled = new ArrayList<>();
    List<float[]> a2_intled = new ArrayList<>();
    List<float[]> a2_patrones = new ArrayList<>();

    List<float[]> a3_frecsonido = new ArrayList<>();
    List<float[]> a3_frecled = new ArrayList<>();
    List<float[]> a3_intled = new ArrayList<>();
    List<float[]> a3_patrones = new ArrayList<>();

    List<float[]> a4_frecsonido = new ArrayList<>();
    List<float[]> a4_frecled = new ArrayList<>();
    List<float[]> a4_intled = new ArrayList<>();
    List<float[]> a4_patrones = new ArrayList<>();

    List<float[]> a5_frecsonido = new ArrayList<>();
    List<float[]> a5_frecled = new ArrayList<>();
    List<float[]> a5_intled = new ArrayList<>();
    List<float[]> a5_patrones = new ArrayList<>();

    List<float[]> a6_frecsonido = new ArrayList<>();
    List<float[]> a6_frecled = new ArrayList<>();
    List<float[]> a6_intled = new ArrayList<>();
    List<float[]> a6_patrones = new ArrayList<>();
    //-----------------------------------------------------------
    List<float[]> b0_frecsonido = new ArrayList<>();
    List<float[]> b0_frecled = new ArrayList<>();
    List<float[]> b0_intled = new ArrayList<>();
    List<float[]> b0_patrones = new ArrayList<>();

    List<float[]> b1_frecsonido = new ArrayList<>();
    List<float[]> b1_frecled = new ArrayList<>();
    List<float[]> b1_intled = new ArrayList<>();
    List<float[]> b1_patrones = new ArrayList<>();

    List<float[]> b2_frecsonido = new ArrayList<>();
    List<float[]> b2_frecled = new ArrayList<>();
    List<float[]> b2_intled = new ArrayList<>();
    List<float[]> b2_patrones = new ArrayList<>();

    List<float[]> b3_frecsonido = new ArrayList<>();
    List<float[]> b3_frecled = new ArrayList<>();
    List<float[]> b3_intled = new ArrayList<>();
    List<float[]> b3_patrones = new ArrayList<>();

    List<float[]> b4_frecsonido = new ArrayList<>();
    List<float[]> b4_frecled = new ArrayList<>();
    List<float[]> b4_intled = new ArrayList<>();
    List<float[]> b4_patrones = new ArrayList<>();

    List<float[]> b5_frecsonido = new ArrayList<>();
    List<float[]> b5_frecled = new ArrayList<>();
    List<float[]> b5_intled = new ArrayList<>();
    List<float[]> b5_patrones = new ArrayList<>();

    List<float[]> b6_frecsonido = new ArrayList<>();
    List<float[]> b6_frecled = new ArrayList<>();
    List<float[]> b6_intled = new ArrayList<>();
    List<float[]> b6_patrones = new ArrayList<>();
    //-----------------------------------------------------------
    List<float[]> ab0_frecsonido = new ArrayList<>();
    List<float[]> ab0_frecled = new ArrayList<>();
    List<float[]> ab0_intled = new ArrayList<>();
    List<float[]> ab0_patrones = new ArrayList<>();

    List<float[]> ab1_frecsonido = new ArrayList<>();
    List<float[]> ab1_frecled = new ArrayList<>();
    List<float[]> ab1_intled = new ArrayList<>();
    List<float[]> ab1_patrones = new ArrayList<>();

    List<float[]> ab2_frecsonido = new ArrayList<>();
    List<float[]> ab2_frecled = new ArrayList<>();
    List<float[]> ab2_intled = new ArrayList<>();
    List<float[]> ab2_patrones = new ArrayList<>();

    List<float[]> ab3_frecsonido = new ArrayList<>();
    List<float[]> ab3_frecled = new ArrayList<>();
    List<float[]> ab3_intled = new ArrayList<>();
    List<float[]> ab3_patrones = new ArrayList<>();

    List<float[]> ab4_frecsonido = new ArrayList<>();
    List<float[]> ab4_frecled = new ArrayList<>();
    List<float[]> ab4_intled = new ArrayList<>();
    List<float[]> ab4_patrones = new ArrayList<>();

    List<float[]> ab5_frecsonido = new ArrayList<>();
    List<float[]> ab5_frecled = new ArrayList<>();
    List<float[]> ab5_intled = new ArrayList<>();
    List<float[]> ab5_patrones = new ArrayList<>();

    List<float[]> ab6_frecsonido = new ArrayList<>();
    List<float[]> ab6_frecled = new ArrayList<>();
    List<float[]> ab6_intled = new ArrayList<>();
    List<float[]> ab6_patrones = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListaEntrada> al = new ArrayList<ListaEntrada>();

        a0_frecsonido.add(new float[]{0, 18});
        a0_frecsonido.add(new float[]{5, 7});
        a0_frecsonido.add(new float[]{12, 7});
        a0_frecsonido.add(new float[]{15, 40});

        a0_frecled.add(new float[]{0, 18});
        a0_frecled.add(new float[]{5, 7});
        a0_frecled.add(new float[]{12, 7});
        a0_frecled.add(new float[]{15, 40});

        a0_intled.add(new float[]{0, 50});
        a0_intled.add(new float[]{3, 100});
        a0_intled.add(new float[]{5, 80});
        a0_intled.add(new float[]{12, 50});
        a0_intled.add(new float[]{13, 100});
        a0_intled.add(new float[]{14, 100});
        a0_intled.add(new float[]{15, 50});

        a0_patrones.add(new float[]{0, 1});

        al.add(new ListaEntrada(R.drawable.a0,
                "A0",
                "15 minutos de acondicionamiento de relajación.",
                a0_frecsonido,
                a0_frecled,
                a0_intled,
                a0_patrones));

        a1_frecsonido.add(new float[]{0, 18});
        a1_frecsonido.add(new float[]{4, 10});
        a1_frecsonido.add(new float[]{7, 5});
        a1_frecsonido.add(new float[]{20, 5});
        a1_frecsonido.add(new float[]{22, 18});

        a1_frecled.add(new float[]{0, 18});
        a1_frecled.add(new float[]{4, 10});
        a1_frecled.add(new float[]{7, 5});
        a1_frecled.add(new float[]{20, 5});
        a1_frecled.add(new float[]{22, 18});

        a1_intled.add(new float[]{0, 50});
        a1_intled.add(new float[]{2, 100});
        a1_intled.add(new float[]{7, 60});
        a1_intled.add(new float[]{20, 60});
        a1_intled.add(new float[]{21, 100});
        a1_intled.add(new float[]{22, 0});

        a1_patrones.add(new float[]{0, 2});
        a1_patrones.add(new float[]{1, 1});
        a1_patrones.add(new float[]{2, 6});
        a1_patrones.add(new float[]{3, 5});
        a1_patrones.add(new float[]{4, 2});
        a1_patrones.add(new float[]{5, 1});

        al.add(new ListaEntrada(R.drawable.a1,
                "A1",
                "22 minutos de relajación/aprendizaje profundo.",
                a1_frecsonido,
                a1_frecled,
                a1_intled,
                a1_patrones));

        a2_frecsonido.add(new float[]{0, 8});
        a2_frecsonido.add(new float[]{3, 30});
        a2_frecsonido.add(new float[]{6, 6});
        a2_frecsonido.add(new float[]{9, 20});
        a2_frecsonido.add(new float[]{12, 4});
        a2_frecsonido.add(new float[]{15, 18});
        a2_frecsonido.add(new float[]{18, 6});
        a2_frecsonido.add(new float[]{21, 24});
        a2_frecsonido.add(new float[]{24, 10});
        a2_frecsonido.add(new float[]{27, 22});
        a2_frecsonido.add(new float[]{29, 6});
        a2_frecsonido.add(new float[]{30, 30});

        a2_frecled.add(new float[]{0, 8});
        a2_frecled.add(new float[]{3, 30});
        a2_frecled.add(new float[]{6, 6});
        a2_frecled.add(new float[]{9, 20});
        a2_frecled.add(new float[]{12, 4});
        a2_frecled.add(new float[]{15, 18});
        a2_frecled.add(new float[]{18, 6});
        a2_frecled.add(new float[]{21, 24});
        a2_frecled.add(new float[]{24, 10});
        a2_frecled.add(new float[]{27, 22});
        a2_frecled.add(new float[]{29, 6});
        a2_frecled.add(new float[]{30, 30});

        a2_intled.add(new float[]{0, 100});
        a2_intled.add(new float[]{3, 75});
        a2_intled.add(new float[]{27, 75});
        a2_intled.add(new float[]{30, 100});

        a2_patrones.add(new float[]{0, 2});
        a2_patrones.add(new float[]{3, 5});
        a2_patrones.add(new float[]{6, 4});
        a2_patrones.add(new float[]{9, 2});
        a2_patrones.add(new float[]{12, 5});
        a2_patrones.add(new float[]{15, 4});
        a2_patrones.add(new float[]{18, 2});
        a2_patrones.add(new float[]{21, 5});
        a2_patrones.add(new float[]{24, 4});
        a2_patrones.add(new float[]{27, 2});
        a2_patrones.add(new float[]{29, 4});

        al.add(new ListaEntrada(R.drawable.a2,
                "A2",
                "30 minutos ejercicio de relajación.",
                a2_frecsonido,
                a2_frecled,
                a2_intled,
                a2_patrones));

        a3_frecsonido.add(new float[]{0, 18});
        a3_frecsonido.add(new float[]{3, 24});
        a3_frecsonido.add(new float[]{6, 6});
        a3_frecsonido.add(new float[]{9, 30});
        a3_frecsonido.add(new float[]{12, 20});
        a3_frecsonido.add(new float[]{15, 36});
        a3_frecsonido.add(new float[]{18, 18});
        a3_frecsonido.add(new float[]{21, 34});
        a3_frecsonido.add(new float[]{24, 18});
        a3_frecsonido.add(new float[]{27, 36});
        a3_frecsonido.add(new float[]{29, 20});
        a3_frecsonido.add(new float[]{30, 36});

        a3_frecled.add(new float[]{0, 18});
        a3_frecled.add(new float[]{3, 24});
        a3_frecled.add(new float[]{6, 6});
        a3_frecled.add(new float[]{9, 30});
        a3_frecled.add(new float[]{12, 20});
        a3_frecled.add(new float[]{15, 36});
        a3_frecled.add(new float[]{18, 18});
        a3_frecled.add(new float[]{21, 34});
        a3_frecled.add(new float[]{24, 18});
        a3_frecled.add(new float[]{27, 36});
        a3_frecled.add(new float[]{29, 20});
        a3_frecled.add(new float[]{30, 36});

        a3_intled.add(new float[]{0, 100});
        a3_intled.add(new float[]{30, 100});

        a3_patrones.add(new float[]{0, 2});
        a3_patrones.add(new float[]{3, 1});
        a3_patrones.add(new float[]{6, 5});
        a3_patrones.add(new float[]{9, 1});
        a3_patrones.add(new float[]{12, 4});
        a3_patrones.add(new float[]{15, 1});
        a3_patrones.add(new float[]{18, 7});
        a3_patrones.add(new float[]{21, 1});
        a3_patrones.add(new float[]{24, 6});
        a3_patrones.add(new float[]{27, 1});
        a3_patrones.add(new float[]{29, 2});

        al.add(new ListaEntrada(R.drawable.a3,
                "A3",
                "30 minutos de Onda Beta.",
                a3_frecsonido,
                a3_frecled,
                a3_intled,
                a3_patrones));

        a4_frecsonido.add(new float[]{0, 18});
        a4_frecsonido.add(new float[]{2, 10});
        a4_frecsonido.add(new float[]{4, 18});
        a4_frecsonido.add(new float[]{6, 10});
        a4_frecsonido.add(new float[]{8, 16});
        a4_frecsonido.add(new float[]{10, 10});
        a4_frecsonido.add(new float[]{12, 16});
        a4_frecsonido.add(new float[]{15, 10});
        a4_frecsonido.add(new float[]{18, 16});
        a4_frecsonido.add(new float[]{20, 10});
        a4_frecsonido.add(new float[]{22, 16});
        a4_frecsonido.add(new float[]{25, 10});

        a4_frecled.add(new float[]{0, 18});
        a4_frecled.add(new float[]{2, 10});
        a4_frecled.add(new float[]{4, 18});
        a4_frecled.add(new float[]{6, 10});
        a4_frecled.add(new float[]{8, 16});
        a4_frecled.add(new float[]{10, 10});
        a4_frecled.add(new float[]{12, 16});
        a4_frecled.add(new float[]{15, 10});
        a4_frecled.add(new float[]{18, 16});
        a4_frecled.add(new float[]{20, 10});
        a4_frecled.add(new float[]{22, 16});
        a4_frecled.add(new float[]{25, 10});

        a4_intled.add(new float[]{0, 75});
        a4_intled.add(new float[]{1, 100});
        a4_intled.add(new float[]{2, 75});
        a4_intled.add(new float[]{4, 100});
        a4_intled.add(new float[]{6, 75});
        a4_intled.add(new float[]{9, 100});
        a4_intled.add(new float[]{12, 75});
        a4_intled.add(new float[]{15, 100});
        a4_intled.add(new float[]{18, 75});
        a4_intled.add(new float[]{21, 100});
        a4_intled.add(new float[]{25, 75});

        a4_patrones.add(new float[]{0, 1});
        a4_patrones.add(new float[]{5, 2});
        a4_patrones.add(new float[]{8, 6});
        a4_patrones.add(new float[]{10, 4});
        a4_patrones.add(new float[]{15, 1});
        a4_patrones.add(new float[]{18, 2});
        a4_patrones.add(new float[]{20, 6});
        a4_patrones.add(new float[]{22, 4});
        a4_patrones.add(new float[]{24, 1});

        al.add(new ListaEntrada(R.drawable.a4,
                "A4",
                "25 minutos de Alfa-Beta.",
                a4_frecsonido,
                a4_frecled,
                a4_intled,
                a4_patrones));

        a5_frecsonido.add(new float[]{0, 12});
        a5_frecsonido.add(new float[]{5, 5});
        a5_frecsonido.add(new float[]{10, 12});
        a5_frecsonido.add(new float[]{15, 5});
        a5_frecsonido.add(new float[]{20, 12});
        a5_frecsonido.add(new float[]{25, 5});
        a5_frecsonido.add(new float[]{30, 12});

        a5_frecled.add(new float[]{0, 12});
        a5_frecled.add(new float[]{5, 5});
        a5_frecled.add(new float[]{10, 12});
        a5_frecled.add(new float[]{15, 5});
        a5_frecled.add(new float[]{20, 12});
        a5_frecled.add(new float[]{25, 5});
        a5_frecled.add(new float[]{30, 12});

        a5_intled.add(new float[]{0, 100});
        a5_intled.add(new float[]{5, 75});
        a5_intled.add(new float[]{10, 100});
        a5_intled.add(new float[]{15, 75});
        a5_intled.add(new float[]{20, 100});
        a5_intled.add(new float[]{25, 75});
        a5_intled.add(new float[]{30, 100});

        a5_patrones.add(new float[]{0, 1});

        al.add(new ListaEntrada(R.drawable.a5,
                "A5",
                "30 minutos de profunda onda Alfa-Theta.",
                a5_frecsonido,
                a5_frecled,
                a5_intled,
                a5_patrones));

        a6_frecsonido.add(new float[]{0, 15});
        a6_frecsonido.add(new float[]{10, 10});
        a6_frecsonido.add(new float[]{15, 10});
        a6_frecsonido.add(new float[]{18, 7});
        a6_frecsonido.add(new float[]{25, 7});
        a6_frecsonido.add(new float[]{28, 5});
        a6_frecsonido.add(new float[]{36, 5});
        a6_frecsonido.add(new float[]{38, 1});
        a6_frecsonido.add(new float[]{45, 1});

        a6_frecled.add(new float[]{0, 15});
        a6_frecled.add(new float[]{10, 10});
        a6_frecled.add(new float[]{15, 10});
        a6_frecled.add(new float[]{18, 7});
        a6_frecled.add(new float[]{25, 7});
        a6_frecled.add(new float[]{28, 5});
        a6_frecled.add(new float[]{36, 5});
        a6_frecled.add(new float[]{38, 1});
        a6_frecled.add(new float[]{45, 1});

        a6_intled.add(new float[]{0, 100});
        a6_intled.add(new float[]{40, 20});
        a6_intled.add(new float[]{45, 0});

        a6_patrones.add(new float[]{0, 1});

        al.add(new ListaEntrada(R.drawable.a6,
                "A6",
                "45 minutos de relajación para dormir.",
                a6_frecsonido,
                a6_frecled,
                a6_intled,
                a6_patrones));

        b0_frecsonido.add(new float[]{0, 18});
        b0_frecsonido.add(new float[]{7, 7.83f});
        b0_frecsonido.add(new float[]{38, 7.83f});
        b0_frecsonido.add(new float[]{40, 18});

        b0_frecled.add(new float[]{0, 18});
        b0_frecled.add(new float[]{7, 7.83f});
        b0_frecled.add(new float[]{38, 7.83f});
        b0_frecled.add(new float[]{40, 18});

        b0_intled.add(new float[]{0, 100});
        b0_intled.add(new float[]{7, 80});
        b0_intled.add(new float[]{35, 80});
        b0_intled.add(new float[]{40, 100});

        b0_patrones.add(new float[]{0, 1});
        b0_patrones.add(new float[]{7, 2});
        b0_patrones.add(new float[]{15, 1});
        b0_patrones.add(new float[]{20, 10});
        b0_patrones.add(new float[]{30, 1});
        b0_patrones.add(new float[]{35, 2});

        al.add(new ListaEntrada(R.drawable.b0,
                "B0",
                "40 minutos de Armonía de la Tierra.",
                b0_frecsonido,
                b0_frecled,
                b0_intled,
                b0_patrones));

        b1_frecsonido.add(new float[]{0, 16});
        b1_frecsonido.add(new float[]{7, 4});
        b1_frecsonido.add(new float[]{12, 7});
        b1_frecsonido.add(new float[]{18, 4});
        b1_frecsonido.add(new float[]{25, 7});
        b1_frecsonido.add(new float[]{30, 5});
        b1_frecsonido.add(new float[]{35, 7});
        b1_frecsonido.add(new float[]{36, 14});

        b1_frecled.add(new float[]{0, 16});
        b1_frecled.add(new float[]{7, 4});
        b1_frecled.add(new float[]{12, 7});
        b1_frecled.add(new float[]{18, 4});
        b1_frecled.add(new float[]{25, 7});
        b1_frecled.add(new float[]{30, 5});
        b1_frecled.add(new float[]{35, 7});
        b1_frecled.add(new float[]{36, 14});

        b1_intled.add(new float[]{0, 100});
        b1_intled.add(new float[]{7, 80});
        b1_intled.add(new float[]{30, 80});
        b1_intled.add(new float[]{36, 100});

        b1_patrones.add(new float[]{0, 1});
        b1_patrones.add(new float[]{4, 2});
        b1_patrones.add(new float[]{7, 1});

        al.add(new ListaEntrada(R.drawable.b1,
                "B1",
                "36 minutos de relajación profunda y aprendizaje Theta.",
                b1_frecsonido,
                b1_frecled,
                b1_intled,
                b1_patrones));

        b2_frecsonido.add(new float[]{0, 15});
        b2_frecsonido.add(new float[]{5, 33});
        b2_frecsonido.add(new float[]{10, 8});
        b2_frecsonido.add(new float[]{15, 18});
        b2_frecsonido.add(new float[]{20, 8});
        b2_frecsonido.add(new float[]{25, 15});
        b2_frecsonido.add(new float[]{30, 32});

        b2_frecled.add(new float[]{0, 15});
        b2_frecled.add(new float[]{5, 33});
        b2_frecled.add(new float[]{10, 8});
        b2_frecled.add(new float[]{15, 18});
        b2_frecled.add(new float[]{20, 8});
        b2_frecled.add(new float[]{25, 15});
        b2_frecled.add(new float[]{30, 32});

        b2_intled.add(new float[]{0, 100});
        b2_intled.add(new float[]{30, 100});

        b2_patrones.add(new float[]{0, 10});
        b2_patrones.add(new float[]{5, 2});
        b2_patrones.add(new float[]{15, 10});
        b2_patrones.add(new float[]{20, 2});
        b2_patrones.add(new float[]{25, 10});
        b2_patrones.add(new float[]{28, 2});

        al.add(new ListaEntrada(R.drawable.b2,
                "B2",
                "30 minutos de Alta Creatividad.",
                b2_frecsonido,
                b2_frecled,
                b2_intled,
                b2_patrones));

        b3_frecsonido.add(new float[]{0, 18});
        b3_frecsonido.add(new float[]{4, 7});
        b3_frecsonido.add(new float[]{10, 5});
        b3_frecsonido.add(new float[]{15, 7});
        b3_frecsonido.add(new float[]{20, 4});
        b3_frecsonido.add(new float[]{30, 7});
        b3_frecsonido.add(new float[]{40, 4});
        b3_frecsonido.add(new float[]{44, 10});
        b3_frecsonido.add(new float[]{46, 18});

        b3_frecled.add(new float[]{0, 18});
        b3_frecled.add(new float[]{4, 7});
        b3_frecled.add(new float[]{10, 5});
        b3_frecled.add(new float[]{15, 7});
        b3_frecled.add(new float[]{20, 4});
        b3_frecled.add(new float[]{30, 7});
        b3_frecled.add(new float[]{40, 4});
        b3_frecled.add(new float[]{44, 10});
        b3_frecled.add(new float[]{46, 18});

        b3_intled.add(new float[]{0, 75});
        b3_intled.add(new float[]{3, 100});
        b3_intled.add(new float[]{25, 75});
        b3_intled.add(new float[]{40, 75});
        b3_intled.add(new float[]{46, 100});

        b3_patrones.add(new float[]{0, 1});
        b3_patrones.add(new float[]{10, 4});
        b3_patrones.add(new float[]{20, 2});
        b3_patrones.add(new float[]{25, 4});
        b3_patrones.add(new float[]{30, 2});
        b3_patrones.add(new float[]{35, 4});
        b3_patrones.add(new float[]{40, 2});
        b3_patrones.add(new float[]{45, 1});

        al.add(new ListaEntrada(R.drawable.b3,
                "B3",
                "46 minutos de relajación profunda/alta creatividad.",
                b3_frecsonido,
                b3_frecled,
                b3_intled,
                b3_patrones));

        b4_frecsonido.add(new float[]{0, 15});
        b4_frecsonido.add(new float[]{5, 35});
        b4_frecsonido.add(new float[]{15, 10});
        b4_frecsonido.add(new float[]{20, 7});
        b4_frecsonido.add(new float[]{24, 7});
        b4_frecsonido.add(new float[]{25, 20});

        b4_frecled.add(new float[]{0, 15});
        b4_frecled.add(new float[]{5, 35});
        b4_frecled.add(new float[]{15, 10});
        b4_frecled.add(new float[]{20, 7});
        b4_frecled.add(new float[]{24, 7});
        b4_frecled.add(new float[]{25, 20});

        b4_intled.add(new float[]{0, 60});
        b4_intled.add(new float[]{10, 100});
        b4_intled.add(new float[]{15, 75});
        b4_intled.add(new float[]{20, 100});
        b4_intled.add(new float[]{23, 75});
        b4_intled.add(new float[]{25, 100});

        b4_patrones.add(new float[]{0, 1});
        b4_patrones.add(new float[]{1, 4});
        b4_patrones.add(new float[]{2, 1});
        b4_patrones.add(new float[]{4, 2});
        b4_patrones.add(new float[]{7, 6});
        b4_patrones.add(new float[]{10, 1});
        b4_patrones.add(new float[]{12, 5});
        b4_patrones.add(new float[]{15, 1});
        b4_patrones.add(new float[]{20, 4});
        b4_patrones.add(new float[]{24, 1});

        al.add(new ListaEntrada(R.drawable.b4,
                "B4",
                "25 minutos de sintonía general.",
                b4_frecsonido,
                b4_frecled,
                b4_intled,
                b4_patrones));

        b5_frecsonido.add(new float[]{0, 20});
        b5_frecsonido.add(new float[]{10, 5});
        b5_frecsonido.add(new float[]{20, 7});
        b5_frecsonido.add(new float[]{30, 5});
        b5_frecsonido.add(new float[]{40, 7});
        b5_frecsonido.add(new float[]{45, 18});

        b5_frecled.add(new float[]{0, 20});
        b5_frecled.add(new float[]{10, 5});
        b5_frecled.add(new float[]{20, 7});
        b5_frecled.add(new float[]{30, 5});
        b5_frecled.add(new float[]{40, 7});
        b5_frecled.add(new float[]{45, 18});

        b5_intled.add(new float[]{0, 60});
        b5_intled.add(new float[]{3, 100});
        b5_intled.add(new float[]{10, 75});
        b5_intled.add(new float[]{40, 75});
        b5_intled.add(new float[]{45, 100});

        b5_patrones.add(new float[]{0, 2});
        b5_patrones.add(new float[]{1, 1});
        b5_patrones.add(new float[]{2, 4});
        b5_patrones.add(new float[]{3, 1});
        b5_patrones.add(new float[]{5, 2});
        b5_patrones.add(new float[]{7, 1});
        b5_patrones.add(new float[]{10, 6});
        b5_patrones.add(new float[]{20, 1});
        b5_patrones.add(new float[]{30, 2});
        b5_patrones.add(new float[]{40, 1});

        al.add(new ListaEntrada(R.drawable.b5,
                "B5",
                "45 minutos de uso general.",
                b5_frecsonido,
                b5_frecled,
                b5_intled,
                b5_patrones));

        b6_frecsonido.add(new float[]{0, 15});
        b6_frecsonido.add(new float[]{10, 5});
        b6_frecsonido.add(new float[]{11, 1});
        b6_frecsonido.add(new float[]{40, 1});
        b6_frecsonido.add(new float[]{45, 15});

        b6_frecled.add(new float[]{0, 15});
        b6_frecled.add(new float[]{10, 5});
        b6_frecled.add(new float[]{40, 5});
        b6_frecled.add(new float[]{45, 15});

        b6_intled.add(new float[]{0, 100});
        b6_intled.add(new float[]{10, 75});
        b6_intled.add(new float[]{40, 75});
        b6_intled.add(new float[]{45, 100});

        b6_patrones.add(new float[]{0, 1});
        b6_patrones.add(new float[]{10, 6});
        b6_patrones.add(new float[]{40, 1});

        al.add(new ListaEntrada(R.drawable.b6,
                "B6",
                "46 minutos de aprendizaje acelerado.",
                b6_frecsonido,
                b6_frecled,
                b6_intled,
                b6_patrones));

        ab0_frecsonido.add(new float[]{0, 15});
        ab0_frecsonido.add(new float[]{13, 3});
        ab0_frecsonido.add(new float[]{18, 5});
        ab0_frecsonido.add(new float[]{25, 3});
        ab0_frecsonido.add(new float[]{30, 6});
        ab0_frecsonido.add(new float[]{40, 3});
        ab0_frecsonido.add(new float[]{50, 5});
        ab0_frecsonido.add(new float[]{60, 30});

        ab0_frecled.add(new float[]{0, 15});
        ab0_frecled.add(new float[]{13, 3});
        ab0_frecled.add(new float[]{18, 5});
        ab0_frecled.add(new float[]{25, 3});
        ab0_frecled.add(new float[]{30, 6});
        ab0_frecled.add(new float[]{40, 3});
        ab0_frecled.add(new float[]{50, 5});
        ab0_frecled.add(new float[]{60, 30});

        ab0_intled.add(new float[]{0, 100});
        ab0_intled.add(new float[]{20, 60});
        ab0_intled.add(new float[]{50, 60});
        ab0_intled.add(new float[]{60, 100});

        ab0_patrones.add(new float[]{0, 1});
        ab0_patrones.add(new float[]{5, 6});
        ab0_patrones.add(new float[]{10, 2});
        ab0_patrones.add(new float[]{15, 1});
        ab0_patrones.add(new float[]{60, 1});

        al.add(new ListaEntrada(R.drawable.ab0,
                "AB0",
                "60 minutos de relajación ultra-profunda.",
                ab0_frecsonido,
                ab0_frecled,
                ab0_intled,
                ab0_patrones));

        ab1_frecsonido.add(new float[]{0, 16});
        ab1_frecsonido.add(new float[]{4, 3});
        ab1_frecsonido.add(new float[]{14, 3});
        ab1_frecsonido.add(new float[]{15, 25});

        ab1_frecled.add(new float[]{0, 16});
        ab1_frecled.add(new float[]{4, 3});
        ab1_frecled.add(new float[]{14, 3});
        ab1_frecled.add(new float[]{15, 25});

        ab1_intled.add(new float[]{0, 100});
        ab1_intled.add(new float[]{4, 75});
        ab1_intled.add(new float[]{14, 75});
        ab1_intled.add(new float[]{15, 100});

        ab1_patrones.add(new float[]{0, 1});
        ab1_patrones.add(new float[]{4, 6});
        ab1_patrones.add(new float[]{14, 1});

        al.add(new ListaEntrada(R.drawable.ab1,
                "AB1",
                "15 minutos de Siesta de gato.",
                ab1_frecsonido,
                ab1_frecled,
                ab1_intled,
                ab1_patrones));

        ab2_frecsonido.add(new float[]{0, 18});
        ab2_frecsonido.add(new float[]{7, 5});
        ab2_frecsonido.add(new float[]{10, 5});
        ab2_frecsonido.add(new float[]{15, 19});
        ab2_frecsonido.add(new float[]{25, 30});
        ab2_frecsonido.add(new float[]{40, 31});

        ab2_frecled.add(new float[]{0, 18});
        ab2_frecled.add(new float[]{6, 6});
        ab2_frecled.add(new float[]{10, 2});
        ab2_frecled.add(new float[]{15, 4});
        ab2_frecled.add(new float[]{20, 1});
        ab2_frecled.add(new float[]{30, 3});
        ab2_frecled.add(new float[]{38, 1});
        ab2_frecled.add(new float[]{40, 20});

        ab2_intled.add(new float[]{0, 100});
        ab2_intled.add(new float[]{7, 75});
        ab2_intled.add(new float[]{38, 75});
        ab2_intled.add(new float[]{40, 100});

        ab2_patrones.add(new float[]{0, 1});
        ab2_patrones.add(new float[]{7, 3});
        ab2_patrones.add(new float[]{10, 4});
        ab2_patrones.add(new float[]{20, 3});
        ab2_patrones.add(new float[]{30, 4});

        al.add(new ListaEntrada(R.drawable.ab2,
                "AB2",
                "40 minutos de Alerta Delta.",
                ab2_frecsonido,
                ab2_frecled,
                ab2_intled,
                ab2_patrones));

        ab3_frecsonido.add(new float[]{0, 31});
        ab3_frecsonido.add(new float[]{12, 39});
        ab3_frecsonido.add(new float[]{16, 39});
        ab3_frecsonido.add(new float[]{30, 30});

        ab3_frecled.add(new float[]{0, 31});
        ab3_frecled.add(new float[]{12, 39});
        ab3_frecled.add(new float[]{16, 39});
        ab3_frecled.add(new float[]{30, 30});

        ab3_intled.add(new float[]{0, 100});
        ab3_intled.add(new float[]{30, 100});

        ab3_patrones.add(new float[]{0, 1});
        ab3_patrones.add(new float[]{15, 2});

        al.add(new ListaEntrada(R.drawable.ab3,
                "AB3",
                "30 minutos de CREATIVIDAD K-COMPLEX.",
                ab3_frecsonido,
                ab3_frecled,
                ab3_intled,
                ab3_patrones));

        ab4_frecsonido.add(new float[]{0, 1});
        ab4_frecsonido.add(new float[]{70, 1});
        ab4_frecsonido.add(new float[]{75, 15});

        ab4_frecled.add(new float[]{0, 20});
        ab4_frecled.add(new float[]{5, 12});
        ab4_frecled.add(new float[]{8, 5});
        ab4_frecled.add(new float[]{25, 1});
        ab4_frecled.add(new float[]{30, 2});
        ab4_frecled.add(new float[]{35, 1});
        ab4_frecled.add(new float[]{45, 3});
        ab4_frecled.add(new float[]{55, 2});
        ab4_frecled.add(new float[]{65, 2});
        ab4_frecled.add(new float[]{70, 1});
        ab4_frecled.add(new float[]{75, 15});

        ab4_intled.add(new float[]{0, 100});
        ab4_intled.add(new float[]{14, 60});
        ab4_intled.add(new float[]{70, 60});
        ab4_intled.add(new float[]{75, 100});

        ab4_patrones.add(new float[]{0, 1});
        ab4_patrones.add(new float[]{8, 6});
        ab4_patrones.add(new float[]{70, 1});

        al.add(new ListaEntrada(R.drawable.ab4,
                "AB4",
                "75 minutos de ESCAPE DELTA.",
                ab4_frecsonido,
                ab4_frecled,
                ab4_intled,
                ab4_patrones));

        ab5_frecsonido.add(new float[]{0, 16});
        ab5_frecsonido.add(new float[]{5, 7});
        ab5_frecsonido.add(new float[]{15, 10});
        ab5_frecsonido.add(new float[]{20, 5});
        ab5_frecsonido.add(new float[]{25, 15});
        ab5_frecsonido.add(new float[]{30, 10});
        ab5_frecsonido.add(new float[]{34, 15});
        ab5_frecsonido.add(new float[]{36, 20});

        ab5_frecled.add(new float[]{0, 16});
        ab5_frecled.add(new float[]{5, 7});
        ab5_frecled.add(new float[]{15, 10});
        ab5_frecled.add(new float[]{20, 5});
        ab5_frecled.add(new float[]{25, 15});
        ab5_frecled.add(new float[]{30, 10});
        ab5_frecled.add(new float[]{34, 15});
        ab5_frecled.add(new float[]{36, 20});

        ab5_intled.add(new float[]{0, 100});
        ab5_intled.add(new float[]{7, 75});
        ab5_intled.add(new float[]{34, 75});
        ab5_intled.add(new float[]{36, 100});

        ab5_patrones.add(new float[]{0, 2});
        ab5_patrones.add(new float[]{3, 1});
        ab5_patrones.add(new float[]{7, 5});
        ab5_patrones.add(new float[]{34, 1});

        al.add(new ListaEntrada(R.drawable.ab5,
                "AB5",
                "36 minutos de Memoria/Recuerdo.",
                ab5_frecsonido,
                ab5_frecled,
                ab5_intled,
                ab5_patrones));

        ab6_frecsonido.add(new float[]{0, 0});
        ab6_frecsonido.add(new float[]{1, 30});
        ab6_frecsonido.add(new float[]{2, 0});
        ab6_frecsonido.add(new float[]{3, 36});
        ab6_frecsonido.add(new float[]{4, 0});
        ab6_frecsonido.add(new float[]{8, 40});
        ab6_frecsonido.add(new float[]{12, 5});
        ab6_frecsonido.add(new float[]{16, 40});
        ab6_frecsonido.add(new float[]{22, 16});
        ab6_frecsonido.add(new float[]{30, 40});
        ab6_frecsonido.add(new float[]{33, 16});
        ab6_frecsonido.add(new float[]{35, 30});

        ab6_frecled.add(new float[]{0, 0});
        ab6_frecled.add(new float[]{1, 30});
        ab6_frecled.add(new float[]{2, 0});
        ab6_frecled.add(new float[]{3, 36});
        ab6_frecled.add(new float[]{4, 0});
        ab6_frecled.add(new float[]{8, 40});
        ab6_frecled.add(new float[]{12, 5});
        ab6_frecled.add(new float[]{16, 40});
        ab6_frecled.add(new float[]{22, 16});
        ab6_frecled.add(new float[]{30, 40});
        ab6_frecled.add(new float[]{33, 16});
        ab6_frecled.add(new float[]{35, 30});

        ab6_intled.add(new float[]{0, 100});
        ab6_intled.add(new float[]{35, 100});

        ab6_patrones.add(new float[]{0, 9});
        ab6_patrones.add(new float[]{2, 10});
        ab6_patrones.add(new float[]{4, 3});
        ab6_patrones.add(new float[]{8, 6});
        ab6_patrones.add(new float[]{12, 1});
        ab6_patrones.add(new float[]{14, 4});
        ab6_patrones.add(new float[]{16, 2});
        ab6_patrones.add(new float[]{22, 9});
        ab6_patrones.add(new float[]{24, 10});
        ab6_patrones.add(new float[]{25, 1});
        ab6_patrones.add(new float[]{30, 3});
        ab6_patrones.add(new float[]{34, 1});

        al.add(new ListaEntrada(R.drawable.ab6,
                "AB6",
                "35 minutos de Caleidoscopio.",
                ab6_frecsonido,
                ab6_frecled,
                ab6_intled,
                ab6_patrones));

        lv = findViewById(R.id.ListView_listado);
        lv.setAdapter(new ListaAdapter(this, R.layout.listado, al) {
            public void onEntrada(Object o, View v) {
                if (o != null) {
                    TextView texto_superior_entrada = v.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((ListaEntrada) o).get_textoEncima());
                    TextView texto_inferior_entrada = v.findViewById(R.id.textView_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((ListaEntrada) o).get_textoDebajo());
                    ImageView imagen_entrada = v.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((ListaEntrada) o).get_idImagen());
                }
            }
        });
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                ListaEntrada le = (ListaEntrada) av.getItemAtPosition(i);

                // Crear ArrayList a partir de List<float[]>
                ArrayList<float[]> tablaFrecSonido = new ArrayList<>(le.get_tabla_frec_sonido());
                ArrayList<float[]> tablaFrecLED = new ArrayList<>(le.get_tabla_frec_LED());
                ArrayList<float[]> tablaIntensidadLED = new ArrayList<>(le.get_tabla_intensidad_LED());
                ArrayList<float[]> tablaPatrones = new ArrayList<>(le.get_tabla_patrones());

                intent = new Intent(MainActivity.this, ProgramaActivity.class);
                bundle = new Bundle();

                // Pasar las listas como serializables
                bundle.putString("titulo", le.get_textoEncima());
                bundle.putSerializable("frec_sonido", tablaFrecSonido);
                bundle.putSerializable("frec_led", tablaFrecLED);
                bundle.putSerializable("int_led", tablaIntensidadLED);
                bundle.putSerializable("patrones", tablaPatrones);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}
