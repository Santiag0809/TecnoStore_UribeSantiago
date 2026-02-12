package controlador;

import java.util.ArrayList;
import modelo.venta;

public interface ventaControlador {

    void registrar(venta v);

    void actualizar(venta v, int id);

    ArrayList<venta> listar();

    venta buscarPorId(int id);

    void eliminar(int id);
}
