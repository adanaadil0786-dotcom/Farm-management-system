import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private int           inventoryID;
    private String        itemName;
    private ItemCategory  category;
    private int           quantity;
    private double        price;
    private int           minQuantity;   // threshold for low-stock alert

    // Shared list – holds all items (only one Inventory instance manages it)
    private final List<Inventory> itemList = new ArrayList<>();

    public Inventory(int inventoryID, String itemName, ItemCategory category,
                     int quantity, double price, int minQuantity) {
        this.inventoryID  = inventoryID;
        this.itemName     = itemName;
        this.category     = category;
        this.quantity     = quantity;
        this.price        = price;
        this.minQuantity  = minQuantity;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public void addItem(Inventory newItem) {
        itemList.add(newItem);
        System.out.println("Success: " + newItem.getItemName() + " added to inventory.");
    }

    public void updateItem(int id, int newQuantity, double newPrice) {
        for (Inventory item : itemList) {
            if (item.getInventoryID() == id) {
                item.setQuantity(newQuantity);
                item.setPrice(newPrice);
                System.out.println("Success: Item ID " + id + " updated.");
                return;
            }
        }
        System.out.println("Error: Item ID " + id + " not found.");
    }

    public void deleteItem(int id) {
        itemList.removeIf(item -> {
            if (item.getInventoryID() == id) {
                System.out.println("Item ID " + id + " deleted.");
                return true;
            }
            return false;
        });
    }

    public Inventory searchItem(int id) {
        for (Inventory item : itemList) {
            if (item.getInventoryID() == id) return item;
        }
        return null;
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Inventory item : itemList) {
            System.out.println(
                    "ID: "       + item.getInventoryID()  +
                            " | Name: "  + item.getItemName()      +
                            " | Cat: "   + item.getCategory()      +
                            " | Qty: "   + item.getQuantity()      +
                            " | Price: $"+ item.getPrice()         +
                            (item.isLowStock() ? "  ⚠ LOW" : "")
            );
        }
    }

    public boolean isLowStock() {
        return quantity <= minQuantity;
    }

    public List<Inventory> getLowStockItems() {
        List<Inventory> low = new ArrayList<>();
        for (Inventory item : itemList) {
            if (item.isLowStock()) low.add(item);
        }
        return low;
    }

    public List<Inventory> getItemList() { return itemList; }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int          getInventoryID()            { return inventoryID; }
    public void         setInventoryID(int id)      { this.inventoryID = id; }

    public String       getItemName()               { return itemName; }
    public void         setItemName(String n)       { this.itemName = n; }

    public ItemCategory getCategory()               { return category; }
    public void         setCategory(ItemCategory c) { this.category = c; }

    public int          getQuantity()               { return quantity; }
    public void         setQuantity(int q) {
        if (q >= 0) this.quantity = q;
        else System.out.println("Error: quantity cannot be negative.");
    }

    public double       getPrice()                  { return price; }
    public void         setPrice(double p)          { this.price = p; }

    public int          getMinQuantity()            { return minQuantity; }
    public void         setMinQuantity(int m)       { this.minQuantity = m; }
}