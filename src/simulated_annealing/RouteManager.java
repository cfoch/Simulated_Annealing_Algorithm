/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulated_annealing;
import java.util.ArrayList;

/**
 *
 * @author Bryam
 */
public class RouteManager {
    private static ArrayList destinationNodes = new ArrayList<Node>();
    
    public static void addNode(Node node){
        destinationNodes.add(node);
    }
    
    public static Node getNode(int index){
        return (Node)destinationNodes.get(index);
    }
    
    public static int routeSize(){
        return destinationNodes.size();
    }
}
