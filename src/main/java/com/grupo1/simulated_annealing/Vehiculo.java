/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo1.simulated_annealing;

/**
 *
 * @author cfoch
 */
public class Vehiculo {
    /**
     * El tipo de Vehículo.
     */
    public static class Tipo {
        private final String id;
        private final Dimensiones dimension;
        private final int capacidad;

        /**
         * Crea un tipo de vehículo.
         * @param id Identificador del vehiculo.
         * @param capacidadPeso La capacidad máxima del vehículo.
         */
        public Tipo(final String id, final int capacidadPeso) {
            // Solo PESO soportado por ahora.
            this.id = id;
            this.dimension = Dimensiones.PESO;
            this.capacidad = capacidadPeso;
        }

        /**
         * Obtiene el identificador del vehículo.
         * @return un id.
         */
        public final String getId() {
            return id;
        }

        /**
         * Obtiene el peso soportado del vehículo.
         * @return (int) peso.
         */
        public final int getCapacidad() {
            return capacidad;
        }
    }

    private Vehiculo.Tipo tipo;
    private String placa;
    private Locacion locacionInicio;

    /**
     * Crea un vehículo.
     * @param placa la placa de vehículo.
     * @param tipo el tipo de vehículo.
     * @param locacionInicio el depósito al cual pertenece el vehículo.
     */
    public Vehiculo(final String placa, final Vehiculo.Tipo tipo,
            final Locacion locacionInicio) {
        this.placa = placa;
        this.tipo = tipo;
        this.locacionInicio = locacionInicio;
    }

    /**
     * Obtiene el tipo de vehículo.
     * @return un Vehiculo.Tipo
     */
    public final Vehiculo.Tipo getTipo() {
        return tipo;
    }
}
