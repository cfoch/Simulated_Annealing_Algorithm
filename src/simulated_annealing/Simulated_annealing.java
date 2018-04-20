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
        
        // Data
        Node node = new Node(60, 200,"A");
        RouteManager.addNode(node);
        Node node2 = new Node(180, 200,"B");
        RouteManager.addNode(node2);
        Node node3 = new Node(80, 180,"C");
        RouteManager.addNode(node3);
        Node node4 = new Node(140, 180,"D");
        RouteManager.addNode(node4);
        Node node5 = new Node(20, 160,"E");
        RouteManager.addNode(node5);
        Node node6 = new Node(100, 160,"F");
        RouteManager.addNode(node6);
        Node node7 = new Node(200, 160,"G");
        RouteManager.addNode(node7);
        Node node8 = new Node(140, 140,"H");
        RouteManager.addNode(node8);
        Node node9 = new Node(40, 120,"I");
        RouteManager.addNode(node9);
        Node node10 = new Node(100, 120,"J");
        RouteManager.addNode(node10);
        
        
        // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.003;

        // Initialize intial solution
        Route currentSolution = new Route();
        currentSolution.generateIndividual();
        
        System.out.println("Initial solution distance: " + currentSolution.getDistance());
        // Set as current best
        Route best = new Route(currentSolution.getRoute());
        System.out.println("Route: " + best);
        
        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Route newSolution = new Route(currentSolution.getRoute());

            // Get a random positions in the tour
            int tourPos1 = (int) (newSolution.routeSize()* Math.random());
            int tourPos2 = (int) (newSolution.routeSize() * Math.random());

            // Get the nodes at selected positions in the route
            Node nodeSwap1 = newSolution.getNodeFromRoute(tourPos1);
            Node nodeSwap2 = newSolution.getNodeFromRoute(tourPos2);

            // Swap them
            newSolution.setNode(tourPos2, nodeSwap1);
            newSolution.setNode(tourPos1, nodeSwap2);
            
            // Get energy of solutions
            int currentEnergy = currentSolution.getDistance();
            int neighbourEnergy = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new Route(newSolution.getRoute());
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Route(currentSolution.getRoute());
            }
            
            // Cool system
            temp *= 1-coolingRate;
        }
        // Here the route is printed by positions
        System.out.println("Final solution distance: " + best.getDistance());
        System.out.println("Route: " + best);
        
    }
    
}
