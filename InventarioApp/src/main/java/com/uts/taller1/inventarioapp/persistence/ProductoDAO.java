package com.uts.taller1.inventarioapp.persistence;

import com.uts.taller1.inventarioapp.model.Producto;

import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class ProductoDAO {
    
    @Inject
    private DataSource ds;

    private Producto map(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getInt("id"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setCategoria(rs.getString("categoria"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("stock"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }

    public List<Producto> listar() throws SQLException {
        String sql = "SELECT id, codigo, nombre, categoria, precio, stock, activo FROM productos ORDER BY nombre";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Producto> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        }
    }

    public Optional<Producto> buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT id, codigo, nombre, categoria, precio, stock, activo FROM productos WHERE codigo = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
                return Optional.empty();
            }
        }
    }

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio, stock, activo) VALUES (?,?,?,?,?,?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setBoolean(6, Boolean.TRUE.equals(p.getActivo()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getInt(1));
            }
        }
    }

    public void eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<Producto> listarPorCategoria(String categoria) throws SQLException {
    String sql = "SELECT id, codigo, nombre, categoria, precio, stock, activo " +
                 "FROM productos WHERE categoria = ? ORDER BY nombre";
    try (Connection con = ds.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, categoria);
        try (ResultSet rs = ps.executeQuery()) {
            List<Producto> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        }
    }
}

}
