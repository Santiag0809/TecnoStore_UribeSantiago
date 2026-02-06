package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import modelo.cliente;

public class GestionarClienteImpl implements clienteControlador {

    conexion c = new conexion();

    @Override
    public void registrar(cliente cl) {
        try (Connection con = c.conectar()) {
            PreparedStatement ps = con.prepareStatement("insert into cliente(nombre, identificacion, correo, telefono) values (?,?,?,?)");
            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getIdentificacion());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getTelefono());


            ps.executeUpdate();
            System.out.println("REGISTRO EXITOSO!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public cliente buscarPorId(int id) {
        System.out.println("buscar Id");
        return null;
    }

    @Override
    public boolean existeIdentificacion(String identificacion) {
        System.out.println("");
        return false;
    }

    @Override
    public List<cliente> listar() {
        System.out.println("lista");
        return null;
    }

}
