package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.celular;
import modelo.marca;
import modelo.gama;

public class GestionarCelularImpl implements celularControlador {

    conexion c = new conexion();

    @Override
    public void registrar(celular ce) {
        String sql = "INSERT INTO celular(modelo, id_marca, precio, stock, sistema_operativo, gama)) VALUES (?,?,?,?,?,?)";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ce.getModelo());
            ps.setObject(2, ce.getId_marca());
            ps.setDouble(2, ce.getPrecio());
            ps.setInt(3, ce.getStock());
            ps.setString(1, ce.getSistema_operativo());
            ps.setObject(1, ce.getGama());

            ps.executeUpdate();
            System.out.println("REGISTRO EXITOSO!");

        } catch (SQLException e) {
            System.out.println("Error registrando cliente: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(celular ce, int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<celular> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public celular buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
