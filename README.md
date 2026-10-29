# Sistema de Auditoría Jerárquica y Académica UTA

**Universidad Técnica de Ambato — FISEI**  
**Carrera:** Ingeniería de Software  
**Asignatura:** Estructura de Datos  
**Lenguaje:** Java 17 o superior  
**Herramientas:** Visual Studio Code y GitHub

## 1. Descripción

Aplicación de consola en Java para gestionar la **jerarquía académica de la UTA** mediante un árbol general y los **registros de estudiantes** mediante un árbol binario de búsqueda (ABB). Integra ambas estructuras en un menú y genera un reporte de auditoría con información de sus nodos y operaciones.

Repositorio: https://github.com/shirleymikaela2/Prueba-Practica2

## 2. Objetivo

Implementar y comprobar operaciones recursivas de árboles, validaciones de datos, recorridos, eliminación de nodos, cálculos de métricas y análisis del costo de búsqueda.

## 3. Funcionalidades

- **Árbol general:** creación de la jerarquía, inserción, búsqueda, nivel, grado, altura, cantidad de nodos y hojas, eliminación segura y conversión hijo izquierdo/hermano derecho.
- **ABB:** inserción y búsqueda de matrículas, recorridos preorden, inorden y posorden, modificación de nombre y promedio, eliminación de nodos con cero, uno o dos hijos, mínimo, máximo y validación del orden.
- **Auditoría:** total de unidades, hojas, altura, matrícula mínima y máxima, promedio de estudiantes y validez del ABB.
- **Análisis:** comparación de búsquedas en dos ABB con igual número de nodos y diferentes alturas.
- **Pruebas:** casos de aceptación y fallos, ejecutados mediante aserciones.

## 4. Organización del código

Todas las clases Java se ubican en una sola carpeta **`src/`**, sin paquetes adicionales.

| Archivo | Función |
| --- | --- |
| `Main.java` | Menú principal y conexión de módulos |
| `DatosEjemplo.java` | Carga de la jerarquía y las matrículas iniciales |
| `AuditoriaAcademica.java` | Generación del reporte de auditoría |
| `NodoUnidad.java` | Representación de las unidades académicas |
| `ArbolGeneral.java` | Operaciones y métricas del árbol general |
| `NodoBinarioUnidad.java` | Representación hijo izquierdo/hermano derecho |
| `Estudiante.java` | Datos asociados a una matrícula |
| `ABB.java` | Operaciones y recorridos del ABB |
| `Pruebas.java` | Pruebas de aceptación reproducibles |

**Estado:** algunas clases todavía deben incorporarse desde las ramas de los integrantes. El funcionamiento completo se verificará cuando estén integradas.

## 5. Datos iniciales de la práctica

La jerarquía contiene nueve unidades: **UTA** como raíz; sus hijos **FISEI, FCHE y FCS**; dentro de FISEI están **SOFTWARE y TI**; SOFTWARE tiene **3A y 3B** y TI tiene **4A**. El resultado esperado es **9 unidades, 5 hojas y altura 3** (medida en aristas).

Las matrículas se insertan en el orden: `50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85`.

Se debe rechazar la matrícula duplicada **40**, permitir modificar nombre/promedio sin cambiar la clave y comprobar las eliminaciones **20** (hoja), **60** (un hijo) y **70** (dos hijos, usando sucesor inorden).

**Inorden esperado tras eliminar:** `30, 35, 40, 45, 50, 65, 75, 80, 85`.

## 6. Ejecución

1. Instalar **JDK 17 o superior** y **Visual Studio Code** con **Extension Pack for Java**.
2. Abrir en VS Code la carpeta que contiene `src/` y `README.md`.
3. Cuando todas las clases estén integradas, abrir `src/Main.java` y ejecutar **Run Java**.
4. Para las pruebas, abrir `src/Pruebas.java` y ejecutar **Run Java**.

Alternativamente, desde la terminal, en la raíz del repositorio:

```powershell
mkdir out
javac -encoding UTF-8 -d out -sourcepath src src/Main.java src/Pruebas.java
java -cp out Main
java -cp out Pruebas
```

> Si `out` ya existe, omitir `mkdir out`. Si aún faltan clases por fusionar, pueden aparecer errores de compilación hasta completar la integración.

## 7. Integrantes y responsabilidades

| Integrante | Rama | Responsabilidad | Evidencia GitHub |
| --- | --- | --- | --- |
| Cris | `feature/cris-general` | Nodo y operaciones básicas del árbol general | Pendiente |
| Andrés | `feature/andres-metricas-conversion` | Métricas de jerarquía y conversión binaria | Pendiente |
| Lenin | `feature/lenin-abb-basico` | Inserción, búsqueda y recorridos ABB | Pendiente |
| Josué | `feature/josue-abb-operaciones` | Modificación, eliminación y métricas ABB | Pendiente |
| Héctor | `feature/hector-pruebas` | Pruebas automatizadas y análisis de búsquedas | Pendiente |
| Shirley | `feature/shirley-integracion` | Menú, datos iniciales, auditoría y documentación | [PR #2](https://github.com/shirleymikaela2/Prueba-Practica2/pull/2) |

Cada integrante debe registrar cambios reales en su rama. Antes de fusionar futuras pull requests, otro integrante debe revisar el trabajo. La PR #2 fue fusionada; en su captura no aparecía un revisor asignado.

## 8. Evidencias y resultados

**Por completar cuando el proyecto esté integrado y se ejecuten las pruebas en el repositorio del grupo:**

- [ ] Captura de la jerarquía con 9 unidades, 5 hojas y altura 3.
- [ ] Captura del inorden original y rechazo de matrícula duplicada 40.
- [ ] Captura de eliminación de 20, 60 y 70 e inorden final.
- [ ] Captura de la representación hijo izquierdo/hermano derecho.
- [ ] Captura del reporte de auditoría.
- [ ] Captura del análisis comparativo de búsquedas.
- [ ] Resultado real de al menos 12 pruebas reproducibles.
- [ ] Enlaces a ramas, commits y pull requests revisadas.
- [ ] Hash del commit final de entrega.

Los archivos y capturas se guardarán en la carpeta `evidencias/` cuando estén disponibles.

## 9. Consideraciones

No se utilizan `TreeMap`, `TreeSet` ni librerías que implementen los árboles automáticamente. El ABB ordinario **no garantiza autoequilibrio**: su costo de búsqueda depende de la altura `h` y es `O(h)`. En el peor caso puede ser `O(n)`; en un árbol de altura logarítmica, `O(log n)`.

**Nota organizativa:** el documento de evaluación menciona equipos de 3 a 5 estudiantes; debe confirmarse con el docente la participación de seis integrantes.
