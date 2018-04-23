/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import java.util.Random;

/**
 *
 * @author cfoch
 */
public class Utils {
    /**
     * Utility methods related to colors.
     */
    public static class Color {
        private static final int MAX_COLOR = 255;
        /**
         * Generates a random hexadecimal color.
         * @return a Hexadecimal color represented as a String.
         */
        public static String generateHexColor() {
            Random random;
            int randomNumber;
            random = new Random();
            randomNumber = random.nextInt(MAX_COLOR * MAX_COLOR * MAX_COLOR);
            return String.format("#%06x", randomNumber);
        }
    }
}
