public class Login {

    private int    userId;
    private String username;
    private String password;
    private String role;   // "FARMER" or "MANAGER"

    public Login(int userId, String username, String password, String role) {
        this.userId   = userId;
        this.username = username;
        this.password = password;
        this.role     = role.toUpperCase();
    }

    /**
     * Validates credentials and returns the role string if valid, null otherwise.
     */
    public String authenticate(String enteredUsername, String enteredPassword) {
        if (this.username.equals(enteredUsername) && this.password.equals(enteredPassword)) {
            return role;
        }
        return null;
    }

    public boolean isManager() { return "MANAGER".equals(role); }
    public boolean isFarmer()  { return "FARMER".equals(role);  }

    // Getters
    public int    getUserId()   { return userId; }
    public String getUsername() { return username; }
    public String getRole()     { return role; }
}