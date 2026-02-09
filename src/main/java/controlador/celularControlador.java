package controlador;

import java.util.ArrayList;
import modelo.celular;

public interface celularControlador {

    void registrar(celular ce);

    void actualizar(celular ce, int id);

    void eliminar(int id);

    ArrayList<celular> listar();

    celular buscar(int id);

}
