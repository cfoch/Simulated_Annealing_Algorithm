/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.util.Coordinate;

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
        CLIENTE,
        OTRO
    };

    private Location location;
    private Tipo tipo;

    /**
     * Crea una locación. Una locación es la representación de un lugar
     * específico en el mapa. Una locación por lo general será una intersección
     * de calles, ubicación de un cliente o ubicación de un depṕsito.
     * @param nombre El nombre (dirección, por ejemplo) de la locación.
     * @param tipo El tipo de locación.
     * @param x La coordenada en x.
     * @param y La coordenada en y.
     */
    public Locacion(final String nombre, final Locacion.Tipo tipo,
            final double x, final double y) {
        Location.Builder builder;
        builder = Location.Builder.newInstance();
        builder = builder.setCoordinate(Coordinate.newInstance(x, y));
        builder = builder.setName(nombre);
        this.tipo = tipo;
        this.location = builder.build();
    }

    /**
     * Obtiene una Location de JSprit.
     * @return una Location.
     */
    public final Location getLocation() {
        return location;
    }

    /**
     * Obtiene el nombre de la locación.
     * @return un nombre.
    */
    public final String getNombre() {
        return location.getName();
    }


    /**
     * Obtiene la coordenada en x.
     * @return la coordenada en x.
    */
    public final double getX() {
        return location.getCoordinate().getX();
    }

    /**
     * Obtiene la coordenada en y.
     * @return la coordenada en y.
    */
    public final double getY() {
        return location.getCoordinate().getY();
    }
}
