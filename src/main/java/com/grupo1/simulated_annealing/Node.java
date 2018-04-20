/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

/**
 *
 * @author Bryam
 */
public class Node {
    
    // Coordinates
    private int x;
    private int y;
    private String identifier;
    
    public Node(int x, int y, String identifier){
        this.x = x;
        this.y = y;
        this.identifier = identifier;
    }
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
     /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    
    // Euclidian distance
    public double nodeDistance(Node node){
        int x = Math.abs(getX() - node.getX());
        int y = Math.abs(getY() - node.getY());
        return Math.sqrt((x*x) + (y*y));
    }
    
    @Override
    public String toString(){
        return getIdentifier() + "(" + getX()+", "+getY() + ")";
    }
}
