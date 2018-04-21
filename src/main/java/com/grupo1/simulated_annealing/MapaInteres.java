/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author cfoch
 */
public class MapaInteres {
    private Graph<Locacion, DefaultWeightedEdge> grafo;

    /**
     * Crea un mapa de interés a partir de un archivo de texto.
     * @return un MapaInteres
     */
    public static MapaInteres fromArchivo() {
        /* TODO */
        return new MapaInteres();
    }

    /**
     * Un simple constructor. Un MapaInteres representa el conjunto de
     * locaciones y servicios a atender en cierto periodo (día, por ejemplo).
     */
    public MapaInteres() {
        grafo = new SimpleGraph<>(DefaultWeightedEdge.class);
    }
}
