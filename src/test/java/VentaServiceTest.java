import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaServiceTest {

    private VentaService ventaService;

    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String DB_USER = "";
    private final String DB_PASS = "";

    @BeforeEach
    void setUp() {
        ventaService = new VentaService();
        limpiarTablas();
    }

    @Test
    void testVentaExitosaGeneraLogExito() throws SQLException {
        String usuario = "test_user";
        double monto = 500.00;

        ventaService.procesarVenta(usuario, monto);

        String ultimoResultado = obtenerUltimoLogResultado(usuario);
        Assertions.assertEquals("EXITO", ultimoResultado);

        int totalVentas = contarVentas(usuario);
        Assertions.assertEquals(1, totalVentas);
    }

    @Test
    void testMontoNegativoGeneraLogError() throws SQLException {
        String usuario = "hacker_user";
        double monto = -100.00;

        ventaService.procesarVenta(usuario, monto);

        String ultimoResultado = obtenerUltimoLogResultado(usuario);
        Assertions.assertEquals("ERROR", ultimoResultado);

        int totalVentas = contarVentas(usuario);
        Assertions.assertEquals(0, totalVentas);
    }

    private String obtenerUltimoLogResultado(String usuario) throws SQLException {
        String sql = "SELECT resultado FROM logs_auditoria WHERE usuario = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("resultado") : null;
        }
    }

    private int contarVentas(String usuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ventas WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private void limpiarTablas() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM ventas");
            stmt.executeUpdate("DELETE FROM logs_auditoria");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}