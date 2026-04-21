public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        AuthService authService = new AuthService();

        boolean registered = authService.registerUser("sibe", "mypassword123");
        System.out.println("Registered: " + registered);

        boolean loggedIn = authService.loginUser("sibe", "mypassword123");
        System.out.println("Logged in: " + loggedIn);

        boolean wrongPassword = authService.loginUser("sibe", "wrongpassword");
        System.out.println("Wrong password: " + wrongPassword);
    }
}
