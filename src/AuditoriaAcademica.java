import java.util.Locale;

/**
 * Responsable de integrar los resultados de la jerarquía y las matrículas.
 * No modifica los árboles; obtiene todas las métricas de sus métodos públicos.
 */
public final class AuditoriaAcademica {
    private AuditoriaAcademica() { }

    public static String generarReporte(ArbolGeneral jerarquia, ABB matriculas) {
        if (jerarquia == null || matriculas == null) {
            throw new IllegalArgumentException("Los dos árboles son necesarios para auditar.");
        }

        Estudiante minimo = matriculas.minimo();
        Estudiante maximo = matriculas.maximo();
        double promedio = matriculas.promedioGeneral();

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== AUDITORÍA ACADÉMICA UTA ==========\n");
        reporte.append(String.format(Locale.US,
                "Jerarquía: %d unidades | %d hojas | altura %d\n",
                jerarquia.totalNodos(), jerarquia.totalHojas(), jerarquia.altura()));
        reporte.append(String.format(Locale.US,
                "Matrículas: %d estudiantes | %d hojas | altura %d\n",
                matriculas.totalNodos(), matriculas.totalHojas(), matriculas.altura()));
        reporte.append("Matrícula mínima: ")
                .append(minimo == null ? "N/D" : minimo.getMatricula()).append('\n');
        reporte.append("Matrícula máxima: ")
                .append(maximo == null ? "N/D" : maximo.getMatricula()).append('\n');
        reporte.append("Promedio general: ")
                .append(Double.isNaN(promedio) ? "N/D" : String.format(Locale.US, "%.2f", promedio))
                .append('\n');
        reporte.append("Invariante ABB: ")
                .append(matriculas.esABBValido() ? "VÁLIDA" : "INVÁLIDA")
                .append('\n');
        return reporte.toString();
    }
}
