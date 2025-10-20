package com.uts.taller1.inventarioapp.facade;

import com.uts.taller1.inventarioapp.model.Producto;
import com.uts.taller1.inventarioapp.persistence.ProductoDAO;
import com.uts.taller1.inventarioapp.web.MensajeBean;
import com.uts.taller1.inventarioapp.web.PreferenciasBean;
import com.uts.taller1.inventarioapp.domain.ValidadorProducto;
import com.uts.taller1.inventarioapp.events.ProductoCaro;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;



/**
 * Fachada de negocio para Producto
 * @author Estudiante
 */
@Named("productoFacade")          // disponible por EL cuando migremos a JSF
@ApplicationScoped                // un servicio “singleton” a nivel de app
public class ProductoFacade {

    @Inject
    private ProductoDAO dao;      // Inyección del DAO

    @Inject
    private ValidadorProducto validador;

    @Inject
    private MensajeBean mensaje;  // Mensajes por request

    @Inject
    private PreferenciasBean prefs; // Preferencias en sesión (futuros filtros)
    
    @Inject @ProductoCaro
    private jakarta.enterprise.event.Event<Producto> prodCaroEvent;

    public List<Producto> listar() throws Exception {
        // (Opcional) usar prefs.getFiltros() para aplicar filtros por sesión en el futuro
        return dao.listar();
    }

    public void crear(Producto p) throws Exception {
        // Validaciones con el bean @Dependent
        validador.validarCodigo(p.getCodigo());
        validador.validarNombre(p.getNombre());
        validador.validarPrecio(p.getPrecio());
        validador.validarStock(p.getStock());

        // Regla: código único
        if (dao.buscarPorCodigo(p.getCodigo()).isPresent()) {
            throw new Exception("Ya existe un producto con código " + p.getCodigo());
        }

        dao.insertar(p);
        mensaje.setTextoInfo("Producto creado correctamente: " + p.getNombre());
        if (p.getPrecio() != null && p.getPrecio() > 1_000_000) { // umbral ejemplo
            prodCaroEvent.fire(p);
        }
    }

    public void eliminar(int id) throws Exception {
        dao.eliminarPorId(id);
        mensaje.setTextoInfo("Producto eliminado (id=" + id + ")");
    }
    
    
    public List<Producto> listarPorCategoria(String categoria) throws Exception {
    return dao.listarPorCategoria(categoria);
}

}