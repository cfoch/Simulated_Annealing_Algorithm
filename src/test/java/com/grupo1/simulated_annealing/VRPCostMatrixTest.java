/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cfoch
 */
public class VRPCostMatrixTest {
    private static final double DELTA = 1e-15;
    private static final double [][] cost = {
        {1, 2},
        {4, 45}
    };
    private Locacion [] locaciones;

    public VRPCostMatrixTest() {
        int i;
        locaciones = new Locacion[cost.length];
        for (i = 0; i < locaciones.length; i++) {
            Locacion locacion;
            locacion = new Locacion(i, "Loc-" + i, Locacion.Tipo.OTRO, i, i);
            locaciones[i] = locacion;
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCost method, of class VRPCostMatrix.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetCost() throws Exception {
        System.out.println("getCost");
        VRPCostMatrix matrix = new VRPCostMatrix(cost, locaciones);
        Locacion locacionDoesNotExist = new Locacion(10000, "Loc-10000",
                Locacion.Tipo.OTRO, 100000, 100000);

        Assert.assertEquals(1, matrix.getCost(locaciones[0], locaciones[0]),
                DELTA);
        Assert.assertEquals(2, matrix.getCost(locaciones[0], locaciones[1]),
                DELTA);
        Assert.assertEquals(4, matrix.getCost(locaciones[1], locaciones[0]),
                DELTA);
        Assert.assertEquals(45, matrix.getCost(locaciones[1], locaciones[1]),
                DELTA);
        Assert.assertEquals(-1,
                matrix.getCost(locacionDoesNotExist, locaciones[0]), DELTA);
    }
}
