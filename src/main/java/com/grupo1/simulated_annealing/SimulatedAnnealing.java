/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import java.util.ArrayList;
import javax.swing.JFrame;
import org.jgrapht.graph.GraphWalk;

/**
 *
 * @author Bryam
 */
public final class SimulatedAnnealing {
    /**
     * The main application.
     */
    private SimulatedAnnealing() {
    }

    /**
     * Programa principal.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        VRPProblem problem;
        VRPAlgorithm algorithm;
        JFrame frame;
        ArrayList<GraphWalk> solution;
        ArrayList<Locacion> check;
        int i, j;

        problem = new VRPProblem("datasets/CVRP/augerat/A/A-n80-k10.vrp");
        algorithm = new VRPAlgorithm(problem);
        solution = algorithm.solve();

        algorithm.printSolution(solution);
        System.out.println("Best solution: "
                + algorithm.getSolutionWeight(solution));
        System.out.println("Solucion valida: "
                + algorithm.validateSolution(solution));

        frame = SolutionVisualization.getSolutionVisualizationFrame(problem,
                solution);
        frame.setVisible(true);
    }

}
