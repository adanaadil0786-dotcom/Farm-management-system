import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DatabaseManager {

    private static final String URL     = "jdbc:mysql://localhost:3306/smart_farm";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Adanaadil414";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASS);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  FARMERS
    // ══════════════════════════════════════════════════════════════════════
    public static boolean saveFarmer(Farmers f) {
        String sql = "INSERT INTO farmers (farmer_id, farmer_name, phone, address) VALUES (?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, f.getFarmerId());
            ps.setString(2, f.getFarmerName());
            ps.setString(3, f.getPhone());
            ps.setString(4, f.getAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (farmer): " + e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    //  ANIMAL STOCK
    // ══════════════════════════════════════════════════════════════════════
    public static boolean saveAnimalStock(AnimalStock a) {
        String sql = "INSERT INTO animal_stock " +
                "(animal_id,animal_name,age,health_status,weight," +
                "stock_count,min_stock_level,purchase_price,sale_price," +
                "feed_type,daily_feed_kg) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1,  a.getAnimalId());
            ps.setString(2,  a.getAnimalname());
            ps.setInt   (3,  a.getAge());
            ps.setString(4,  a.getHealth_status());
            ps.setDouble(5, Double.parseDouble(a.getWeight()));
            ps.setInt   (6,  a.getStockCount());
            ps.setInt   (7,  a.getMinStockLevel());
            ps.setDouble(8,  a.getPurchasePrice());
            ps.setDouble(9,  a.getSalePrice());
            ps.setString(10, a.getFeedType());
            ps.setDouble(11, a.getDailyFeedKg());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (animal): " + e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    //  CROPS  (cropId, cropName, season, price, quantity, plantDate, harvestDate)
    // ══════════════════════════════════════════════════════════════════════

    /** INSERT a new crop row — calls c.addCrop() to log the action too. */
    public static boolean saveCrop(Crops c) {
        String sql = "INSERT INTO crops " +
                "(crop_id, crop_name, season, price, quantity, plant_date, harvest_date) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, c.getCropId());
            ps.setString(2, c.getCropName());
            ps.setString(3, c.getSeason());
            ps.setInt   (4, c.getPrice());
            ps.setInt   (5, c.getQuantity());
            ps.setString(6, c.getPlantDate());
            ps.setString(7, c.getHarvestDate());
            ps.executeUpdate();
            c.addCrop();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (save crop): " + e.getMessage());
            return false;
        }
    }

    /** UPDATE price, quantity, harvest_date of an existing crop by crop_id. */
    public static boolean updateCrop(Crops c) {
        String sql = "UPDATE crops SET price = ?, quantity = ?, harvest_date = ? WHERE crop_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, c.getPrice());
            ps.setInt   (2, c.getQuantity());
            ps.setString(3, c.getHarvestDate());
            ps.setInt   (4, c.getCropId());
            ps.executeUpdate();
            c.updateCrop();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (update crop): " + e.getMessage());
            return false;
        }
    }

    /** DELETE a crop row by crop_id. */
    public static boolean deleteCrop(Crops c) {
        String sql = "DELETE FROM crops WHERE crop_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getCropId());
            ps.executeUpdate();
            c.deleteCrop();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (delete crop): " + e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    //  INVENTORY  (uses Inventory class + ItemCategory enum)
    // ══════════════════════════════════════════════════════════════════════

    /** INSERT a new inventory item. ItemCategory enum stored via .name(). */
    public static boolean saveInventoryItem(Inventory item) {
        String sql = "INSERT INTO inventory " +
                "(inventory_id, item_name, category, quantity, price, min_quantity) " +
                "VALUES (?,?,?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, item.getInventoryID());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getCategory().name());   // ItemCategory -> String
            ps.setInt   (4, item.getQuantity());
            ps.setDouble(5, item.getPrice());
            ps.setInt   (6, item.getMinQuantity());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (save inventory): " + e.getMessage());
            return false;
        }
    }

    /** UPDATE quantity, price, category of an inventory item by inventory_id. */
    public static boolean updateInventoryItem(Inventory item) {
        String sql = "UPDATE inventory SET quantity = ?, price = ?, category = ? WHERE inventory_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, item.getQuantity());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getCategory().name());
            ps.setInt   (4, item.getInventoryID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (update inventory): " + e.getMessage());
            return false;
        }
    }

    /** DELETE an inventory item by inventory_id. */
    public static boolean deleteInventoryItem(int inventoryId) {
        String sql = "DELETE FROM inventory WHERE inventory_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, inventoryId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("DB error (delete inventory): " + e.getMessage());
            return false;
        }
    }

    /** Converts a String from the DB back into an ItemCategory enum (safe default EQUIPMENT). */
    public static ItemCategory parseCategory(String value) {
        try {
            return ItemCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ItemCategory.EQUIPMENT;
        }
    }
}
