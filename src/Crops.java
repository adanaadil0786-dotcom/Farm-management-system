public class Crops {

    private int cropId;
    private String cropName;
    private String season;
    private int price;
    private int quantity;
    private String plantDate;
    private String harvestDate;

    public Crops(int cropId, String cropName,
                String season, int price,
                int quantity,
                String plantDate,
                String harvestDate) {

        this.cropId = cropId;
        this.cropName = cropName;
        this.season = season;
        this.price = price;
        this.quantity = quantity;
        this.plantDate = plantDate;
        this.harvestDate = harvestDate;
    }

    public double calculateRevenue() {
        return price * quantity;
    }

    public void addCrop() {
        System.out.println("Crop added successfully.");
    }

    public void updateCrop() {
        System.out.println("Crop updated successfully.");
    }
    public void deleteCrop() {
        System.out.println("Crop deleted successfully.");

    }

}
