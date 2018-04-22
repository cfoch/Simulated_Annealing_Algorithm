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
public interface GRParser {
    /**
     * Crea un mapa de interés a partir de un archivo de texto formateado según
     * la propuesta de Gerhard Reinelt para problemas CVRP. La especificación
     * del formato en más detalle se puede encontrar en
     * http://neo.lcc.uma.es/vrp/wp-content/data/Doc.ps
     * @param path la ruta del archivo.
     */
    void fromFile(final String path);
}
