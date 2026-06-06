import java.util.Scanner;

public class Crops
{
    int cropId;

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Crops(int cropId, String cropname, String season, int price, int quantity, int profit) {
        this.cropId = cropId;
        this.cropname = cropname;
        this.season = season;
        this.price = price;
        this.quantity = quantity;
        this.profit = profit;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int getProfit() {
        return profit;
    }

    private void setProfit(int profit) {
        this.profit = profit;
    }

    String cropname;
    String season;
    int price;
    int quantity;
    int profit;
    void addcrops(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter crop name: ");
        cropname = input.nextLine();
    }
    void updatecrops() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter season: ");
        season = input.nextLine();
        System.out.print("Enter price: ");
        price = input.nextInt();
    }
    double calculateProfit() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter quantity: ");
        quantity = input.nextInt();
        profit = price * quantity;
        return profit;
    }}

