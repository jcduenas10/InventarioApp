package com.uts.taller1.inventarioapp.controller;

import com.uts.taller1.inventarioapp.facade.ProductoFacade;
import com.uts.taller1.inventarioapp.model.Producto;
import com.uts.taller1.inventarioapp.web.MensajeBean;
import com.uts.taller1.inventarioapp.web.PreferenciasBean;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet controlador para Producto
 * @author Estudiante
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {

    @Inject
    private ProductoFacade facade;

    @Inject
    private MensajeBean mensaje;
    
    @Inject
    private PreferenciasBean prefs;

    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String codigo = req.getParameter("codigo");
    String nombre = req.getParameter("nombre");
    String categoria = req.getParameter("categoria");
    String precioStr = req.getParameter("precio");
    String stockStr = req.getParameter("stock");
    boolean activo = "on".equalsIgnoreCase(req.getParameter("activo"));

    try {
        Double precio = (precioStr == null || precioStr.isEmpty()) ? null : Double.valueOf(precioStr);
        Integer stock = (stockStr == null || stockStr.isEmpty()) ? null : Integer.valueOf(stockStr);

        Producto p = new Producto(codigo, nombre, categoria, precio, stock, activo);
        facade.crear(p);

        // 🔹 FLASH (guardar y redirigir)
        req.getSession().setAttribute("flashInfo", "Producto creado correctamente: " + p.getNombre());
        resp.sendRedirect(req.getContextPath() + "/productos");
    } catch (Exception e) {
        mensaje.setTextoError(e.getMessage());
        req.setAttribute("mensajeBean", mensaje);
        doGet(req, resp);
    }
}

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
        // 🔹 Leer parámetros
        String accion = req.getParameter("accion");
        String idStr = req.getParameter("id");
        String fcat = req.getParameter("fcat"); // filtro de categoría

        // 🔹 Eliminar producto si se pidió ?accion=eliminar&id=X
        if ("eliminar".equalsIgnoreCase(accion) && idStr != null) {
            int id = Integer.parseInt(idStr);
            facade.eliminar(id);

            // Guardar mensaje flash y redirigir
            req.getSession().setAttribute("flashInfo", "Producto eliminado (id=" + id + ")");
            resp.sendRedirect(req.getContextPath() + "/productos");
            return;
        }

        // 🔹 Aplicar o guardar filtro (si se envió por GET)
        if (fcat != null) {
            // Guardar preferencia de filtro en sesión
            prefs.setFiltro("categoria", fcat.isEmpty() ? null : fcat);
            // Redirigir para limpiar la URL (PRG pattern)
            resp.sendRedirect(req.getContextPath() + "/productos");
            return;
        }

        // 🔹 Consumir flash (si existe)
        Object flash = req.getSession().getAttribute("flashInfo");
        if (flash != null) {
            mensaje.setTextoInfo(String.valueOf(flash));
            req.getSession().removeAttribute("flashInfo");
        }

        // 🔹 Obtener filtro actual desde sesión
        String filtroCat = (String) prefs.getFiltro("categoria");

        // 🔹 Cargar productos (aplica filtro si existe)
        if (filtroCat == null || filtroCat.isEmpty()) {
            req.setAttribute("productos", facade.listar());
        } else {
            req.setAttribute("productos", facade.listarPorCategoria(filtroCat));
        }

        // 🔹 Pasar beans a la vista
        req.setAttribute("preferenciasBean", prefs);
        req.setAttribute("mensajeBean", mensaje);

    } catch (Exception e) {
        mensaje.setTextoError(e.getMessage());
        req.setAttribute("mensajeBean", mensaje);
    }

    // 🔹 Reenviar a la vista
    req.getRequestDispatcher("/productos.jsp").forward(req, resp);
}


}