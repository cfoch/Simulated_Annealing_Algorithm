/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 *
 * @author cfoch
 */
public class TestRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Result result;

        result = JUnitCore.runClasses(DefaultTestSuite.class);
        result.getFailures().forEach((failure) -> {
            System.out.println(failure.toString());
        });
        System.out.println(result.wasSuccessful());
    }

}
