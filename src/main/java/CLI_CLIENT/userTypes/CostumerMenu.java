package CLI_CLIENT.userTypes;

import CLI_CLIENT.UserMenu;

public class CostumerMenu extends UserMenu {
    @Override
    public void runMenuByUserType(int userType) {
        System.out.println("Hello World!");
        // Waiting for the user to respond
        System.out.println("Press number as you like: ");
        System.out.println("1. Add new order");
        System.out.println("2. Add new order item");
    }
}