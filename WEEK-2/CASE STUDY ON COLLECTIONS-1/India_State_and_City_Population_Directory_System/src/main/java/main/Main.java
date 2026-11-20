
package main;

import java.util.Scanner;
import service.PopulationService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PopulationService service = new PopulationService();

        int choice;

        do {

            System.out.println("\n===== INDIA POPULATION DIRECTORY =====");
            System.out.println("1. Add State");
            System.out.println("2. Add City");
            System.out.println("3. Update City Population");
            System.out.println("4. Search City by State");
            System.out.println("5. Display Cities in a State");
            System.out.println("6. Global City Search");
            System.out.println("7. Filter Cities by Population");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter state name: ");
                    String state = sc.nextLine();

                    service.addState(state);
                    break;

                case 2:
                    System.out.print("Enter state name: ");
                    state = sc.nextLine();

                    System.out.print("Enter city name: ");
                    String city = sc.nextLine();

                    System.out.print("Enter population: ");
                    long population = sc.nextLong();
                    sc.nextLine();

                    service.addCity(state, city, population);
                    break;

                case 3:
                    System.out.print("Enter state name: ");
                    state = sc.nextLine();

                    System.out.print("Enter city name: ");
                    city = sc.nextLine();

                    System.out.print("Enter new population: ");
                    population = sc.nextLong();
                    sc.nextLine();

                    service.updatePopulation(state, city, population);
                    break;

                case 4:
                    System.out.print("Enter state name: ");
                    state = sc.nextLine();

                    System.out.print("Enter city name: ");
                    city = sc.nextLine();

                    service.searchCity(state, city);
                    break;

                case 5:
                    System.out.print("Enter state name: ");
                    state = sc.nextLine();

                    service.displayStatePopulation(state);
                    break;

                case 6:
                    System.out.print("Enter city name: ");
                    city = sc.nextLine();

                    service.searchCityGlobally(city);
                    break;

                case 7:
                    System.out.print("Enter population threshold: ");
                    population = sc.nextLong();
                    sc.nextLine();

                    service.filterCitiesByPopulation(population);
                    break;

                case 8:
                    System.out.println("Exiting application...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 8);

        sc.close();
    }
}
