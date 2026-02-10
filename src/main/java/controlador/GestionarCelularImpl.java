package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.celular;
import modelo.marca;
import modelo.gama;

public class GestionarCelularImpl implements celularControlador {

    conexion c = new conexion();

    @Override
    public void registrar(celular ce) {

        String sql = """
        INSERT INTO celular
        (modelo, id_marca, precio, stock, sistema_operativo, gama)
        VALUES (?,?,?,?,?,?)
        """;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ce.getModelo());
            ps.setInt(2, ce.getId_marca().getId());
            ps.setDouble(3, ce.getPrecio());
            ps.setInt(4, ce.getStock());
            ps.setString(5, ce.getSistema_operativo());
            ps.setString(6, ce.getGama().name());

            ps.executeUpdate();
            System.out.println("REGISTRO EXITOSO!");

        } catch (SQLException e) {
            System.out.println("Error registrando celular: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(celular ce, int id) {

        String sql = """
        UPDATE celular
        SET modelo = ?,
            id_marca = ?,
            precio = ?,
            stock = ?,
            sistema_operativo = ?,
            gama = ?
        WHERE id = ?
        """;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ce.getModelo());
            ps.setInt(2, ce.getId_marca().getId());
            ps.setDouble(3, ce.getPrecio());
            ps.setInt(4, ce.getStock());
            ps.setString(5, ce.getSistema_operativo());
            ps.setString(6, ce.getGama().name());
            ps.setInt(7, id);

            ps.executeUpdate();
            System.out.println("Celular actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error actualizando celular: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM celular WHERE id=?";

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Celular eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error eliminando celular: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<celular> listar() {

        ArrayList<celular> celulares = new ArrayList<>();

        String sql = """
        SELECT c.id, c.modelo, c.precio, c.stock,
               c.sistema_operativo, c.gama,
               m.id AS marca_id, m.marca
        FROM celular c
        JOIN marca m ON c.id_marca = m.id
        ORDER BY c.id
        """;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                marca m = new marca(
                        rs.getInt("marca_id"),
                        rs.getString("marca")
                );

                celulares.add(new celular(
                        rs.getInt("id"),
                        rs.getInt("stock"),
                        m,
                        rs.getDouble("precio"),
                        rs.getString("modelo"),
                        rs.getString("sistema_operativo"),
                        gama.valueOf(rs.getString("gama"))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error listando celulares: " + e.getMessage());
        }

        return celulares;
    }

    @Override
    public celular buscar(int id) {

        String sql = """
        SELECT c.id, c.modelo, c.precio, c.stock,
               c.sistema_operativo, c.gama,
               m.id AS marca_id, m.marca
        FROM celular c
        JOIN marca m ON c.id_marca = m.id
        WHERE c.id = ?
        """;

        celular ce = null;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                marca m = new marca(
                        rs.getInt("marca_id"),
                        rs.getString("marca")
                );

                ce = new celular(
                        rs.getInt("id"),
                        rs.getInt("stock"),
                        m,
                        rs.getDouble("precio"),
                        rs.getString("modelo"),
                        rs.getString("sistema_operativo"),
                        gama.valueOf(rs.getString("gama"))
                );
            }

        } catch (SQLException e) {
            System.out.println("Error buscando celular: " + e.getMessage());
        }

        return ce;
    }

}
