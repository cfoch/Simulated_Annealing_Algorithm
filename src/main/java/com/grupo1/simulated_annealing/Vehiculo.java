/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

import com.graphhopper.jsprit.core.problem.Capacity;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;

/**
 *
 * @author cfoch
 */
public class Vehiculo {
    /**
     * El tipo de Vehículo.
     */
    public static class Tipo {
        private final VehicleType vehicleType;

        /**
         * Crea un tipo de vehículo.
         * @param id Identificador del vehiculo.
         * @param capacidadPeso La capacidad máxima del vehículo.
         */
        Tipo(final String id, final int capacidadPeso) {
            VehicleTypeImpl.Builder builder;
            builder = VehicleTypeImpl.Builder.newInstance(id);
            builder.addCapacityDimension(Dimensiones.PESO.ordinal(),
                    capacidadPeso);
            vehicleType = builder.build();
        }

        /**
         * Obtiene el identificador del vehículo.
         * @return un id.
         */
        public final String getId() {
            return getVehicleType().getTypeId();
        }

        /**
         * Obtiene el peso soportado del vehículo.
         * @return (int) peso.
         */
        public final int getCapacidad() {
            Capacity capacity;
            capacity = getVehicleType().getCapacityDimensions();
            return capacity.get(Dimensiones.PESO.ordinal());
        }

        /**
         * Obtiene el VehiculeType de jsprit.
         * @return un VehicleType.
         */
        public final VehicleType getVehicleType() {
            return vehicleType;
        }
    }

    private Vehicle vehicle;
    private Vehiculo.Tipo tipo;

    /**
     * Crea un vehículo.
     * @param placa la placa de vehículo.
     * @param tipo el tipo de vehículo.
     * @param locacionInicio el depósito al cual pertenece el vehículo.
     */
    Vehiculo(final String placa, final Vehiculo.Tipo tipo,
            final Locacion locacionInicio) {
        VehicleImpl.Builder builder = VehicleImpl.Builder.newInstance(placa);
        builder.setType(tipo.getVehicleType());
        builder.setStartLocation(locacionInicio.getLocation());
        this.tipo = tipo;
        vehicle = builder.build();
    }

    /**
     * Obtiene un Vehicle de jsprit.
     * @return un Vehicle.
     */
    public final Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Obtiene el tipo de vehículo.
     * @return un Vehiculo.Tipo
     */
    public final Vehiculo.Tipo getTipo() {
        return tipo;
    }
}
