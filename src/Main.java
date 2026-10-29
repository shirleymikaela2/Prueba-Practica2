import java.util.Locale;
import java.util.Scanner;

/** Menú integrador para la prueba práctica UTA. */
public class Main {
    private static final Scanner ENTRADA = new Scanner(System.in);
    private static ArbolGeneral general = DatosEjemplo.crearGeneral();
    private static ABB abb = DatosEjemplo.crearABB();

    public static void main(String[] args) {
        System.out.println("SISTEMA DE AUDITORÍA JERÁRQUICA Y ACADÉMICA UTA");
        System.out.println("Se cargaron los datos obligatorios. Los nombres/notas son ejemplos.");
        while (true) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Ver jerarquía académica");
            System.out.println("2. Administrar árbol general");
            System.out.println("3. Ver ABB y sus recorridos");
            System.out.println("4. Administrar matrículas (ABB)");
            System.out.println("5. Auditoría general");
            System.out.println("6. Transformar general a hijo izquierdo/hermano derecho");
            System.out.println("7. Comparar búsquedas en dos ABB de igual tamaño");
            System.out.println("8. Ejecutar caso de aceptación de eliminaciones (reinicia datos)");
            System.out.println("9. Restablecer datos iniciales");
            System.out.println("0. Salir");
            int opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> System.out.println(general.imprimirJerarquia());
                case 2 -> menuGeneral();
                case 3 -> mostrarABB();
                case 4 -> menuABB();
                case 5 -> auditar();
                case 6 -> System.out.println(general.imprimirBinario());
                case 7 -> compararBusquedas();
                case 8 -> demoEliminaciones();
                case 9 -> { reiniciar(); System.out.println("Datos restablecidos."); }
                case 0 -> { System.out.println("Programa finalizado."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void reiniciar() {
        general = DatosEjemplo.crearGeneral();
        abb = DatosEjemplo.crearABB();
    }

    private static int leerEntero(String texto) {
        while (true) {
            System.out.print(texto);
            String linea = ENTRADA.nextLine().trim();
            try { return Integer.parseInt(linea); }
            catch (NumberFormatException ex) { System.out.println("Ingrese un número entero válido."); }
        }
    }

    private static double leerDouble(String texto) {
        while (true) {
            System.out.print(texto);
            String linea = ENTRADA.nextLine().trim().replace(',', '.');
            try {
                double resultado = Double.parseDouble(linea);
                if (Double.isFinite(resultado)) return resultado;
            } catch (NumberFormatException ignored) { }
            System.out.println("Ingrese un número decimal válido.");
        }
    }

    private static String leerTexto(String texto) {
        System.out.print(texto);
        return ENTRADA.nextLine().trim();
    }

    private static void menuGeneral() {
        while (true) {
            System.out.println("\n--- ÁRBOL GENERAL ---");
            System.out.println("1. Agregar unidad | 2. Buscar y mostrar nivel/grado");
            System.out.println("3. Eliminar unidad | 4. Métricas y hojas | 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1 -> {
                    String padre = leerTexto("Código del padre: ");
                    String nuevo = leerTexto("Nuevo código: ");
                    boolean exito = general.agregar(padre, nuevo);
                    System.out.println(exito ? "Unidad agregada." : "Rechazada: revise padre existente/código único.");
                }
                case 2 -> {
                    String codigo = leerTexto("Código: ");
                    NodoUnidad nodo = general.buscar(codigo);
                    if (nodo == null) System.out.println("Unidad inexistente.");
                    else System.out.printf("Código: %s | Nivel: %d | Grado: %d | Hoja: %s%n",
                            nodo.getCodigo(), general.nivel(codigo), general.grado(codigo), nodo.esHoja());
                }
                case 3 -> {
                    String codigo = leerTexto("Código a eliminar: ");
                    System.out.println(general.eliminar(codigo) ? "Unidad eliminada." :
                            "Rechazada: no existe, es raíz o tiene descendientes.");
                }
                case 4 -> System.out.printf("Nodos: %d | Hojas: %d | Altura: %d | Hojas: %s%n",
                        general.totalNodos(), general.totalHojas(), general.altura(), general.listarHojas());
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void mostrarABB() {
        System.out.println("Preorden:  " + abb.preorden());
        System.out.println("Inorden:   " + abb.inorden());
        System.out.println("Posorden: " + abb.posorden());
        System.out.printf("Nodos: %d | Hojas: %d | Altura: %d%n",
                abb.totalNodos(), abb.totalHojas(), abb.altura());
    }

    private static void menuABB() {
        while (true) {
            System.out.println("\n--- MATRÍCULAS (ABB) ---");
            System.out.println("1. Insertar | 2. Buscar | 3. Modificar nombre/promedio");
            System.out.println("4. Eliminar | 5. Recorridos y métricas | 0. Volver");
            switch (leerEntero("Opción: ")) {
                case 1 -> {
                    int clave = leerEntero("Matrícula: ");
                    String nombre = leerTexto("Nombre: ");
                    double promedio = leerDouble("Promedio (0 a 10): ");
                    try {
                        System.out.println(abb.insertar(clave, nombre, promedio) ?
                                "Estudiante agregado." : "Rechazada: matrícula duplicada.");
                    } catch (IllegalArgumentException e) { System.out.println("Rechazada: " + e.getMessage()); }
                }
                case 2 -> {
                    int clave = leerEntero("Matrícula a buscar: ");
                    ABB.ResultadoBusqueda r = abb.buscarConComparaciones(clave);
                    System.out.println(r.encontrado() ? r.getEstudiante() : "Matrícula inexistente.");
                    System.out.println("Nodos comparados: " + r.getComparaciones());
                }
                case 3 -> {
                    int clave = leerEntero("Matrícula (no se puede cambiar): ");
                    String nombre = leerTexto("Nuevo nombre: ");
                    double promedio = leerDouble("Nuevo promedio: ");
                    try {
                        System.out.println(abb.modificar(clave, nombre, promedio) ? "Registro modificado." :
                                "Matrícula inexistente.");
                    } catch (IllegalArgumentException e) { System.out.println("Rechazada: " + e.getMessage()); }
                }
                case 4 -> {
                    int clave = leerEntero("Matrícula a eliminar: ");
                    System.out.println(abb.eliminar(clave) ? "Registro eliminado." : "Matrícula inexistente.");
                }
                case 5 -> mostrarABB();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void auditar() {
        System.out.println("\n========== AUDITORÍA ==========");
        System.out.printf("Jerarquía: %d unidades | %d hojas | altura %d%n",
                general.totalNodos(), general.totalHojas(), general.altura());
        System.out.printf("Matrículas: %d estudiantes | %d hojas | altura %d%n",
                abb.totalNodos(), abb.totalHojas(), abb.altura());
        System.out.println("Mínima: " + (abb.minimo() == null ? "N/D" : abb.minimo().getMatricula()));
        System.out.println("Máxima: " + (abb.maximo() == null ? "N/D" : abb.maximo().getMatricula()));
        System.out.println("Promedio de estudiantes: " + (Double.isNaN(abb.promedioGeneral()) ?
                "N/D" : String.format(Locale.US, "%.2f", abb.promedioGeneral())));
        System.out.println("Invariante ABB: " + (abb.esABBValido() ? "VÁLIDA" : "INVÁLIDA"));
    }

    private static void compararBusquedas() {
        ABB cadena = new ABB();
        ABB masEquilibrado = new ABB();
        for (int i = 1; i <= 15; i++) cadena.insertar(i, "Estudiante " + i, 8);
        int[] orden = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        for (int i : orden) masEquilibrado.insertar(i, "Estudiante " + i, 8);
        System.out.println("Dos ABB de 15 nodos, sin autoequilibrio.");
        System.out.printf("Altura: cadena %d | inserción más equilibrada %d%n",
                cadena.altura(), masEquilibrado.altura());
        for (int buscado : new int[] {15, 16}) {
            ABB.ResultadoBusqueda uno = cadena.buscarConComparaciones(buscado);
            ABB.ResultadoBusqueda dos = masEquilibrado.buscarConComparaciones(buscado);
            System.out.printf("Buscar %d (%s): cadena %d | más equilibrado %d comparaciones%n",
                    buscado, uno.encontrado() ? "existente" : "inexistente",
                    uno.getComparaciones(), dos.getComparaciones());
        }
        System.out.println("Interpretación: búsqueda O(h); cadena O(n), árbol aproximadamente equilibrado O(log n).");
    }

    private static void demoEliminaciones() {
        reiniciar();
        System.out.println("Jerarquía inicial: " + general.totalNodos() + " unidades, "
                + general.totalHojas() + " hojas, altura " + general.altura());
        System.out.println("ABB antes: " + abb.inorden());
        System.out.println("Insertar duplicada 40: " + abb.insertar(40, "Duplicado", 9));
        System.out.println("Modificar 35 (misma clave): " + abb.modificar(35, "Nombre editado", 9.5));
        System.out.println("Eliminar 20 (hoja): " + abb.eliminar(20));
        System.out.println("Eliminar 60 (un hijo): " + abb.eliminar(60));
        System.out.println("Eliminar 70 (dos hijos): " + abb.eliminar(70));
        System.out.println("ABB después: " + abb.inorden());
        System.out.println("Nodos restantes: " + abb.totalNodos());
        System.out.println("Intentar eliminar FISEI con descendencia: " + general.eliminar("FISEI"));
        auditar();
    }
}
