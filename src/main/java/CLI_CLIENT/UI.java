package CLI_CLIENT;



import java.net.http.HttpClient;
import java.util.Scanner;

import static CLI_CLIENT.ClientOperations.*;

public class UI {
    public static void main(String[] args) {
        System.out.println("Welcome to Shopping System!");
        System.out.println("In order to continue, please choose your way:");
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            // Reading data using readLine
            Scanner reader = new Scanner(System.in);
            int chosen = reader.nextInt();

            if (chosen == 1) {
                register();
            } else if (chosen == 2) {
                login();
            } else if (chosen == 3) {
                System.out.println("Bye Bye!");
                break;
            } else {
                invalidInput();
            }
        }
    }
}