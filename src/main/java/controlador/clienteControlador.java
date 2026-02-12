package controlador;

import java.util.ArrayList;
import modelo.cliente;

public interface clienteControlador {

    void registrar(cliente c);

    void actualizar(cliente c, int id);

    ArrayList<cliente> listar();

    void eliminar(int id);

    cliente buscarPorId(int id);

    boolean existeIdentificacion(String identificacion);

    boolean tieneVentas(int idCliente);

}
