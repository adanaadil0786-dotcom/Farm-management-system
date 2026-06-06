import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
public class Farm_Project {
    public static void main(String[] args) {
        System.out.println("Welcome to Farm Project");
        Login l = new Login(1, "adan", "123", "employee");
        l.login();


        sensors tempSensor = new sensors(1, "Temperature");

        tempSensor.generateReading();
        tempSensor.displayData();

        System.out.println();

        sensors moistureSensor = new sensors(2, "Moisture");

        moistureSensor.generateReading();
        moistureSensor.displayData();
        Farmers f1 = new Farmers(1, "Zahid", 5656567, "Multan");
        Farmers f2 = new Farmers(2, "Abdullah", 64542424, "Sailkot");
        Farmers f3 = new Farmers(3, "Zaviar", 45577, "Sahiwal");


        String url = "jdbc:mysql://localhost:3306/smart_farm";
        String username = "root";
        String password = "Adanaadil414";


        String sql = "INSERT INTO farmers (farmer_id, farmer_name, phone_number, Address) VALUES (?, ?, ?, ?)";

        // 4. Connect and execute
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            System.out.println("Database Connected Successfully!");


            pstmt.setInt(1, f1.getFarmerId());
            pstmt.setString(2, f1.getFarmername());
            pstmt.setInt(3,f1.phone_number);
            pstmt.setString(4, f1.getAddress());

            pstmt.setInt(1, f2.getFarmerId());
            pstmt.setString(2, f2.getFarmername());
            pstmt.setInt(3,f2.phone_number);
            pstmt.setString(4, f2.getAddress());

            pstmt.setInt(1, f3.getFarmerId());
            pstmt.setString(2, f3.getFarmername());
            pstmt.setInt(3,f3.phone_number);
            pstmt.setString(4, f3.getAddress());


            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new farmer was inserted successfully into MySQL!");
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }


}}

