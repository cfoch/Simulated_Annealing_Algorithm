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
public class Servicio {
    private String nombre;
    private Locacion locacion;
    private int demanda;
    private Dimensiones dimension;

    /**
     * Constructor del Servicio. El servicio representa la tarea de hacer un
     * reparte de un paquete a una locación con cierta demanda.
     * @param nombre El nombre del servicio.
     * @param locacion La locación de donde se hará la entrega.
     * @param demandaPeso La demanda del cliente.
     */
    public Servicio(final String nombre, final Locacion locacion,
            final int demandaPeso) {
        // Por ahora solo dimension PESO es soportada.
        this.dimension = Dimensiones.PESO;
        this.nombre = nombre;
        this.demanda = demandaPeso;
        this.locacion = locacion;
    }

    /**
     * Obtiene la demanda del servicio.
     * @return (int) la demanda
     */
    public final int getDemanda() {
        return demanda;
    }
}
