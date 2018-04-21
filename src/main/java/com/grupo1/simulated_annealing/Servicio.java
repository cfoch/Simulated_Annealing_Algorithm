/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import com.graphhopper.jsprit.core.problem.job.Service;

/**
 *
 * @author cfoch
 */
public class Servicio {
    private Service service;
    private Locacion locacion;

    /**
     * Constructor del Servicio. El servicio representa la tarea de hacer un
     * reparte de un paquete a una locación con cierta demanda.
     * @param nombre El nombre del servicio.
     * @param locacion La locación de donde se hará la entrega.
     * @param demandaPeso La demanda del cliente.
     */
    public Servicio(final String nombre, final Locacion locacion,
            final int demandaPeso) {
        Service.Builder builder;
        builder = Service.Builder.newInstance(nombre);
        builder =
            builder.addSizeDimension(Dimensiones.PESO.ordinal(), demandaPeso);
        builder = builder.setLocation(locacion.getLocation());
        this.service = builder.build();
        this.locacion = locacion;
    }

    /**
     * Obtiene la demanda del servicio.
     * @return (int) la demanda
     */
    public final int getDemanda() {
        return service.getSize().get(Dimensiones.PESO.ordinal());
    }
}
