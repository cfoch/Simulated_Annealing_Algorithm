/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import com.grupo1.simulated_annealing.VRPProblem.VRPProblemInvalidGraph;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cfoch
 */
public class VRPProblemTest {
    private static final int VEHICLE_CAPACITY = 200;
    private static final int MAX_LOCACIONES = 10;
    private static final int DEMAND_PER_SERVICE = 40;
    private List<Locacion> locaciones;
    private Vehiculo.Tipo vehiculoTipo;
    private Locacion puntoPartida;

    public VRPProblemTest() {
        crearDatosGrafo();
    }

    public void crearDatosGrafo() {
        int i;

        locaciones = new ArrayList<>();

        for (i = 0; i < MAX_LOCACIONES; i++) {
            Locacion locacion;
            Servicio servicio;
            locacion = new Locacion(i, "Locacion " + i, Locacion.Tipo.OTRO,
                    i, i);
            servicio = new Servicio("Servicio " + i, locacion,
                    DEMAND_PER_SERVICE);
            locacion.setServicio(servicio);
            locaciones.add(locacion);
        }
        vehiculoTipo = new Vehiculo.Tipo("Foo", VEHICLE_CAPACITY);
        puntoPartida = new Locacion(i, "Deposito 0", Locacion.Tipo.DEPOSITO,
                    i, i);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of VRPProblem constructor, of class VRPProblem.
     */
    @Test
    public void testVRPProblemValid() throws Exception {
        System.out.println("VRPProblemValid");
        int i, j;
        VRPProblem instance;
        Graph<Locacion, Pista> grafo;
        List<Locacion> locaciones1;

        locaciones1 = new ArrayList<>(this.locaciones);

        grafo = new SimpleGraph<>(Pista.class);
        locaciones1.add(puntoPartida);
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion locacion;
            locacion = locaciones1.get(i);
            grafo.addVertex(locacion);
        }
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion v1;
            v1 = locaciones1.get(i);
            for (j = 0; j < locaciones1.size(); j++) {
                Locacion v2;
                v2 = locaciones1.get(j);
                if (i == j) {
                    continue;
                }
                grafo.addEdge(v1, v2);
            }
        }
        instance = new VRPProblem(grafo, puntoPartida, vehiculoTipo);
    }

    /**
     * Test of VRPProblem constructor, of class VRPProblem.
     */
    @Test
    public void testVRPProblemInvalid() throws Exception {
        System.out.println("VRPProblemInvalid");
        int i, j;
        VRPProblem instance;
        Graph<Locacion, Pista> grafo;
        List<Locacion> locaciones1;

        // Se ingresa un punto de partida que no existe en el grafo.
        locaciones1 = new ArrayList<>(this.locaciones);
        grafo = new SimpleGraph<>(Pista.class);
        locaciones1.add(puntoPartida);
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion locacion;
            locacion = locaciones1.get(i);
            grafo.addVertex(locacion);
        }
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion v1;
            v1 = locaciones1.get(i);
            for (j = 0; j < locaciones1.size(); j++) {
                Locacion v2;
                v2 = locaciones1.get(j);
                if (i == j) {
                    continue;
                }
                grafo.addEdge(v1, v2);
            }
        }

        Locacion otroPuntoPartida = new Locacion(100, "Locacion dummy",
                Locacion.Tipo.DEPOSITO, 10000, 10000);

        try {
            instance = new VRPProblem(grafo, otroPuntoPartida, vehiculoTipo);
        } catch (VRPProblemInvalidGraph ex) {
        }

        // Una locacion no tiene un servicio asignado.
        crearDatosGrafo();

        locaciones1 = new ArrayList<>(this.locaciones);
        locaciones1.get(0).setServicio(null);
        grafo = new SimpleGraph<>(Pista.class);
        locaciones1.add(puntoPartida);
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion locacion;
            locacion = locaciones1.get(i);
            grafo.addVertex(locacion);
        }
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion v1;
            v1 = locaciones1.get(i);
            for (j = 0; j < locaciones1.size(); j++) {
                Locacion v2;
                v2 = locaciones1.get(j);
                if (i == j) {
                    continue;
                }
                grafo.addEdge(v1, v2);
            }
        }
        try {
            instance = new VRPProblem(grafo, puntoPartida, vehiculoTipo);
        } catch (VRPProblemInvalidGraph ex) {
        }

        // Se ingresa una locacion con un servicio que apunta a otra locacion.
        crearDatosGrafo();

        locaciones1 = new ArrayList<>(this.locaciones);
        locaciones1.get(0).getServicio().setLocacion(null);
        grafo = new SimpleGraph<>(Pista.class);
        locaciones1.add(puntoPartida);
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion locacion;
            locacion = locaciones1.get(i);
            grafo.addVertex(locacion);
        }
        for (i = 0; i < locaciones1.size(); i++) {
            Locacion v1;
            v1 = locaciones1.get(i);
            for (j = 0; j < locaciones1.size(); j++) {
                Locacion v2;
                v2 = locaciones1.get(j);
                if (i == j) {
                    continue;
                }
                grafo.addEdge(v1, v2);
            }
        }
        try {
            instance = new VRPProblem(grafo, otroPuntoPartida, vehiculoTipo);
        } catch (VRPProblemInvalidGraph ex) {
        }

        // Hacky tear down
        crearDatosGrafo();
    }
}
