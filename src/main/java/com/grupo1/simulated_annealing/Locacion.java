/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

/**
 *
 * @author cfoch
 */
public class Locacion {
    /**
     * Tipos de locaciones que pueden ser usados.
     */
    public enum Tipo {
        DEPOSITO,
        OTRO
    };

    private int id;
    private Tipo tipo;
    private Servicio servicio;
    private String nombre;
    private double x;
    private double y;

    /**
     * Crea una locación. Una locación es la representación de un lugar
     * específico en el mapa. Una locación por lo general será una intersección
     * de calles, ubicación de un cliente o ubicación de un depṕsito.
     * @param id El identificador de la locación.
     * @param nombre El nombre (dirección, por ejemplo) de la locación.
     * @param tipo El tipo de locación.
     * @param x La coordenada en x.
     * @param y La coordenada en y.
     */
    public Locacion(final int id, final String nombre, final Locacion.Tipo tipo,
            final double x, final double y) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.tipo = tipo;
        this.servicio = null;
    }

    /**
     * Obtiene el nombre de la locación.
     * @return un nombre.
    */
    public final String getNombre() {
        return nombre;
    }


    /**
     * Obtiene la coordenada en x.
     * @return la coordenada en x.
    */
    public final double getX() {
        return x;
    }

    /**
     * Obtiene la coordenada en y.
     * @return la coordenada en y.
    */
    public final double getY() {
        return y;
    }

    /**
     * Asigna un servicio a esta locación.
     * @param servicio un Servicio.
     */
    public final void setServicio(final Servicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Obtiene el servicio de esta locación.
     * @return un Servicio.
     */
    public final Servicio getServicio() {
        return servicio;
    }

    /**
     * Coloca el tipo de locación.
     * @param tipo un Servicio o null si no existe.
     */
    public final void setTipo(final Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el tipo de locación.
     * @return tipo de Locacion.
     */
    public final Locacion.Tipo getTipo() {
        return tipo;
    }

    /**
     * Si la locación es un servicio o no.
     * @return true si es un servicio, false de lo contrario.
     */
    public final boolean esServicio() {
        return servicio != null;
    }

    @Override
    public final String toString() {
        if (this.getServicio() != null) {
            return String.format("%s[%d]", this.getNombre(),
                    this.getServicio().getDemanda());
        }
        return this.getNombre();
    }
}
