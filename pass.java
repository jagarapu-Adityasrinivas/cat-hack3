import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ThreeLevelPasswordSystem {
    // Simulated user database
    private static Map<String, User> usersDb = new HashMap<>();

    static {
        // Adding a sample user
        usersDb.put("user1", new User("primary_password", "What is your pet's name?", "Fluffy", "user1@example.com"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your username (or type 'exit' to quit): ");
            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("exit")) {
                break; // Exit the loop if the user types 'exit'
            }

            if (authenticateUser(username, scanner)) {
                System.out.println("Authentication successful!");
            } else {
                System.out.println("User not found! Would you like to register? (yes/no)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    registerUser(username, scanner);
                }
            }
        }
        scanner.close();
    }

    private static boolean authenticateUser(String username, Scanner scanner) {
        User user = usersDb.get(username);
        if (user == null) {
            return false; // User does not exist
        }

        // Level 1: Primary Password
        System.out.print("Enter your primary password: ");
        String primaryPassword = scanner.nextLine();
        if (!primaryPassword.equals(user.getPassword())) {
            System.out.println("Incorrect primary password!");
            return false;
        }

        // Level 2: Security Question
        System.out.print(user.getSecurityQuestion() + " ");
        String answer = scanner.nextLine();
        if (!answer.equals(user.getSecurityAnswer())) {
            System.out.println("Incorrect answer to the security question!");
            return false;
        }

        // Level 3: One-Time Password (OTP)
        int otp = generateOtp();
        sendOtp(user.getEmail(), otp);
        System.out.print("Enter the OTP sent to your email: ");
        int userOtp = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        if (userOtp != otp) {
            System.out.println("Incorrect OTP!");
            return false;
        }

        return true;
    }

    private static void registerUser(String username, Scanner scanner) {
        System.out.print("Enter your primary password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your security question: ");
        String securityQuestion = scanner.nextLine();
        System.out.print("Enter your security answer: ");
        String securityAnswer = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Add the new user to the database
        usersDb.put(username, new User(password, securityQuestion, securityAnswer, email));
        System.out.println("User registered successfully!");
    }

    private static int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Generates a 6-digit OTP
    }

    private static void sendOtp(String email, int otp) {
        // Placeholder for sending email
        System.out.println("Sending OTP: " + otp + " to " + email);
        // Implement actual email sending logic here
    }

    private static class User {
        private String password;
        private String securityQuestion;
        private String securityAnswer;
        private String email;

        public User(String password, String securityQuestion, String securityAnswer, String email) {
            this.password = password;
            this.securityQuestion = securityQuestion;
            this.securityAnswer = securityAnswer;
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public String getSecurityQuestion() {
            return securityQuestion;
        }

        public String getSecurityAnswer() {
            return securityAnswer;
        }

        public String getEmail() {
            return email;
        }
    }
}
