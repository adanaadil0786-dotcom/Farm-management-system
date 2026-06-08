import java.util.Scanner;

public class Login {

    private int userId;
    private String username;
    private String password;
    private String role;

    public Login(int userID, String username, String password, String role) {
        this.userId = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    void login() {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter username: ");
        String enteredUsername = input.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = input.nextLine();

        if (enteredUsername.equals(username)
                && enteredPassword.equals(password)) {

            System.out.println("Login Successful!");
            System.out.println("Welcome " + role);

        } else {

            System.out.println("Invalid Username or Password");
        }
    }

    void logout() {
        System.out.println("Logout Successful!");
    }
}