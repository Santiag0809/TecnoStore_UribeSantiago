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

        String sqlVenta = """
        INSERT INTO venta
        (id_cliente, fecha, total)
        VALUES (?,?,?)
        """;

        String sqlDetalle = """
        INSERT INTO detalle_venta
        (id_venta, id_celular, cantidad, subtotal)
        VALUES (?,?,?,?)
        """;

        String sqlStock = """
        UPDATE celular
        SET stock = stock - ?
        WHERE id = ? AND stock >= ?
        """;

        try (Connection con = c.conectar()) {

            con.setAutoCommit(false);

            if (v.getItems() == null || v.getItems().isEmpty()) {
                System.out.println("La venta debe tener al menos un item.");
                con.rollback();
                con.setAutoCommit(true);
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

            int idVenta;

            try (PreparedStatement ps = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, v.getCliente().getId());
                ps.setDate(2, Date.valueOf(v.getFecha() != null ? v.getFecha() : LocalDate.now()));
                ps.setDouble(3, v.getTotal());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        con.rollback();
                        con.setAutoCommit(true);
                        System.out.println("Error registrando venta: no se pudo obtener ID.");
                        return;
                    }
                    idVenta = rs.getInt(1);
                    v.setId(idVenta);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlDetalle); PreparedStatement ps2 = con.prepareStatement(sqlStock)) {

                for (detalleVenta it : v.getItems()) {

                    int idCel = it.getCelular().getId();
                    int cant = it.getCantidad();

                    ps2.setInt(1, cant);
                    ps2.setInt(2, idCel);
                    ps2.setInt(3, cant);

                    int rows = ps2.executeUpdate();
                    if (rows == 0) {
                        con.rollback();
                        con.setAutoCommit(true);
                        System.out.println("Stock insuficiente para el celular id=" + idCel);
                        return;
                    }

                    it.setId_venta(idVenta);

                    ps.setInt(1, it.getId_venta());
                    ps.setInt(2, idCel);
                    ps.setInt(3, cant);
                    ps.setDouble(4, it.getSubtotal());
                    ps.executeUpdate();
                }
            }

            con.commit();
            con.setAutoCommit(true);
            System.out.println("REGISTRO EXITOSO!");

        } catch (SQLException e) {
            System.out.println("Error registrando venta: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(venta v, int id) {

        String sqlRestoreStock = """
        UPDATE celular c
        JOIN detalle_venta dv ON dv.id_celular = c.id
        SET c.stock = c.stock + dv.cantidad
        WHERE dv.id_venta = ?
        """;

        String sqlDeleteDetalle = """
        DELETE FROM detalle_venta
        WHERE id_venta = ?
        """;

        String sqlUpdateVenta = """
        UPDATE venta
        SET id_cliente = ?,
            fecha = ?,
            total = ?
        WHERE id = ?
        """;

        String sqlDetalle = """
        INSERT INTO detalle_venta
        (id_venta, id_celular, cantidad, subtotal)
        VALUES (?,?,?,?)
        """;

        String sqlStock = """
        UPDATE celular
        SET stock = stock - ?
        WHERE id = ? AND stock >= ?
        """;

        try (Connection con = c.conectar()) {

            con.setAutoCommit(false);

            if (v.getItems() == null || v.getItems().isEmpty()) {
                System.out.println("La venta debe tener al menos un item.");
                con.rollback();
                con.setAutoCommit(true);
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sqlRestoreStock)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlDeleteDetalle)) {
                ps.setInt(1, id);
                ps.executeUpdate();
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

            try (PreparedStatement ps = con.prepareStatement(sqlUpdateVenta)) {

                ps.setInt(1, v.getCliente().getId());
                ps.setDate(2, Date.valueOf(v.getFecha() != null ? v.getFecha() : LocalDate.now()));
                ps.setDouble(3, v.getTotal());
                ps.setInt(4, id);

                int rows = ps.executeUpdate();
                if (rows == 0) {
                    con.rollback();
                    con.setAutoCommit(true);
                    System.out.println("No existe venta con id=" + id);
                    return;
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlDetalle); PreparedStatement ps2 = con.prepareStatement(sqlStock)) {

                for (detalleVenta it : v.getItems()) {

                    int idCel = it.getCelular().getId();
                    int cant = it.getCantidad();

                    ps2.setInt(1, cant);
                    ps2.setInt(2, idCel);
                    ps2.setInt(3, cant);

                    int rows = ps2.executeUpdate();
                    if (rows == 0) {
                        con.rollback();
                        con.setAutoCommit(true);
                        System.out.println("Stock insuficiente para el celular id=" + idCel);
                        return;
                    }

                    it.setId_venta(id);

                    ps.setInt(1, it.getId_venta());
                    ps.setInt(2, idCel);
                    ps.setInt(3, cant);
                    ps.setDouble(4, it.getSubtotal());
                    ps.executeUpdate();
                }
            }

            con.commit();
            con.setAutoCommit(true);
            System.out.println("ACTUALIZACION EXITOSA!");

        } catch (SQLException e) {
            System.out.println("Error actualizando venta: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {

        String sqlRestoreStock = """
        UPDATE celular c
        JOIN detalle_venta dv ON dv.id_celular = c.id
        SET c.stock = c.stock + dv.cantidad
        WHERE dv.id_venta = ?
        """;

        String sqlDeleteDetalle = """
        DELETE FROM detalle_venta
        WHERE id_venta = ?
        """;

        String sqlDeleteVenta = """
        DELETE FROM venta
        WHERE id = ?
        """;

        try (Connection con = c.conectar()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlRestoreStock)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlDeleteDetalle)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            int rows;

            try (PreparedStatement ps = con.prepareStatement(sqlDeleteVenta)) {
                ps.setInt(1, id);
                rows = ps.executeUpdate();
            }

            if (rows > 0) {
                con.commit();
                con.setAutoCommit(true);
                System.out.println("ELIMINACION EXITOSA!");
            } else {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("No existe venta con id=" + id);
            }

        } catch (SQLException e) {
            System.out.println("Error eliminando venta: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<venta> listar() {

        ArrayList<venta> ventas = new ArrayList<>();

        String sql = """
        SELECT v.id, v.fecha, v.total,
               c.id AS cliente_id, c.nombre, c.identificacion, c.correo, c.telefono
        FROM venta v
        JOIN cliente c ON v.id_cliente = c.id
        ORDER BY v.id
        """;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                cliente cl = new cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );

                ventas.add(new venta(
                        rs.getInt("id"),
                        cl,
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("total"),
                        new ArrayList<>()
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error listando ventas: " + e.getMessage());
        }

        return ventas;
    }

    @Override
    public venta buscarPorId(int id) {

        String sqlVenta = """
        SELECT v.id, v.fecha, v.total,
               c.id AS cliente_id, c.nombre, c.identificacion, c.correo, c.telefono
        FROM venta v
        JOIN cliente c ON v.id_cliente = c.id
        WHERE v.id = ?
        """;

        String sqlDetalle = """
        SELECT dv.id, dv.id_venta, dv.id_celular, dv.cantidad, dv.subtotal,
               ce.modelo, ce.precio
        FROM detalle_venta dv
        JOIN celular ce ON dv.id_celular = ce.id
        WHERE dv.id_venta = ?
        ORDER BY dv.id
        """;

        venta v = null;

        try (Connection con = c.conectar(); PreparedStatement ps = con.prepareStatement(sqlVenta)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                cliente cl = new cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );

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

            try (PreparedStatement ps2 = con.prepareStatement(sqlDetalle)) {

                ps2.setInt(1, id);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {

                    celular ce = new celular();
                    ce.setId(rs2.getInt("id_celular"));
                    ce.setModelo(rs2.getString("modelo"));
                    ce.setPrecio(rs2.getDouble("precio"));

                    detalleVenta dv = new detalleVenta();
                    dv.setId(rs2.getInt("id"));
                    dv.setId_venta(rs2.getInt("id_venta"));
                    dv.setCelular(ce);
                    dv.setCantidad(rs2.getInt("cantidad"));
                    dv.setSubtotal(rs2.getDouble("subtotal"));

                    v.getItems().add(dv);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error buscando venta: " + e.getMessage());
        }

        return v;
    }
}
