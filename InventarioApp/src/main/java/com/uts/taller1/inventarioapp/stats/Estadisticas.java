/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uts.taller1.inventarioapp.stats;

import com.uts.taller1.inventarioapp.events.ProductoCaro;
import com.uts.taller1.inventarioapp.model.Producto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class Estadisticas {
    private int creadosCaros;

    public int getCreadosCaros() { return creadosCaros; }

    public void onProductoCaro(@Observes @ProductoCaro Producto p) {
        creadosCaros++;
        System.out.println("[EVENTO] Producto caro creado: " + p.getCodigo() + " (" + p.getPrecio() + ")");
    }
}
