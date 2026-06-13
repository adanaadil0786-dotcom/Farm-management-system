public class Farmers {

    private int farmerId;
    private String farmerName;
    private String phone;       // 4th field – was missing in original
    private String address;

    public Farmers(int farmerId, String farmerName, String phone, String address) {
        this.farmerId   = farmerId;
        this.farmerName = farmerName;
        this.phone      = phone;
        this.address    = address;
    }

    public int getFarmerId()                { return farmerId; }
    public void setFarmerId(int id)         { this.farmerId = id; }

    public String getFarmerName()               { return farmerName; }
    public void setFarmerName(String name)      { this.farmerName = name; }

    public String getPhone()                { return phone; }
    public void setPhone(String phone)      { this.phone = phone; }

    public String getAddress()              { return address; }
    public void setAddress(String address)  { this.address = address; }

    public String getDetails() {
        return "ID: " + farmerId +
                ", Name: " + farmerName +
                ", Phone: " + phone +
                ", Address: " + address;
    }
}