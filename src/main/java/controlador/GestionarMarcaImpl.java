package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.marca;

public class GestionarMarcaImpl implements marcaControlador {

    private conexion c = new conexion();

    @Override
    public void registrar(marca m) {
        String sql = "INSERT INTO marca (marca) VALUES (?)";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getMarca());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error registering brand: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<marca> listar() {
        ArrayList<marca> marcas = new ArrayList<>();
        String sql = "SELECT * FROM marca";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                marcas.add(new marca(
                        rs.getInt("id"),
                        rs.getString("marca")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error listing brands: " + e.getMessage());
        }

        return marcas;
    }

    @Override
    public void actualizar(marca m) {
        String sql = "UPDATE marca SET marca=? WHERE id=?";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getMarca());
            ps.setInt(2, m.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating brand: " + e.getMessage());
        }
    }

    @Override
    public marca buscarPorId(int id) {
        String sql = "SELECT * FROM marca WHERE id=?";
        marca m = null;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new marca(
                        rs.getInt("id"),
                        rs.getString("marca")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding brand: " + e.getMessage());
        }

        return m;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM marca WHERE id=?";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting brand: " + e.getMessage());
        }
    }
}
