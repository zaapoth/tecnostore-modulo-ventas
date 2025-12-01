import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.File;

public class ReporteTest {

    @Test
    void testGenerarTodosLosReportes() {
        ReporteSeguridadService servicio = new ReporteSeguridadService();

        String archivoVentas = "1_Reporte_Ventas.pdf";
        String archivoInventario = "2_Reporte_Inventario.pdf";
        String archivoKPIs = "3_Reporte_KPIs.pdf";

        // simulamos que somos ADMIN para que nos deje pasar
        servicio.generarReporteVentas(archivoVentas, "ADMIN");
        servicio.generarReporteInventario(archivoInventario, "ADMIN");
        servicio.generarReporteKPIs(archivoKPIs, "ADMIN");

        Assertions.assertTrue(new File(archivoVentas).exists(), "falta reporte ventas");
        Assertions.assertTrue(new File(archivoInventario).exists(), "falta reporte inventario");
        Assertions.assertTrue(new File(archivoKPIs).exists(), "falta reporte kpis");
    }

    @Test
    void testAccesoDenegadoUsuarioComun() {
        ReporteSeguridadService servicio = new ReporteSeguridadService();
        String archivo = "Reporte_Ilegal.pdf";

        // simulamos que somos VENDEDOR y el sistema debe bloquearnos
        Exception exception = Assertions.assertThrows(SecurityException.class, () -> {
            servicio.generarReporteVentas(archivo, "VENDEDOR");
        });

        System.out.println("seguridad ok: " + exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("ACCESO DENEGADO"));
    }

    @Test
    void testSeguridadRFC() {
        ProveedorService servicio = new ProveedorService();
        String rfcHacker = "RFC' OR 1=1 --";

        Exception exception = Assertions.assertThrows(SecurityException.class, () -> {
            servicio.registrarProveedor("Empresa Fake", rfcHacker, "test@mail.com");
        });
        Assertions.assertTrue(exception.getMessage().contains("caracteres invalidos"));
    }
}