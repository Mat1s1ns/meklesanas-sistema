import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    System.out.println("Before server");

    private static final String URL = "jdbc:sqlite:products.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Izveido tabulu
    public static void createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS products (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "price REAL, " +
            "category TEXT" +
            ");";

    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {

        stmt.execute(sql);
        System.out.println("Table created successfully");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Ievieto test datus
    public static void insertData() {
    String[] brands = {"Apple", "Samsung", "Dell", "HP", "Sony", "Asus"};
    String[] types = {"Phone", "Laptop", "TV", "Headphones", "Watch", "Mouse"};
    String[] extras = {"Pro", "Max", "Ultra", "Plus", "Mini"};

    String sql = "INSERT INTO products(name) VALUES(?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < 10000; i++) {
            String name = brands[i % brands.length] + " " +
                          types[i % types.length] + " " +
                          extras[i % extras.length] + " " + i;

            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static List<String> getAllProducts() {
    List<String> products = new ArrayList<>();

    String sql = "SELECT name FROM products";

    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            products.add(rs.getString("name"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return products;
}
}