/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uts.taller1.inventarioapp.config;

import com.uts.taller1.inventarioapp.web.PreferenciasBean;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.Locale;

public class LocaleProducer {
    @Inject
    private PreferenciasBean prefs;

    @Produces
    public Locale produceLocale() {
        String lang = prefs.getIdioma();
        return "en".equalsIgnoreCase(lang) ? Locale.ENGLISH : new Locale("es", "CO");
    }
}
