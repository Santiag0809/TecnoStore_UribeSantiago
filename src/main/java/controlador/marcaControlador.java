package controlador;

import java.util.ArrayList;
import modelo.marca;

public interface marcaControlador {

    void registrar(marca m);

    ArrayList<marca> listar();

    marca buscarPorId(int id);

    void actualizar(marca m);

    void eliminar(int id);
}
