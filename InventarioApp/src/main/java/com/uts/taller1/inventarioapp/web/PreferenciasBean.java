/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uts.taller1.inventarioapp.web;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named("preferenciasBean")
@SessionScoped
public class PreferenciasBean implements Serializable {
    private String idioma = "es";
    private Map<String, Object> filtros = new HashMap<>();

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Map<String, Object> getFiltros() { return filtros; }
    public void setFiltros(Map<String, Object> filtros) { this.filtros = filtros; }

    public void setFiltro(String clave, Object valor) { filtros.put(clave, valor); }
    public Object getFiltro(String clave) { return filtros.get(clave); }
}
