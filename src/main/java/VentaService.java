import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VentaService {

    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String DB_USER = "";
    private final String DB_PASS = "";

    private final VentaDAO ventaDAO = new VentaDAO();
    private final LogDAO logDAO = new LogDAO();

    public void procesarVenta(String usuario, double monto) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            conn.setAutoCommit(false);

            if (monto <= 0) {
                throw new IllegalArgumentException("El monto debe ser positivo.");
            }

            ventaDAO.insertarVenta(conn, usuario, monto);
            logDAO.registrarLog(conn, usuario, "REGISTRO_VENTA", "EXITO", "Monto: " + monto);

            conn.commit();
            System.out.println("Venta procesada correctamente.");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    logDAO.registrarLog(conn, usuario, "REGISTRO_VENTA", "ERROR", e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Error en transacción: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}