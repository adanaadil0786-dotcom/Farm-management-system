import java.util.Scanner;

public class Farmers
{
    int farmerId;

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public int getPhoneNo() {
        return phone_number;
    }

    public void setPhoneNo(int phoneNo) {
        this.phone_number = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFarmername() {
        return farmername;
    }

    public void setFarmername(String farmername) {
        this.farmername = farmername;
    }

    public Farmers(int farmerId, String farmername, int phoneNo, String address) {
        this.farmerId = farmerId;
        this.farmername = farmername;
        this.phone_number = phoneNo;
        this.address = address;
    }

    String farmername;
    int phone_number;
    String address;
    void addfarmers(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter farmer name: ");
        farmername = input.nextLine();
        System.out.print("Enter phone number: ");
        phone_number = input.nextInt();
        System.out.print("Enter address: ");
        address = input.nextLine();
    }
    void updatefarmers(){
        Scanner input = new Scanner(System.in);

    }
}