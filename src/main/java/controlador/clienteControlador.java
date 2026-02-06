package controlador;

import java.util.List;
import modelo.cliente;

public interface clienteControlador  {

    void registrar(cliente c);

    cliente buscarPorId(int id);

    boolean existeIdentificacion(String identificacion);

    List<cliente> listar();
}
