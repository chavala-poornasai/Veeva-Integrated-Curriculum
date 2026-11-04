
import java.util.Scanner;

public class InventoryManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Inventory inventory = new Inventory();

        int choice;

        do {

            System.out.println("\n===== INVENTORY MANAGEMENT SYSTEM =====");

            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Check Quantity");
            System.out.println("4. Display Inventory");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid choice: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter item name: ");
                    String addName = sc.nextLine().trim();

                    System.out.print("Enter quantity: ");

                    while (!sc.hasNextInt()) {
                        System.out.print("Enter a valid quantity: ");
                        sc.next();
                    }

                    int addQuantity = sc.nextInt();
                    sc.nextLine();

                    inventory.addItem(addName, addQuantity);
                    break;

                case 2:

                    System.out.print("Enter item name: ");
                    String removeName = sc.nextLine().trim();

                    System.out.print("Enter quantity to remove: ");

                    while (!sc.hasNextInt()) {
                        System.out.print("Enter a valid quantity: ");
                        sc.next();
                    }

                    int removeQuantity = sc.nextInt();
                    sc.nextLine();

                    inventory.removeItem(removeName, removeQuantity);
                    break;

                case 3:

                    System.out.print("Enter item name: ");
                    String checkName = sc.nextLine().trim();

                    inventory.checkQuantity(checkName);
                    break;

                case 4:

                    inventory.displayInventory();
                    break;

                case 5:

                    System.out.println("Exiting Inventory Management System...");
                    break;

                default:

                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}
