package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.cliente;

public class GestionarClienteImpl implements clienteControlador {

    conexion c = new conexion();

    @Override
    public void registrar(cliente cl) {
        String sql = "INSERT INTO cliente(nombre, identificacion, correo, telefono) VALUES (?,?,?,?)";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getIdentificacion());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getTelefono());

            ps.executeUpdate();
            System.out.println("REGISTRO EXITOSO!");

        } catch (SQLException e) {
            System.out.println("Error registrando cliente: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(cliente cl, int id) {
        String sql = "UPDATE cliente SET nombre=?, identificacion=?, correo=?, telefono=? WHERE id=?";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getIdentificacion());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getTelefono());
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("ACTUALIZACION EXITOSA!");
            } else {
                System.out.println("No existe cliente con id=" + id);
            }

        } catch (SQLException e) {
            System.out.println("Error actualizando cliente: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<cliente> listar() {
        ArrayList<cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nombre, identificacion, correo, telefono FROM cliente";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(new cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error listando clientes: " + e.getMessage());
        }

        return clientes;
    }

    @Override
    public cliente buscarPorId(int id) {
        String sql = "SELECT id, nombre, identificacion, correo, telefono FROM cliente WHERE id=?";
        cliente cl = null;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cl = new cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("identificacion"),
                            rs.getString("correo"),
                            rs.getString("telefono")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error buscando cliente: " + e.getMessage());
        }

        return cl;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("ELIMINACION EXITOSA!");
            } else {
                System.out.println("No existe cliente con id=" + id);
            }

        } catch (SQLException e) {
            System.out.println("Error eliminando cliente: " + e.getMessage());
        }
    }

    @Override
    public boolean existeIdentificacion(String identificacion) {
        String sql = "SELECT 1 FROM cliente WHERE identificacion = ? LIMIT 1";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, identificacion);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }

        } catch (SQLException e) {
            System.out.println("Error validando identificación: " + e.getMessage());
            return true; 
        }
    }

}
