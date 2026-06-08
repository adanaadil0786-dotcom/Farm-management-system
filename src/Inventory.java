import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private int inventoryID;
    private String itemName;
    private String category;
    private int quantity;
    private int price;

    private List<Inventory> itemList = new ArrayList<>();

    public Inventory(int inventoryID, String itemName, String category, int quantity, int price) {
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public int getInventoryID() {
        return inventoryID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            System.out.println("Error: Quantity cannot be negative.");
        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Add Inventory object
    public void addItem(Inventory newItem) {
        itemList.add(newItem);
        System.out.println("Success: " + newItem.getItemName() + " added to inventory.");
    }

    public void updateItem(int id, int newQuantity, double newPrice) {
        for (Inventory item : itemList) {
            if (item.getInventoryID() == id) {
                item.setQuantity(newQuantity);
                item.setPrice((int) newPrice);
                System.out.println("Success: Item ID " + id + " has been updated.");
                return;
            }
        }
        System.out.println("Error: Item with ID " + id + " not found.");
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");

        for (Inventory item : itemList) {
            System.out.println(
                    "ID: " + item.getInventoryID() +
                            " | Name: " + item.getItemName() +
                            " | Category: " + item.getCategory() +
                            " | Qty: " + item.getQuantity() +
                            " | Price: $" + item.getPrice()
            );
        }}

        public void searchItem(int id) {
            for (Inventory item : itemList) {
                if (item.getInventoryID() == id) {
                    System.out.println("Item Found:");
                    System.out.println("ID: " + item.getInventoryID());
                    System.out.println("Name: " + item.getItemName());
                    System.out.println("Category: " + item.getCategory());
                    System.out.println("Quantity: " + item.getQuantity());
                    System.out.println("Price: $" + item.getPrice());
                    return;
                }
            }
            System.out.println("Item with ID " + id + " not found.");
        }
        public void deleteItem(int id) {
            for (Inventory item : itemList) {
                if (item.getInventoryID() == id) {
                    itemList.remove(item);
                    System.out.println("Item with ID " + id + " deleted successfully.");
                    return;
                }
            }
            System.out.println("Item with ID " + id + " not found.");


        }
}