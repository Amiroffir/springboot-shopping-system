package CLI_CLIENT;

import CLI_CLIENT.userTypes.AdminMenu;
import CLI_CLIENT.userTypes.CostumerMenu;



public class ClientOperations {
    private static final String BASE_URL = "http://localhost:8080";


    static void invalidInput() {
        System.out.println("Invalid input, please try again");
    }

    static void login() {

    }


    private static void runMenuByUserType(int userType) {
        if (userType == 1) {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.runMenu();
        } else if (userType == 2) {
            CostumerMenu costumerMenu = new CostumerMenu();
            costumerMenu.runMenu();
        }
    }
      static void register() {
        // Send request to server
        // Get response from server
        // If response is OK, then run menu by user type
        // Else, print error message
    }
}