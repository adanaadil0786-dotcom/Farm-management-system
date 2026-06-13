/**
 * Crops — fields: cropId, cropName, season, price, quantity, plantDate, harvestDate.
 * addCrop()/updateCrop()/deleteCrop() are kept as instance methods (as in your
 * original class) and are also called from Farm_Project + DatabaseManager.
 */
public class Crops {

    private int    cropId;
    private String cropName;
    private String season;
    private int    price;
    private int    quantity;
    private String plantDate;
    private String harvestDate;

    public Crops(int cropId, String cropName, String season, int price,
                 int quantity, String plantDate, String harvestDate) {
        this.cropId      = cropId;
        this.cropName    = cropName;
        this.season      = season;
        this.price       = price;
        this.quantity    = quantity;
        this.plantDate   = plantDate;
        this.harvestDate = harvestDate;
    }

    /** Revenue if entire crop quantity is sold at `price`. */
    public double calculateRevenue() {
        return (double) price * quantity;
    }

    public void addCrop()    { System.out.println("Crop added successfully: "   + cropName); }
    public void updateCrop() { System.out.println("Crop updated successfully: " + cropName); }
    public void deleteCrop() { System.out.println("Crop deleted successfully: " + cropName); }

    // ── Getters / Setters ─────────────────────────────────────────────────────
    public int    getCropId()                  { return cropId; }
    public void   setCropId(int id)            { this.cropId = id; }

    public String getCropName()                { return cropName; }
    public void   setCropName(String n)        { this.cropName = n; }

    public String getSeason()                  { return season; }
    public void   setSeason(String s)          { this.season = s; }

    public int    getPrice()                   { return price; }
    public void   setPrice(int p)              { this.price = p; }

    public int    getQuantity()                { return quantity; }
    public void   setQuantity(int q)           { this.quantity = q; }

    public String getPlantDate()               { return plantDate; }
    public void   setPlantDate(String d)       { this.plantDate = d; }

    public String getHarvestDate()             { return harvestDate; }
    public void   setHarvestDate(String d)     { this.harvestDate = d; }

    public String getDetails() {
        return "ID: " + cropId + " | Crop: " + cropName +
                " | Season: " + season + " | Price: $" + price +
                " | Qty: " + quantity + " | Planted: " + plantDate +
                " | Harvest: " + harvestDate +
                " | Revenue: $" + String.format("%.2f", calculateRevenue());
    }
}