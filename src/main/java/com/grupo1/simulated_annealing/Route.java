package com.grupo1.simulated_annealing;
import java.util.ArrayList;
import java.util.Collections;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bryam
 */
public class Route {
    private ArrayList route = new ArrayList<Node>();
    
    private int distance = 0;
    
    // Empty Route
    public Route(){
        for(int i = 0; i < RouteManager.routeSize(); i++){
            route.add(null);
        }
    }
    
    // Creates a route from another route
    public Route(ArrayList route){
        this.route = (ArrayList)route.clone();
    }
    
    // Get route information | nodes
    public ArrayList getRoute(){
        return this.route;
    }
    
     // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination nodes and add them to our route
        for (int i = 0; i < RouteManager.routeSize(); i++) {
          setNode(i, RouteManager.getNode(i));
        }
        // Randomly reorder the tour
        //Collections.shuffle(route);
    }
    // Sets a node in a route given certain position, the doistance gets restarted
    public void setNode(int routePosition, Node node){
        route.set(routePosition, node);
        distance = 0;
    }
    
    public Node getNodeFromRoute(int pos){
        return (Node)route.get(pos);
    }
    
    // Get number of nodes on the route
    public int routeSize() {
        return route.size();
    }
    
    // Gets total distance of the route
    public int getDistance(){
        if (distance == 0) {
            int nodeDistance = 0;
            // For all routes
            for (int i=0; i < routeSize(); i++) {
                // Get "from" node
                Node fromNode = getNodeFromRoute(i);
                // "To" node
                Node destinationNode;
                // Check we're not on our route's last node, if we are set our 
                // route's final destination node to our starting node
                if(i+1 < routeSize()){
                    destinationNode = getNodeFromRoute(i+1);
                }
                else{
                    destinationNode = getNodeFromRoute(0);
                }
                // Get the distance between the two cities
                nodeDistance += fromNode.nodeDistance(destinationNode);
            }
            distance = nodeDistance;
        }
        return distance;
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < routeSize(); i++) {
            geneString += getNodeFromRoute(i)+"|";
        }
        return geneString;
    }
    
}
