package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.celular;
import modelo.cliente;
import modelo.detalleVenta;
import modelo.venta;

public class GestionarVentaImpl implements ventaControlador {

    conexion c = new conexion();

    @Override
    public void registrar(venta v) {

        String sql = "INSERT INTO venta(id_cliente, fecha, total) VALUES (?,?,?)";

        if (v.getItems() == null || v.getItems().isEmpty()) {
            System.out.println("La venta debe tener al menos un item.");
            return;
        }

        double subtotal = 0;
        for (detalleVenta it : v.getItems()) {
            if (it.getSubtotal() <= 0 && it.getCelular() != null) {
                it.setSubtotal(it.getCelular().getPrecio() * it.getCantidad());
            }
            subtotal += it.getSubtotal();
        }
        double total = subtotal + (subtotal * 0.19);
        v.setTotal(total);

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, v.getCliente().getId());
            ps.setDate(2, Date.valueOf(v.getFecha() != null ? v.getFecha() : LocalDate.now()));
            ps.setDouble(3, v.getTotal());

            ps.executeUpdate();

            int idVenta = 0;

            try (Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT MAX(id) AS id FROM venta");
                if (rs.next()) {
                    idVenta = rs.getInt("id");
                    v.setId(idVenta);
                }
            }

            if (idVenta == 0) {
                System.out.println("Error registrando venta: no se pudo obtener ID.");
                return;
            }

            for (detalleVenta it : v.getItems()) {

                int idCel = it.getCelular().getId();
                int cant = it.getCantidad();

                String sqlStock = "UPDATE celular SET stock = stock - ? WHERE id = ? AND stock >= ?";

                try (PreparedStatement ps2 = con.prepareStatement(sqlStock)) {
                    ps2.setInt(1, cant);
                    ps2.setInt(2, idCel);
                    ps2.setInt(3, cant);

                    int rows = ps2.executeUpdate();
                    if (rows == 0) {
                        System.out.println("Stock insuficiente para el celular id=" + idCel);
                        return;
                    }
                }

                String sqlDetalle = "INSERT INTO detalle_venta(id_venta, id_celular, cantidad, subtotal) VALUES (?,?,?,?)";

                try (PreparedStatement ps2 = con.prepareStatement(sqlDetalle)) {
                    ps2.setInt(1, idVenta);
                    ps2.setInt(2, idCel);
                    ps2.setInt(3, cant);
                    ps2.setDouble(4, it.getSubtotal());
                    ps2.executeUpdate();
                }
            }

            System.out.println("REGISTRO EXITOSO!");

        } catch (SQLException e) {
            System.out.println("Error registrando venta: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(venta v, int id) {

        String sql = "UPDATE venta SET id_cliente=?, fecha=?, total=? WHERE id=?";

        if (v.getItems() == null || v.getItems().isEmpty()) {
            System.out.println("La venta debe tener al menos un item.");
            return;
        }

        try (Connection con = c.conectar()) {

            String sqlOld = "SELECT id_celular, cantidad FROM detalle_venta WHERE id_venta=?";

            PreparedStatement ps = con.prepareStatement(sqlOld);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int idCel = rs.getInt("id_celular");
                int cant = rs.getInt("cantidad");

                String sqlRestore = "UPDATE celular SET stock = stock + ? WHERE id=?";

                try (PreparedStatement ps2 = con.prepareStatement(sqlRestore)) {
                    ps2.setInt(1, cant);
                    ps2.setInt(2, idCel);
                    ps2.executeUpdate();
                }
            }

            String sqlDelete = "DELETE FROM detalle_venta WHERE id_venta=?";

            try (PreparedStatement ps2 = con.prepareStatement(sqlDelete)) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            double subtotal = 0;
            for (detalleVenta it : v.getItems()) {
                if (it.getSubtotal() <= 0 && it.getCelular() != null) {
                    it.setSubtotal(it.getCelular().getPrecio() * it.getCantidad());
                }
                subtotal += it.getSubtotal();
            }
            double total = subtotal + (subtotal * 0.19);
            v.setTotal(total);

            try (PreparedStatement ps2 = con.prepareStatement(sql)) {

                ps2.setInt(1, v.getCliente().getId());
                ps2.setDate(2, Date.valueOf(v.getFecha() != null ? v.getFecha() : LocalDate.now()));
                ps2.setDouble(3, v.getTotal());
                ps2.setInt(4, id);

                int rows = ps2.executeUpdate();
                if (rows > 0) {
                    System.out.println("ACTUALIZACION EXITOSA!");
                } else {
                    System.out.println("No existe venta con id=" + id);
                    return;
                }
            }

            for (detalleVenta it : v.getItems()) {

                int idCel = it.getCelular().getId();
                int cant = it.getCantidad();

                String sqlStock = "UPDATE celular SET stock = stock - ? WHERE id = ? AND stock >= ?";

                try (PreparedStatement ps2 = con.prepareStatement(sqlStock)) {
                    ps2.setInt(1, cant);
                    ps2.setInt(2, idCel);
                    ps2.setInt(3, cant);

                    int rows = ps2.executeUpdate();
                    if (rows == 0) {
                        System.out.println("Stock insuficiente para el celular id=" + idCel);
                        return;
                    }
                }

                String sqlDetalle = "INSERT INTO detalle_venta(id_venta, id_celular, cantidad, subtotal) VALUES (?,?,?,?)";

                try (PreparedStatement ps2 = con.prepareStatement(sqlDetalle)) {
                    ps2.setInt(1, id);
                    ps2.setInt(2, idCel);
                    ps2.setInt(3, cant);
                    ps2.setDouble(4, it.getSubtotal());
                    ps2.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.out.println("Error actualizando venta: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<venta> listar() {

        ArrayList<venta> ventas = new ArrayList<>();

        String sql = """
    SELECT v.id,
           v.id_cliente,
           c.nombre,
           v.fecha,
           IFNULL(SUM(dv.subtotal), 0) AS subtotal,
           v.total
    FROM venta v
    JOIN cliente c ON c.id = v.id_cliente
    LEFT JOIN detalle_venta dv ON dv.id_venta = v.id
    GROUP BY v.id, v.id_cliente, c.nombre, v.fecha, v.total
    ORDER BY v.id
    """;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                cliente cl = new cliente();
                cl.setId(rs.getInt("id_cliente"));
                cl.setNombre(rs.getString("nombre"));

                venta v = new venta(
                        rs.getInt("id"),
                        cl,
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("total"),
                        new ArrayList<>()
                );

                ventas.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error listando ventas: " + e.getMessage());
        }

        return ventas;
    }

    @Override
    public venta buscarPorId(int id) {

        String sql = "SELECT id, id_cliente, fecha, total FROM venta WHERE id=?";
        venta v = null;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    cliente cl = new cliente();
                    cl.setId(rs.getInt("id_cliente"));

                    v = new venta(
                            rs.getInt("id"),
                            cl,
                            rs.getDate("fecha").toLocalDate(),
                            rs.getDouble("total"),
                            new ArrayList<>()
                    );
                } else {
                    return null;
                }
            }

            String sqlDetalle = "SELECT id, id_venta, id_celular, cantidad, subtotal FROM detalle_venta WHERE id_venta=?";

            try (PreparedStatement ps2 = con.prepareStatement(sqlDetalle)) {

                ps2.setInt(1, id);

                try (ResultSet rs2 = ps2.executeQuery()) {
                    while (rs2.next()) {

                        celular ce = new celular();
                        ce.setId(rs2.getInt("id_celular"));

                        detalleVenta dv = new detalleVenta();
                        dv.setId(rs2.getInt("id"));
                        dv.setId_venta(rs2.getInt("id_venta"));
                        dv.setCelular(ce);
                        dv.setCantidad(rs2.getInt("cantidad"));
                        dv.setSubtotal(rs2.getDouble("subtotal"));

                        v.getItems().add(dv);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error buscando venta: " + e.getMessage());
        }

        return v;
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM venta WHERE id=?";

        try (Connection con = c.conectar()) {

            String sqlOld = "SELECT id_celular, cantidad FROM detalle_venta WHERE id_venta=?";

            PreparedStatement ps = con.prepareStatement(sqlOld);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int idCel = rs.getInt("id_celular");
                int cant = rs.getInt("cantidad");

                String sqlRestore = "UPDATE celular SET stock = stock + ? WHERE id=?";

                try (PreparedStatement ps2 = con.prepareStatement(sqlRestore)) {
                    ps2.setInt(1, cant);
                    ps2.setInt(2, idCel);
                    ps2.executeUpdate();
                }
            }

            String sqlDelete = "DELETE FROM detalle_venta WHERE id_venta=?";

            try (PreparedStatement ps2 = con.prepareStatement(sqlDelete)) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(sql)) {

                ps2.setInt(1, id);
                int rows = ps2.executeUpdate();

                if (rows > 0) {
                    System.out.println("ELIMINACION EXITOSA!");
                } else {
                    System.out.println("No existe venta con id=" + id);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error eliminando venta: " + e.getMessage());
        }
    }

    public ArrayList<venta> listarConDetalles() {

        ArrayList<venta> ventas = new ArrayList<>();

        String sql = """
    SELECT v.id AS venta_id,
           v.fecha,
           v.total,
           c.id AS cliente_id,
           c.nombre AS cliente_nombre,
           dv.id AS detalle_id,
           dv.cantidad,
           dv.subtotal,
           ce.id AS celular_id,
           ce.modelo,
           ce.precio
    FROM venta v
    JOIN cliente c ON c.id = v.id_cliente
    LEFT JOIN detalle_venta dv ON dv.id_venta = v.id
    LEFT JOIN celular ce ON ce.id = dv.id_celular
    ORDER BY v.id
    """;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            java.util.LinkedHashMap<Integer, venta> map = new java.util.LinkedHashMap<>();

            while (rs.next()) {

                int idVenta = rs.getInt("venta_id");

                venta v = map.get(idVenta);

                if (v == null) {

                    cliente cl = new cliente();
                    cl.setId(rs.getInt("cliente_id"));
                    cl.setNombre(rs.getString("cliente_nombre"));

                    v = new venta(
                            idVenta,
                            cl,
                            rs.getDate("fecha").toLocalDate(),
                            rs.getDouble("total"),
                            new ArrayList<>()
                    );

                    map.put(idVenta, v);
                }

                int detalleId = rs.getInt("detalle_id");
                if (!rs.wasNull()) {

                    celular ce = new celular();
                    ce.setId(rs.getInt("celular_id"));
                    ce.setModelo(rs.getString("modelo"));
                    ce.setPrecio(rs.getDouble("precio"));

                    detalleVenta dv = new detalleVenta();
                    dv.setId(detalleId);
                    dv.setCelular(ce);
                    dv.setCantidad(rs.getInt("cantidad"));
                    dv.setSubtotal(rs.getDouble("subtotal"));

                    v.getItems().add(dv);
                }
            }

            ventas.addAll(map.values());

        } catch (SQLException e) {
            System.out.println("Error listando ventas con detalles: " + e.getMessage());
        }

        return ventas;
    }

}
