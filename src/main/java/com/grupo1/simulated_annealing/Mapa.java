/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import java.util.ArrayList;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author cfoch
 */
public class Mapa {
    private Graph<Locacion, Pista> grafo;

    /**
     * Un simple constructor. Un MapaInteres representa el conjunto de
     * locaciones y servicios a atender en cierto periodo (día, por ejemplo).
     */
    public Mapa() {
        grafo = new SimpleGraph<>(Pista.class);
    }

    /**
     * Obtiene el grafo que representa el mapa.
     * @return un grafo.
     */
    public final Graph<Locacion, Pista> getGrafo() {
        return grafo;
    }

    /**
     * Obtiene las locaciones con servicios asignados.
     * @return lista de Locacion.
     */
    public final ArrayList<Locacion> getServicios() {
        ArrayList<Locacion> servicios;
        ArrayList<Locacion> locaciones;
        int i;
        locaciones = new ArrayList<Locacion>(grafo.vertexSet());
        servicios = new ArrayList<Locacion>();
        for (i = 0; i < locaciones.size(); i++) {
            if (locaciones.get(i).getServicio() != null) {
                servicios.add(locaciones.get(i));
            }
        }
        return servicios;
    }
}
