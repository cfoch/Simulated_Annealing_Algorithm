/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cfoch
 */
public class VRPCostMatrix {
    Map<Locacion, Integer> indexes;
    private double [][] costMatrix;

    /**
     * Crea una matriz de costos que será usada para calcular el costo
     * de ir una locación a otra.
     * @param distanceMatrix Una matriz de distancias en el ordene de las
     *      locaciones,
     * @param locaciones Un arreglo de locaciones.
     * @param isBidirected Si se refiere a un grafo bidireccional.
     */
    public VRPCostMatrix(double [][] costMatrix, Locacion [] locaciones)
            throws Exception {
        int i;

        // Verificar validez de matriz.
        if (costMatrix.length != locaciones.length) {
            throw new Exception("Number of rows of the 'distanceMatrix' should "
                    + "be the same of the number of elements in 'locaciones'.");
        } else {
            for (i = 0; i < costMatrix.length; i++) {
                if (costMatrix[i].length != locaciones.length) {
                    throw new Exception("Number of elements for each row "
                            + "should be the same than Locacion array.");
                }
            }
        }

        indexes = new HashMap<Locacion, Integer>();
        for (i = 0; i < locaciones.length; i++) {
            indexes.put(locaciones[i], i);
        }
        this.costMatrix = costMatrix;
    }

    /**
     * Devuelve el costo de ir de una locación de origen a un destino.
     * @param origen
     * @param destino
     * @return Un costo en caso de éxito, sino -1.
     */
    public double getCost(Locacion origen, Locacion destino) {
        int i, j;
        double cost;
        try {
            i = indexes.get(origen);
            j = indexes.get(destino);
            cost = getCostMatrix()[i][j];
        } catch (Exception ex) {
            cost = -1;
        }
        return cost;
    }

    /**
     * Obtiene la matriz de costos.
     * @return the costMatrix
     */
    public double[][] getCostMatrix() {
        return costMatrix;
    }
}
