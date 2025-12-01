========================================================================
PROYECTO: TECNOSTORE ERP - MÓDULO DE VENTAS, PROVEEDORES Y REPORTES
ASIGNATURA: ASPECTOS DE SEGURIDAD PARA EL DESARROLLO DE SOFTWARE
FECHA: 30/11/2025
========================================================================

1. DESCRIPCIÓN DEL PROYECTO
---------------------------
Este entregable contiene la implementación de la Sección 8 del sistema ERP.
Incluye los módulos de gestión de proveedores y el motor de generación de 
reportes seguros en PDF con integración de gráficas (JFreeChart).

2. CARACTERÍSTICAS DE SEGURIDAD IMPLEMENTADAS
---------------------------------------------
- Sanitización de Entradas: Validación estricta de RFC y Email con Regex.
- Prevención de SQL Injection: Uso total de PreparedStatement.
- RBAC (Role-Based Access Control): Simulación de validación de permisos 
  antes de generar reportes (ADMIN vs VENDEDOR).
- Data Masking: Enmascaramiento de nombres de usuario en reportes impresos.
- Auditoría Automática: Detección y alertamiento de montos atípicos (> $10k).

3. REQUISITOS DEL SISTEMA
-------------------------
- Java JDK 23
- Maven 3.x
- MySQL 8.x
- Conexión a Internet (para descargar dependencias de Maven)

4. CONFIGURACIÓN DE BASE DE DATOS
---------------------------------
El sistema requiere la base de datos 'seguridad_ventas'.
Credenciales configuradas en ReporteSeguridadService.java:
- User: root
- Pass: 4422 (Ajustar según entorno local)

5. INSTRUCCIONES DE EJECUCIÓN
-----------------------------
A. Para ejecutar las Pruebas Unitarias y de Seguridad:
   1. Abrir el proyecto en IntelliJ IDEA.
   2. Ir a src/test/java/ReporteTest.java.
   3. Ejecutar la clase completa (Clic derecho -> Run 'ReporteTest').

B. Para generar los Reportes (PDF):
   1. Al ejecutar el Test exitosamente, los archivos PDF se generarán 
      automáticamente en la carpeta raíz del proyecto:
      - 1_Reporte_Ventas.pdf
      - 2_Reporte_Inventario.pdf
      - 3_Reporte_KPIs.pdf

6. ESTRUCTURA DE ARCHIVOS
-------------------------
/src/main/java
  - ProveedorService.java ..... Lógica de negocio y validación Regex.
  - ReporteSeguridadService.java ... Motor de PDFs y Gráficas.
  - VentaService.java ......... (Módulo anterior integrado).

/src/test/java
  - ReporteTest.java .......... Pruebas de integración y seguridad.

========================================================================