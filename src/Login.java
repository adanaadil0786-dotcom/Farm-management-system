import java.util.Scanner;

public class Login{
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;





    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Login(int userID, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    String username;
    String password;
    String role;

    void login() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter username: ");
        String enteredUsername = input.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = input.nextLine();

        if(enteredUsername.equals(username) &&
                enteredPassword.equals(password))
        {
            System.out.println("Login Successful!");
        }
        else
        {
            System.out.println("Invalid Username or Password");
        }
    }
    void logout(){

    }

}



