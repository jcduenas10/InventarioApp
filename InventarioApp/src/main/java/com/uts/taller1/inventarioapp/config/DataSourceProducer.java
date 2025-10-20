/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uts.taller1.inventarioapp.config;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class DataSourceProducer {

    @Resource(lookup = "jdbc/inventarioPool")
    private DataSource ds;  // viene de GlassFish

    @Produces
    public DataSource produceDataSource() {
        return ds;
    }
}
