/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import static com.grupo1.misc.LocationGeneration.genLocaciones;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author cfoch
 */
public class VRPProblem implements GRParser {
    private Graph<Locacion, Pista> grafo;
    private Vehiculo.Tipo vehiculoTipo;
    private Locacion puntoDePartida;
    private double[][] distanceMatrix;

    /**
     * Crea un problema de VRP aleatoriamente al rededor de un punto a cierto
     * radio.
     * @param n Número de locaciones
     * @param x0 Latitud
     * @param y0 Longitud
     * @param radio El radio al rededor de (x0, y0).
     * @param vehiculoTipo El tipo de vehículo que realizará los repartos a
     *  tales locaciones.
     */
    public VRPProblem(final int n, final double x0, final double y0,
            final double radio, final Vehiculo.Tipo vehiculoTipo) {
        ArrayList<Locacion> locaciones;
        int i, j;

        grafo = new SimpleGraph<>(Pista.class);
        this.vehiculoTipo = vehiculoTipo;
        locaciones = genLocaciones(n, x0, y0, radio, true, vehiculoTipo);
        for (i = 0; i < locaciones.size(); i++) {
            Locacion locacion;
            locacion = locaciones.get(i);
            if (locacion.getTipo() == Locacion.Tipo.DEPOSITO) {
                puntoDePartida = locacion;
            }
            getGrafo().addVertex(locacion);
        }

        locaciones = new ArrayList<>(getGrafo().vertexSet());
        for (i = 0; i < locaciones.size(); i++) {
            Locacion v1;
            v1 = locaciones.get(i);
            for (j = 0; j < locaciones.size(); j++) {
                Locacion v2;
                v2 = locaciones.get(j);
                if (i == j) {
                    continue;
                }
                getGrafo().addEdge(v1, v2);
            }
        }
    }

    /**
     * Crea un problema de VRP a partir de la ruta de un archivo.
     * @param path ruta del archivo.
     */
    public VRPProblem(final String path) {
        grafo = new SimpleGraph<>(Pista.class);
        fromFile(path);
    }

    /**
     * Crea un problema de VRP a partir de un grafo de locaciones (usualmente
     * todas servicios excepto una, el depósito) y un tipo de vehículo.
     * @param grafo un grafo de de locaciones
     * @param puntoPartida una locacion de tipo DEPOSITO.
     * @param vehiculoTipo el tipo del vehículo.
     */
    public VRPProblem(final Graph<Locacion, Pista> grafo,
            final Locacion puntoPartida, final Vehiculo.Tipo vehiculoTipo) {
        //this.mapa = mapa;
        this.grafo = grafo;
        this.puntoDePartida = puntoPartida;
        this.vehiculoTipo = vehiculoTipo;
    }

    /**
     * Obtiene el grafo del problema.
     * @return un Graph.
     */
    public final Graph<Locacion, Pista> getGrafo() {
        return grafo;
    }

    /**
     * Obtiene el tipo del vehículo para este problema.
     * @return tipo de vehículo.
     */
    public final Vehiculo.Tipo getVehiculoTipo() {
        return vehiculoTipo;
    }

    /**
     * Obtiene el punto de partida.
     * @return punto de partida.
     */
    public final Locacion getPuntoDePartida() {
        return puntoDePartida;
    }

    @Override
    public final void fromFile(final String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean ignorar;
            HashMap<Integer, Locacion> locacionesMap;
            ArrayList<Locacion> locaciones;
            int nNodos = 0, iNodo = 0;
            int x, y;
            int i, j;
            int capacidad;

            locacionesMap = new HashMap<Integer, Locacion>();
            while (true) {
                line = br.readLine();
                if (line == null) {
                    // System.out.println("EOF");
                    break;
                }
                // TODO
                // Manejar error en caso de archivo mal formateado.
                if (line.startsWith("DIMENSION : ")) {
                    line = line.replace("DIMENSION : ", "");
                    nNodos = Integer.parseInt(line);
                } else if (line.startsWith("CAPACITY : ")
                        && iNodo == 0) {
                    line = line.replace("CAPACITY : ", "");
                    capacidad = Integer.parseInt(line);
                    vehiculoTipo = new Vehiculo.Tipo(line, capacidad);
                } else if (line.startsWith("NODE_COORD_SECTION")) {
                    for (iNodo = 1; iNodo <= nNodos; iNodo++) {
                        Locacion locacion;
                        String[] tokens;
                        String nombre;
                        Servicio servicio;

                        line = br.readLine();
                        line = line.trim();
                        tokens = line.split(" ");

                        nombre = "Locacion " + iNodo;
                        x = Integer.parseInt(tokens[1]);
                        y = Integer.parseInt(tokens[2]);
                        locacion = new Locacion(iNodo, nombre,
                                Locacion.Tipo.OTRO, x, y);
                        locacionesMap.put(iNodo, locacion);
                        getGrafo().addVertex(locacion);
                    }
                } else if (line.startsWith("DEMAND_SECTION")) {
                    for (iNodo = 1; iNodo <= nNodos; iNodo++) {
                        Locacion locacion;
                        String[] tokens;
                        Servicio servicio;
                        String nombre;
                        int id, demanda;

                        line = br.readLine();
                        tokens = line.split(" ");
                        id = Integer.parseInt(tokens[0]);
                        demanda = Integer.parseInt(tokens[1]);
                        locacion = locacionesMap.get(id);

                        nombre = "Servicio " + id;
                        servicio = new Servicio(nombre, locacion, demanda);
                        locacion.setServicio(servicio);
                    }
                } else if (line.startsWith("DEPOT_SECTION")) {
                    int depotLocationId;
                    Locacion locacion;

                    line = br.readLine();
                    line = line.trim();
                    depotLocationId = Integer.parseInt(line);
                    locacion = locacionesMap.get(depotLocationId);
                    locacion.setTipo(Locacion.Tipo.DEPOSITO);
                    puntoDePartida = locacion;
                }
            }
            locaciones = new ArrayList<>(getGrafo().vertexSet());
            for (i = 0; i < locaciones.size(); i++) {
                Locacion v1;
                v1 = locaciones.get(i);
                for (j = 0; j < locaciones.size(); j++) {
                    Locacion v2;
                    v2 = locaciones.get(j);
                    if (i == j) {
                        continue;
                    }
                    getGrafo().addEdge(v1, v2);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
