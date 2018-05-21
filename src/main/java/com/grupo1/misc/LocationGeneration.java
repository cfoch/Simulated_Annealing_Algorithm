/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.misc;

import com.grupo1.simulated_annealing.Locacion;
import com.grupo1.simulated_annealing.Servicio;
import com.grupo1.simulated_annealing.Vehiculo;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author cfoch
 */
public final class LocationGeneration {
    private static final int GRADOS_DECIMALES_A_METROS = 111320;
    /**
     * LocationGeneration.
     */
    private LocationGeneration() {
    }

    /**
     * Obtiene coordenadas aleatoriamente dado un punto dentro de cierto radio.
     * @param n Número de coordenadas a obtener.
     * @param x0 Posición en x.
     * @param y0 posición en y.
     * @param radio El radio en metros.
     * @return Lista de coordenadas.
     */
    public static ArrayList<Pair<Double, Double>> genGeoCoords(final int n,
            final double x0, final double y0, final double radio) {
        ArrayList<Pair<Double, Double>> ret = new ArrayList<>();
        double radioGrados, r;
        int i;
        Random rand = new Random();

        radioGrados = radio / GRADOS_DECIMALES_A_METROS;
        r = radioGrados;
        for (i = 0; i < n; i++) {
            double u, v, w, t, x, y;
            Double xLat, yLon;
            Pair<Double, Double> coord;
            u = rand.nextDouble();
            v = rand.nextDouble();

            w = r * sqrt(u);
            t = 2 * Math.PI * v;
            x = w * cos(t);
            y = w * sin(t);

            xLat = x + x0;
            yLon = y + y0;

            coord = Pair.of(xLat, yLon);
            ret.add(coord);
        }
        return ret;
    }

    /**
     * Genera locaciones aleatorias con servicios (opcionalmente) dentro de
     * cierto radio.
     * @param n Número de coordenadas a obtener.
     * @param x0 Posición en x.
     * @param y0 posición en y.
     * @param radio El radio en metros.
     * @param generarServicio Asignar o no un servicio a cada locación.
     * @param tipoVehiculo El tipo de vehiculo.
     * @return Lista de coordenadas.
     */
    public static ArrayList<Locacion> genLocaciones(final int n,
            final double x0, final double y0, final double radio,
            final boolean generarServicio, final Vehiculo.Tipo tipoVehiculo) {
        ArrayList<Locacion> locaciones = new ArrayList<>();
        ArrayList<Pair<Double, Double>> coords;
        Random random;
        int i;

        random = new Random();
        coords = genGeoCoords(n, x0, y0, radio);
        for (i = 0; i < coords.size(); i++) {
            Locacion locacion;
            if (i == 0) {
                Servicio servicio;
                locacion = new Locacion(i, "Locacion " + i,
                    Locacion.Tipo.DEPOSITO,
                    coords.get(i).getLeft(), coords.get(i).getRight());
                servicio = new Servicio("Servicio " + i, locacion, 0);
                locacion.setServicio(servicio);
            } else {
                locacion = new Locacion(i, "Locacion " + i,
                    Locacion.Tipo.OTRO,
                    coords.get(i).getLeft(), coords.get(i).getRight());
                if (generarServicio && tipoVehiculo != null) {
                    Servicio servicio;
                    int demanda;
                    demanda = random.nextInt(tipoVehiculo.getCapacidad()) + 1;
                    servicio = new Servicio("Servicio " + i, locacion, demanda);
                    locacion.setServicio(servicio);
                }
            }
            locaciones.add(locacion);
        }
        return locaciones;
    }
}
