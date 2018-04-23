/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import static com.grupo1.simulated_annealing.Utils.Color.generateHexColor;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.traverse.GraphIterator;

/**
 *
 * @author cfoch
 */
public final class SolutionVisualization {
    private static final Dimension DEFAULT_IMG_SIZE = new Dimension(800, 600);
    private static final int DEFAULT_COORD_SCALE = 20;
    private static final String DEFAULT_COLOR_DEPOT = "red";

    /**
     * Dummy constructor to avoid SolutionVisualization instantiation.
     */
    private SolutionVisualization() {
        // Nothing.
    }

    /**
     * Changes nodes position. This method is useful because mxGraph / JGraphT
     * adapter puts all nodes at (0, 0). This method change nodes position to
     * its real positions scaled against DEFAULT_COORD_SCALE.
     * @param xgraph a mxGraph..
     * @param problem a VRPProblem.
     * @param depotColor the depot color in hexadecimal or literal (e.g: blue).
     */
    private static void changeNodesPosition(final mxGraph xgraph,
            final VRPProblem problem, final String depotColor) {
        int c;
        xgraph.selectVertices();
        Object[] cellsObjs = xgraph.getSelectionCells();
        for (c = 0; c < cellsObjs.length; c++) {
            Locacion locacion;
            mxCell cell;
            mxCell[] cellArray;
            cell = (mxCell) cellsObjs[c];
            locacion = (Locacion) cell.getValue();


            cellArray = new mxCell[]{cell};
            xgraph.moveCells(cellArray,
                    locacion.getX() * DEFAULT_COORD_SCALE,
                    locacion.getY() * DEFAULT_COORD_SCALE);

            if (locacion == problem.getPuntoDePartida()) {
                xgraph.setCellStyles(mxConstants.STYLE_FILLCOLOR, depotColor,
                        cellArray);
            }
        }
    }

    /**
     * Clears the edges from the graph.
     * @param xgraph A mxGraph.
     */
    private static void clearEdges(final mxGraph xgraph) {
        int c;
        xgraph.clearSelection();
        xgraph.selectEdges();
        Object[] cellsEdgesObjs = xgraph.getSelectionCells();
        for (c = 0; c < cellsEdgesObjs.length; c++) {
            mxCell cell;
            cell = (mxCell) cellsEdgesObjs[c];
            cell.setVisible(false);
        }
    }

    /**
     * Draws the solution's routes edges with random colors.
     * @param xgraph A mxGraph.
     * @param problem A problem.
     * @param solution A solution as a list of GraphWalk.
     */
    private static void drawSolution(final mxGraph xgraph,
            final VRPProblem problem, final ArrayList<GraphWalk> solution) {
        int c;
        xgraph.clearSelection();
        xgraph.selectEdges();
        Object[] cellsEdgesObjs = xgraph.getSelectionCells();
        for (c = 0; c < solution.size(); c++) {
            int v;
            String color;
            List<Locacion> route;
            route = solution.get(c).getVertexList();
            color = generateHexColor();

            for (v = 0; v < route.size() - 1; v++) {
                int j;
                Locacion l1, l2;
                Pista pista;
                l1 = route.get(v);
                l2 = route.get(v + 1);
                pista = problem.getGrafo().getEdge(l1, l2);
                for (j = 0; j < cellsEdgesObjs.length; j++) {
                    mxCell cell;
                    cell = (mxCell) cellsEdgesObjs[j];
                    if (cell.isEdge()) {
                        Pista cellValue;
                        cellValue = (Pista) cell.getValue();
                        mxCell[] cellArray;
                        cellArray = new mxCell[]{cell};
                        if (cellValue == pista) {
                            cell.setVisible(true);
                            xgraph.setCellStyles(mxConstants.STYLE_ENDARROW,
                                    "none", cellArray);
                            xgraph.setCellStyles(mxConstants.STYLE_STROKECOLOR,
                                    color, cellArray);
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a JFrame with the visualization of the VRP problem and shows
     * the solution.
     * @param problem a VRPProblem.
     * @param solution a solution.
     * @return a JFrame.
     */
    public static JFrame getSolutionVisualizationFrame(final VRPProblem problem,
            final ArrayList<GraphWalk> solution) {
        mxGraph xgraph;
        JGraphXAdapter<Locacion, Pista> adapter;
        BufferedImage image;
        JFrame frame;
        mxGraphComponent graphComponent;
        mxGraphModel xmodel;
        GraphIterator<Locacion, Pista> iterator;
        Graphics2D graphics;
        File file;

        xgraph = new JGraphXAdapter<Locacion, Pista>(problem.getGrafo());
        xgraph.getModel().beginUpdate();
        changeNodesPosition(xgraph, problem, DEFAULT_COLOR_DEPOT);
        clearEdges(xgraph);
        drawSolution(xgraph, problem, solution);
        xgraph.getModel().endUpdate();

        graphComponent = new mxGraphComponent(xgraph);
        frame = new JFrame();
        frame.getContentPane().add(graphComponent);
        frame.resize(DEFAULT_IMG_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        return frame;
    }
}
