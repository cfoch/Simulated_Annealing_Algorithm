/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        //problem = new VRPProblem("datasets/CVRP/augerat/A/A-n80-k10.vrp");
        Vehiculo.Tipo vehiculoTipo = new Vehiculo.Tipo("Foo", 100);
        problem = new VRPProblem(32, 40, 40, 1000, vehiculoTipo);
        try {
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
        } catch (VRPAlgorithm.VRPAlgorithmSolutionNotPossible ex) {
            Logger.getLogger(SimulatedAnnealing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
