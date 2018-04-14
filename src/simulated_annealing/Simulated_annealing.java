/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulated_annealing;

/**
 *
 * @author Bryam
 */
public class Simulated_annealing {

    /**
     * @param args the command line arguments
     */
     
    // Calculate the acceptance probability
    public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
        
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hola a todos");
    }
    
}
