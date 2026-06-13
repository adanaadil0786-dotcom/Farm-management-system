import java.time.LocalDate;

/**
 * AnimalStock extends Animals to add livestock inventory tracking.
 * Demonstrates inheritance: AnimalStock IS-A Animals with additional stock attributes.
 */
public class AnimalStock extends Animals {

    private int stockCount;          // number of this animal in stock
    private int minStockLevel;       // threshold for low-stock notification
    private double purchasePrice;    // cost per head
    private double salePrice;        // selling price per head
    private LocalDate lastRestocked;
    private String feedType;
    private double dailyFeedKg;

    public AnimalStock(int animalId, String animalname, int age,
                       String health_status, double weight,
                       int stockCount, int minStockLevel,
                       double purchasePrice, double salePrice,
                       String feedType, double dailyFeedKg) {
        super(animalId, animalname, age, health_status, String.valueOf(weight));
        this.stockCount      = stockCount;
        this.minStockLevel   = minStockLevel;
        this.purchasePrice   = purchasePrice;
        this.salePrice       = salePrice;
        this.lastRestocked   = LocalDate.now();
        this.feedType        = feedType;
        this.dailyFeedKg     = dailyFeedKg;
    }

    // ── Stock management ──────────────────────────────────────────────────────

    public boolean isLowStock() {
        return stockCount <= minStockLevel;
    }

    public void addStock(int count) {
        this.stockCount  += count;
        this.lastRestocked = LocalDate.now();
    }

    public void removeStock(int count) {
        if (count > stockCount)
            throw new IllegalArgumentException("Cannot remove more than current stock.");
        this.stockCount -= count;
    }

    // ── Profit calculation ────────────────────────────────────────────────────

    /** Total purchase (input) cost for current stock */
    public double getTotalCost() {
        return stockCount * purchasePrice;
    }

    /** Potential revenue if all current stock is sold */
    public double getPotentialRevenue() {
        return stockCount * salePrice;
    }

    /** Potential profit margin on current stock */
    public double getPotentialProfit() {
        return getPotentialRevenue() - getTotalCost();
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int getStockCount()                      { return stockCount; }
    public void setStockCount(int stockCount)       { this.stockCount = stockCount; }

    public int getMinStockLevel()                   { return minStockLevel; }
    public void setMinStockLevel(int min)           { this.minStockLevel = min; }

    public double getPurchasePrice()                { return purchasePrice; }
    public void setPurchasePrice(double p)          { this.purchasePrice = p; }

    public double getSalePrice()                    { return salePrice; }
    public void setSalePrice(double s)              { this.salePrice = s; }

    public LocalDate getLastRestocked()             { return lastRestocked; }

    public String getFeedType()                     { return feedType; }
    public void setFeedType(String feedType)        { this.feedType = feedType; }

    public double getDailyFeedKg()                  { return dailyFeedKg; }
    public void setDailyFeedKg(double d)            { this.dailyFeedKg = d; }

    public String getDetails() {
        return super.getClass() +
                " | Stock: " + stockCount +
                " | Min Level: " + minStockLevel +
                " | Buy: $" + purchasePrice +
                " | Sell: $" + salePrice +
                (isLowStock() ? "  ⚠ LOW STOCK" : "");
    }
}
