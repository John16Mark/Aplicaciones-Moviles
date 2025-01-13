package com.example.proyectofinal;

import java.util.ArrayList;
import java.util.List;

public class ListaEntrada {
    private int idim;
    private String a;
    private String d;
    private List<float[]> tabla_frecSonido = new ArrayList<>();
    private List<float[]> tabla_frecLED = new ArrayList<>();
    private List<float[]> tabla_intensidadLED = new ArrayList<>();
    private List<float[]> tabla_patrones = new ArrayList<>();

    public ListaEntrada (int idI, String sa, String sd, List<float[]> tabla_frecSonido, List<float[]> tabla_frecLED, List<float[]> tabla_intensidadLED, List<float[]> tabla_patrones) {
        this.idim = idI;
        this.a = sa;
        this.d = sd;
        this.tabla_frecSonido = tabla_frecSonido;
        this.tabla_frecLED = tabla_frecLED;
        this.tabla_intensidadLED = tabla_intensidadLED;
        this.tabla_patrones = tabla_patrones;
    }

    public String get_textoEncima () {
        return a;
    }

    public String get_textoDebajo () {
        return d;
    }

    public int get_idImagen () {
        return idim;
    }

    public List<float[]> get_tabla_frec_sonido () {
        return tabla_frecSonido;
    }

    public List<float[]> get_tabla_frec_LED () {
        return tabla_frecLED;
    }

    public List<float[]> get_tabla_intensidad_LED () {
        return tabla_intensidadLED;
    }

    public List<float[]> get_tabla_patrones () {
        return tabla_patrones;
    }
}
