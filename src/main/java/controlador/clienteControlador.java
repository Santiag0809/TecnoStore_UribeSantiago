package controlador;

import java.util.List;
import modelo.cliente;

public interface clienteControlador  {

    void registrar(cliente c);
    
    void actualizar(cliente c, int id);
 
    List<cliente> listar();
    
    void eliminar(int id);

    cliente buscarPorId(int id);

    boolean existeIdentificacion(String identificacion);

    
}
