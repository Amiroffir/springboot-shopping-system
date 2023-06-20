package CLI_CLIENT;

import java.util.Scanner;

public abstract class UserMenu {

    public void runMenu() {
        System.out.println("Hello World!");
        // Waiting for the user to respond

        // Reading data using readLine
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        runMenuByUserType(n);
    }

    public abstract void runMenuByUserType(int userType);
}