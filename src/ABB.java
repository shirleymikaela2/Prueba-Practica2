import java.util.ArrayList;
import java.util.List;

/** ABB manual, sin TreeMap ni TreeSet; no es autoequilibrado. */
public class ABB {
    private static class Nodo {
        Estudiante estudiante;
        Nodo izquierdo;
        Nodo derecho;
        Nodo(Estudiante estudiante) { this.estudiante = estudiante; }
    }

    public static class ResultadoBusqueda {
        private final Estudiante estudiante;
        private final int comparaciones;
        ResultadoBusqueda(Estudiante estudiante, int comparaciones) {
            this.estudiante = estudiante;
            this.comparaciones = comparaciones;
        }
        public Estudiante getEstudiante() { return estudiante; }
        public int getComparaciones() { return comparaciones; }
        public boolean encontrado() { return estudiante != null; }
    }

    private Nodo raiz;

    /** Inserción recursiva. La clave no puede repetirse. */
    public boolean insertar(int matricula, String nombre, double promedio) {
        Estudiante nuevo = new Estudiante(matricula, nombre, promedio);
        if (buscar(matricula) != null) return false;
        raiz = insertarRec(raiz, nuevo);
        return true;
    }

    private Nodo insertarRec(Nodo actual, Estudiante nuevo) {
        if (actual == null) return new Nodo(nuevo);
        if (nuevo.getMatricula() < actual.estudiante.getMatricula())
            actual.izquierdo = insertarRec(actual.izquierdo, nuevo);
        else
            actual.derecho = insertarRec(actual.derecho, nuevo);
        return actual;
    }

    public Estudiante buscar(int matricula) {
        Nodo nodo = raiz;
        while (nodo != null) {
            if (matricula == nodo.estudiante.getMatricula()) return nodo.estudiante;
            nodo = matricula < nodo.estudiante.getMatricula() ? nodo.izquierdo : nodo.derecho;
        }
        return null;
    }

    /** Una comparación por nodo visitado (no se cuenta la referencia nula). */
    public ResultadoBusqueda buscarConComparaciones(int matricula) {
        int comparaciones = 0;
        Nodo nodo = raiz;
        while (nodo != null) {
            comparaciones++;
            int clave = nodo.estudiante.getMatricula();
            if (matricula == clave) return new ResultadoBusqueda(nodo.estudiante, comparaciones);
            nodo = matricula < clave ? nodo.izquierdo : nodo.derecho;
        }
        return new ResultadoBusqueda(null, comparaciones);
    }

    public boolean modificar(int matricula, String nuevoNombre, double nuevoPromedio) {
        Estudiante estudiante = buscar(matricula);
        if (estudiante == null) return false;
        estudiante.actualizar(nuevoNombre, nuevoPromedio);
        return true;
    }

    /** Elimina hoja, nodo con un hijo y nodo con dos hijos (sucesor inorden). */
    public boolean eliminar(int matricula) {
        if (buscar(matricula) == null) return false;
        raiz = eliminarRec(raiz, matricula);
        return true;
    }

    private Nodo eliminarRec(Nodo actual, int matricula) {
        if (actual == null) return null;
        if (matricula < actual.estudiante.getMatricula())
            actual.izquierdo = eliminarRec(actual.izquierdo, matricula);
        else if (matricula > actual.estudiante.getMatricula())
            actual.derecho = eliminarRec(actual.derecho, matricula);
        else {
            if (actual.izquierdo == null) return actual.derecho;
            if (actual.derecho == null) return actual.izquierdo;
            Nodo sucesor = minimoNodo(actual.derecho);
            actual.estudiante = sucesor.estudiante;
            actual.derecho = eliminarRec(actual.derecho, sucesor.estudiante.getMatricula());
        }
        return actual;
    }

    private Nodo minimoNodo(Nodo nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo;
    }

    private Nodo maximoNodo(Nodo nodo) {
        while (nodo.derecho != null) nodo = nodo.derecho;
        return nodo;
    }

    public Estudiante minimo() { return raiz == null ? null : minimoNodo(raiz).estudiante; }
    public Estudiante maximo() { return raiz == null ? null : maximoNodo(raiz).estudiante; }

    public List<Integer> inorden() {
        ArrayList<Integer> resultado = new ArrayList<>();
        inordenRec(raiz, resultado);
        return resultado;
    }
    private void inordenRec(Nodo actual, List<Integer> resultado) {
        if (actual == null) return;
        inordenRec(actual.izquierdo, resultado);
        resultado.add(actual.estudiante.getMatricula());
        inordenRec(actual.derecho, resultado);
    }

    public List<Integer> preorden() {
        ArrayList<Integer> resultado = new ArrayList<>();
        preordenRec(raiz, resultado);
        return resultado;
    }
    private void preordenRec(Nodo actual, List<Integer> resultado) {
        if (actual == null) return;
        resultado.add(actual.estudiante.getMatricula());
        preordenRec(actual.izquierdo, resultado);
        preordenRec(actual.derecho, resultado);
    }

    public List<Integer> posorden() {
        ArrayList<Integer> resultado = new ArrayList<>();
        posordenRec(raiz, resultado);
        return resultado;
    }
    private void posordenRec(Nodo actual, List<Integer> resultado) {
        if (actual == null) return;
        posordenRec(actual.izquierdo, resultado);
        posordenRec(actual.derecho, resultado);
        resultado.add(actual.estudiante.getMatricula());
    }

    public int altura() { return alturaRec(raiz); }
    private int alturaRec(Nodo actual) {
        if (actual == null) return -1;
        return 1 + Math.max(alturaRec(actual.izquierdo), alturaRec(actual.derecho));
    }

    public int totalNodos() { return contarRec(raiz); }
    private int contarRec(Nodo actual) {
        if (actual == null) return 0;
        return 1 + contarRec(actual.izquierdo) + contarRec(actual.derecho);
    }

    public int totalHojas() { return hojasRec(raiz); }
    private int hojasRec(Nodo actual) {
        if (actual == null) return 0;
        if (actual.izquierdo == null && actual.derecho == null) return 1;
        return hojasRec(actual.izquierdo) + hojasRec(actual.derecho);
    }

    /** Media aritmética de los promedios registrados (cada alumno pesa 1). */
    public double promedioGeneral() {
        if (raiz == null) return Double.NaN;
        return sumaPromediosRec(raiz) / totalNodos();
    }
    private double sumaPromediosRec(Nodo actual) {
        if (actual == null) return 0;
        return actual.estudiante.getPromedio()
                + sumaPromediosRec(actual.izquierdo)
                + sumaPromediosRec(actual.derecho);
    }

    public boolean esABBValido() { return validarRec(raiz, Long.MIN_VALUE, Long.MAX_VALUE); }
    private boolean validarRec(Nodo actual, long minimoExclusivo, long maximoExclusivo) {
        if (actual == null) return true;
        int clave = actual.estudiante.getMatricula();
        return clave > minimoExclusivo && clave < maximoExclusivo
                && validarRec(actual.izquierdo, minimoExclusivo, clave)
                && validarRec(actual.derecho, clave, maximoExclusivo);
    }
}
