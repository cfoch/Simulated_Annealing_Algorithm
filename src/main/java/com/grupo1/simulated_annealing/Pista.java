/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Una Pista es la representación de una arista entre dos nodos (en este caso,
 * entre locaciones).
 * @author cfoch
 */
public class Pista extends DefaultWeightedEdge implements Comparable {
    @Override
    public final double getWeight() {
        Locacion origen, destino;
        VRPCostMatrix costMatrix;
        EuclideanDistance euclides;
        double cost;
        double[] origenPunto, destinoPunto;

        origen = (Locacion) getSource();
        destino = (Locacion) getTarget();

        cost = origen.getCost(destino);
        if (cost == -1) {
            // If a distance matrix does not exist.
            origenPunto = new double[]{origen.getX(), origen.getX()};
            destinoPunto = new double[]{destino.getY(), origen.getY()};

            euclides = new EuclideanDistance();
            cost = euclides.compute(origenPunto, destinoPunto);
        }
        return cost;
    }

    @Override
    public final int compareTo(final Object t) {
        Pista pista;
        double dif;
        dif =  this.getWeight() - ((Pista) (t)).getWeight();
        if (dif > 0) {
            return 1;
        } else if (dif < 0) {
            return -1;
        }
        return 0;
    }
}
