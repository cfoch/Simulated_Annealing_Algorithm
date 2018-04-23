/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.disjoint;
import java.util.List;
import java.util.Random;
import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

/**
 * Implementation of
 * A Simulated Annealing Algorithm for The Capacitated Vehicle Routing Problem.
 * @author cfoch
 */
public class VRPAlgorithm {
    public static final double DEFAULT_COOLING_RATE = 0.99;
    public static final int DEFAULT_INITIAL_TEMPERATURE = 5000;
    public static final double DEFAULT_ITERATION_MULTIPLIER = 1.05;
    public static final int DEFAULT_INITIAL_TIME_TO_UPDATE = 5;
    public static final int DEFAULT_MAX_TIME = 10000;
    public static final int DEFAULT_N_MOVE = 5;
    public static final int DEFAULT_N_RHA = 5;
    public static final double DEFAULT_MOVE_PROBABABILTY = 0.8;
    public static final double DEFAULT_MINIMUM_TEMPERATURE = 0.001;

    private VRPProblem problem;
    private double initialTemperature;
    private double coolingRate;
    private double iterationMultiplier;
    // M0 parameter.
    private int initialTimeToUpdate;
    private int maxTime;
    // Number of nodes to move according Move transformation.
    private int nMove;
    // Number of nodes to replace by Replace High Average transformation.
    private int nRHA;
    private double moveProbability;


    /**
     * Calculates the acceptance probability based on Boltzmann factor.
     * @param energy La energia en el estado actual.
     * @param newEnergy La nueva energía en el siguiente estado.
     * @param temperature La temperatura en el estado actual.
     * @return a double number between 0.0 and 1.0
     */
    public static final double acceptanceProbability(final double energy,
            final double newEnergy, final double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }

    /**
     * Crea un algoritmo para resolver un problema CVRP.
     * @param problem un VRPProblem.
     */
    public VRPAlgorithm(final VRPProblem problem) {
        this.problem = problem;
        coolingRate = DEFAULT_COOLING_RATE;
        initialTemperature = DEFAULT_INITIAL_TEMPERATURE;
        iterationMultiplier = DEFAULT_ITERATION_MULTIPLIER;
        initialTimeToUpdate = DEFAULT_INITIAL_TIME_TO_UPDATE;
        maxTime = DEFAULT_MAX_TIME;
        nMove = DEFAULT_N_MOVE;
        nRHA = DEFAULT_N_RHA;
        moveProbability = DEFAULT_MOVE_PROBABABILTY;
    }

    /**
     * Joins the edges of a list of GraphWalk into a single list. There may
     * be repeated edges.
     * @param solution a list of GraphWalk
     * @return a list of Pista.
     */
    private ArrayList<Pista> joinSolutionEdges(
            final ArrayList<GraphWalk> solution) {
        ArrayList<Pista> allEdges;
        int i;

        allEdges = new ArrayList<Pista>();
        for (i = 0; i < solution.size(); i++) {
            GraphWalk walk;
            ArrayList<Pista> edges;
            walk = solution.get(i);
            edges = new ArrayList<Pista>(walk.getEdgeList());
            allEdges.addAll(edges);
        }
        return allEdges;
    }

    /**
     * Gets the @n (or a valid number of) edges with the minimum distances
     * between them.
     * @param solution a list of GraphWalk
     * @param n the number of edges to return.
     * @return a list of Pista.
     */
    private ArrayList<Pista> getNMinimumEdges(
            final ArrayList<GraphWalk> solution, final int n) {
        ArrayList<Pista> allEdges;
        ArrayList<Pista> minimumEdges;
        int i;

        allEdges = joinSolutionEdges(solution);
        Collections.sort(allEdges);
        minimumEdges = new ArrayList<Pista>();
        for (i = 0; i < min(n, allEdges.size()); i++) {
            minimumEdges.add(allEdges.get(i));
        }
        return minimumEdges;
    }

    /**
     * Gets the list of targets nodes in a list of edges.
     * @param edges a list of Pista (edges).
     * @return a list of Locacion.
     */
    private ArrayList<Locacion> getFixedNodes(
            final ArrayList<Pista> edges) {
        ArrayList<Locacion> fixedNodes;
        int i;
        fixedNodes = new ArrayList<Locacion>();
        for (i = 0; i < edges.size(); i++) {
            Pista pista;
            Locacion target;
            pista = edges.get(i);
            target = problem.getGrafo().getEdgeTarget(pista);
            fixedNodes.add(target);
        }
        return fixedNodes;
    }

    /**
     * Picks a random number of vertices (nodes) from a Graph and removes them
     * from the current @solution.
     * @param n the number of elements to pick.
     * @param solution the solution.
     * @param fixedNodes the nodes that will not be removed.
     * @return a list of Locacion.
     */
    private ArrayList<Locacion> pickRandomMoves(final int n,
            final ArrayList<GraphWalk> solution,
            final ArrayList<Locacion> fixedNodes) {
        Random random;
        ArrayList<Locacion> randomNodes;

        random = new Random();
        randomNodes = new ArrayList<Locacion>();
        while (randomNodes.size() != n) {
            List<Locacion> routeNodes;
            ArrayList<Locacion> newRouteNodes;
            Locacion randomNode, prevNode, nextNode;
            GraphWalk newRoute;
            Pista pista;
            double totalWeight;
            int iRoute, iNode;

            iRoute = random.nextInt(solution.size());
            totalWeight = solution.get(iRoute).getWeight();
            routeNodes = solution.get(iRoute).getVertexList();
            iNode = random.nextInt(routeNodes.size());

            randomNode = routeNodes.get(iNode);
            // No tiene sentido borrar el depósito. Tampoco se permite escoger
            // un nodo protegido o fijo.
            if (randomNode == problem.getPuntoDePartida()
                    || fixedNodes.contains(randomNode)) {
                continue;
            }

            prevNode = routeNodes.get(iNode - 1);
            nextNode = routeNodes.get(iNode + 1);

            // Borrar al nodo de la solución.
            newRouteNodes = new ArrayList<Locacion>(routeNodes);
            newRouteNodes.remove(iNode);
            // Agregarlo a la lista de nodos a insertar en rutas aleatorias.
            randomNodes.add(randomNode);
            // Si solo quedan dos nodos (depósitos), borrar la ruta.
            if (newRouteNodes.size() == 2) {
                solution.remove(iRoute);
                continue;
            }
            newRoute = new GraphWalk(problem.getGrafo(), newRouteNodes,
                    getRouteWeight(newRouteNodes));
            solution.set(iRoute, newRoute);
        }
        return randomNodes;
    }

    /**
     * Checks if it is possible to insert a new node in all solution's routes.
     * @param solution A solution
     * @param weightToAdd The weight to check validation against.
     * @param maxWeight The maximum weight per route.
     * @return true if it is possible to add a new node with @maxWeight,
     *          false otherwise.
     */
    private boolean allInfactible(final ArrayList<GraphWalk> solution,
            final double weightToAdd, final double maxWeight) {
        int i;
        for (i = 0; i < solution.size(); i++) {
            GraphWalk walk;
            ArrayList<Locacion> locaciones;
            double routeDemand;
            walk = solution.get(i);
            locaciones = new ArrayList<Locacion>(walk.getVertexList());
            routeDemand = this.getRouteDemand(locaciones);
            if (routeDemand + weightToAdd <= maxWeight) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a solution following the Move transformation.
     * @param oldSolution A solution.
     * @return A solution.
     */
    private ArrayList<GraphWalk> newMoveSolution(
            final ArrayList<GraphWalk> oldSolution) {
        ArrayList<Pista> minEdges;
        ArrayList<Pista> allEdges;
        ArrayList<Locacion> fixedNodes;
        ArrayList<Locacion> randomNodes;
        ArrayList<GraphWalk> solution;
        Random random;

        solution = new ArrayList<GraphWalk>(oldSolution);

        // allEdges = joinSolutionEdges(solution);
        minEdges = getNMinimumEdges(solution, nMove);
        // Get the list of nodes that will not be moved.
        fixedNodes = getFixedNodes(minEdges);
        // Pick the random nodes that will be re-inserted.
        randomNodes = pickRandomMoves(nMove, solution, fixedNodes);
        assert disjoint(fixedNodes, randomNodes);

        random = new Random();
        while (!randomNodes.isEmpty()) {
            Locacion locacion;
            GraphWalk route, newRoute;
            ArrayList<Locacion> routeList;
            ArrayList<Locacion> newNodes;
            int iRoute;
            int iNode;
            double newTotalDemand, newTotalWeight;
            boolean createRoute;

            iNode = randomNodes.size() - 1;
            locacion = randomNodes.get(iNode);

            createRoute = allInfactible(solution,
                    locacion.getServicio().getDemanda(),
                    problem.getVehiculoTipo().getCapacidad());

            // Si todas las rutas actuales son infactibles, crear una nueva.
            if (!createRoute) {
                iRoute = random.nextInt(solution.size());
                route = solution.get(iRoute);
            } else {
                ArrayList<Locacion> depositos;
                double weightDeposito;
                depositos = new ArrayList<Locacion>();
                depositos.add(problem.getPuntoDePartida());
                depositos.add(problem.getPuntoDePartida());
                route = new GraphWalk(problem.getGrafo(), depositos, 0.0);
                solution.add(route);
                iRoute = solution.size() - 1;
            }

            routeList = new ArrayList<Locacion>(route.getVertexList());
            newTotalDemand = getRouteDemand(routeList)
                    + locacion.getServicio().getDemanda();

            // Si no es factible, continuar.
            if (newTotalDemand > problem.getVehiculoTipo().getCapacidad()) {
                continue;
            }

            newNodes = new ArrayList<Locacion>(route.getVertexList());
            // Borrar nodo usado.
            randomNodes.remove(iNode);
            // Insertar nodo en ruta aleatoria y actualizar la ruta en solución.
            newNodes.add(newNodes.size() - 1, locacion);
            newRoute = new GraphWalk(problem.getGrafo(), newNodes,
                    getRouteWeight(newNodes));
            solution.set(iRoute, newRoute);
        }
        return solution;
    }

    /**
     * Generates the first solution.
     * @return a solution (list of routes(GraphWalk))
     */
    private ArrayList<GraphWalk> getFirstSolution() {
        ArrayList<Vehiculo> vehiculos;
        ArrayList<Locacion> servicios;
        ArrayList<Locacion> serviciosVisitados;
        Graph<Locacion, Pista> grafo;
        ArrayList<GraphWalk> solucion;
        Integer iRuta = 0;
        int i = 0;

        solucion = new ArrayList<GraphWalk>();
        serviciosVisitados = new ArrayList<Locacion>();
        servicios = new ArrayList<Locacion>(problem.getGrafo().vertexSet());

        while (servicios.size() - 1 != serviciosVisitados.size()) {
            ArrayList<Locacion> ruta;
            GraphWalk walk;
            int demandaTotal = 0;
            double totalWeight = 0;

            ruta = new ArrayList<Locacion>();
            for (i = 0; i < servicios.size(); i++) {
                Locacion servicio;
                int nuevaDemanda;
                servicio = servicios.get(i);
                // Excluir el depósito de la ruta.
                if (servicio == problem.getPuntoDePartida()) {
                    continue;
                }
                nuevaDemanda =
                        servicio.getServicio().getDemanda() + demandaTotal;
                if (nuevaDemanda < problem.getVehiculoTipo().getCapacidad()) {
                    if (!serviciosVisitados.contains(servicio)) {
                        if (!ruta.isEmpty()) {
                            Pista pista;
                            pista = problem.getGrafo().getEdge(
                                    ruta.get(ruta.size() - 1), servicio);
                            totalWeight += pista.getWeight();
                        }
                        ruta.add(servicio);
                        serviciosVisitados.add(servicio);
                        demandaTotal = nuevaDemanda;
                    }
                }
            }
            // Agregar depósito como punto inicial y final.
            ruta.add(0, problem.getPuntoDePartida());
            ruta.add(problem.getPuntoDePartida());
            walk = new GraphWalk(problem.getGrafo(), ruta,
                    getRouteWeight(ruta));
            solucion.add(walk);
            iRuta++;
        }
        return solucion;
    }

    /**
     * Gets the energy of a given solution.
     * @param solution A solution
     * @return The energy or cost of the solution.
     */
    private double getSolutionEnergy(final List<GraphWalk> solution) {
        int i;
        double totalEnergy = 0;
        for (i = 0; i < solution.size(); i++) {
            totalEnergy += solution.get(i).getWeight();
        }
        return totalEnergy;
    }

    /**
     * Gets the total energy (or weight) of a given route.
     * @param locaciones A route.
     * @return A weight.
     */
    private double getRouteWeight(final List<Locacion> locaciones) {
        int l;
        double totalWeight = 0;
        for (l = 0; l < locaciones.size() - 1; l++) {
            Pista pista;
            Locacion l1, l2;
            l1 = locaciones.get(l);
            l2 = locaciones.get(l + 1);
            pista = problem.getGrafo().getEdge(l1, l2);
            totalWeight += pista.getWeight();
        }
        return totalWeight;
    }

    /**
     * Gets the route's demand.
     * @param locaciones A route
     * @return The demand.
     */
    private int getRouteDemand(final ArrayList<Locacion> locaciones) {
        int l;
        int demand = 0;
        for (l = 0; l < locaciones.size(); l++) {
            Locacion locacion;
            locacion = locaciones.get(l);
            if (locacion.esServicio()) {
                demand += locacion.getServicio().getDemanda();
            }
        }
        return demand;
    }

    /**
     * Obtiene el costo total de una solución.
     * @param solution Una solución.
     * @return el costo/peso total.
     */
    public final double getSolutionWeight(final ArrayList<GraphWalk> solution) {
        return getSolutionEnergy(solution);
    }

    /**
     * Verifica que una solución sea válida. Eso es
     * 1. No puede haber nodos repetidos en dos o más rutas.
     * 2. La demanda total de una ruta no debe superar la capacidad del
     *    vehículo.
     * @param solution Una solución.
     * @return true si es válido, o false en caso contrario.
     */
    public final boolean validateSolution(final ArrayList<GraphWalk> solution) {
        ArrayList<Locacion> check;
        int i, j;

        check = new ArrayList<Locacion>();
        for (i = 0; i < solution.size(); i++) {
            GraphWalk walk;
            List<Locacion> locaciones;
            ArrayList<Locacion> locacionesLista;
            double demanda;
            locaciones = solution.get(i).getVertexList();
            locacionesLista = new ArrayList<Locacion>(locaciones);
            demanda = getRouteDemand(locacionesLista);
            if (demanda > problem.getVehiculoTipo().getCapacidad()) {
                return false;
            }
            for (j = 0; j < locaciones.size(); j++) {
                if (j != 0 && j != locaciones.size() - 1) {
                    if (check.contains(locaciones.get(j))) {
                        return false;
                    }
                    check.add(locaciones.get(j));
                }
            }
        }
        return true;
    }

    /**
     * Imprime una solución a stdout.
     * @param solution una lista de rutas (GraphWalk)
     */
    public final void printSolution(final ArrayList<GraphWalk> solution) {
        ArrayList<Locacion> check;
        int i, j;

        check = new ArrayList<Locacion>();
        for (i = 0; i < solution.size(); i++) {
            GraphWalk walk;
            List<Locacion> locaciones;
            System.out.println("Ruta " + i);
            locaciones = solution.get(i).getVertexList();
            for (j = 0; j < locaciones.size(); j++) {
                if (j != 0 && j != locaciones.size() - 1) {
                    if (check.contains(locaciones.get(j))) {
                        System.out.println("Error logico");
                    }
                    check.add(locaciones.get(j));
                }
                System.out.println(locaciones.get(j).getNombre());
            }
            System.out.println("======================");
        }
    }

    /**
     * Resuelve el problema de CVRP.
     * @return una lista de rutas (GraphWalk).
     */
    public final ArrayList<GraphWalk> solve() {
        ArrayList<GraphWalk> currentSolution, bestSolution;
        double time;
        double timeToUpdate;
        double newInitialTimeToUpdate;
        double temperature;
        double currentEnergy, bestEnergy;


        // Initial values.
        temperature = initialTemperature;
        time = 0;
        newInitialTimeToUpdate = initialTimeToUpdate;

        currentSolution = getFirstSolution();
        currentEnergy = getSolutionEnergy(currentSolution);

        bestSolution = currentSolution;
        bestEnergy = currentEnergy;

        do {
            timeToUpdate = newInitialTimeToUpdate;
            do {
                double newEnergy;
                double deltaEnergy;
                ArrayList<GraphWalk> newSolution;

                // TODO
                // Replace Highest Average transformation missing.
                newSolution = newMoveSolution(currentSolution);
                newEnergy = getSolutionEnergy(newSolution);
                deltaEnergy = newEnergy - currentEnergy;
                if (deltaEnergy < 0) {
                    currentSolution = newSolution;
                    currentEnergy = newEnergy;
                    if (newEnergy < bestEnergy) {
                        bestSolution = newSolution;
                        bestEnergy = newEnergy;
                    }
                } else {
                    double boltzmann, randomNumber;
                    boltzmann = VRPAlgorithm.acceptanceProbability(
                            currentEnergy, newEnergy, temperature);
                    randomNumber = Math.random();
                    if (randomNumber < boltzmann) {
                        currentSolution = newSolution;
                        currentEnergy = newEnergy;
                    }
                }
                timeToUpdate--;
            } while (timeToUpdate >= 0);
            time += newInitialTimeToUpdate;
            temperature *= coolingRate;
            newInitialTimeToUpdate *= iterationMultiplier;
        } while (time < maxTime && temperature > DEFAULT_MINIMUM_TEMPERATURE);
        return bestSolution;
    }

    /**
     * Gets the initial system's temperature.
     * @return the temp
     */
    public final double getInitialTemperature() {
        return initialTemperature;
    }

    /**
     * Sets the initial system's temperature..
     * @param temp the temperature.
     */
    public final void setinitialTemperature(final double temp) {
        this.initialTemperature = temp;
    }

    /**
     * Gets the cooling rate the temperature will decrease with.
     * @return the coolingRate
     */
    public final double getCoolingRate() {
        return coolingRate;
    }

    /**
     * Sets the cooling rate the temperature will decrease with.
     * @param coolingRate the coolingRate to set
     */
    public final void setCoolingRate(final double coolingRate) {
        this.coolingRate = coolingRate;
    }
}
