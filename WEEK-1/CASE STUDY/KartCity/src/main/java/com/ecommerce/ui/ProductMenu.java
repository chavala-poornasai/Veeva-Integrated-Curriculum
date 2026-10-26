package com.ecommerce.ui;

import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    private final ProductService productService;

    public ProductMenu(ProductService productService) {
        this.productService = productService;
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nProduct Catalog");
            System.out.println("1. View all products");
            System.out.println("2. Search by product ID");
            System.out.println("3. Search by name");
            System.out.println("4. Search by name and exact price");
            System.out.println("5. Search by name and price range");
            System.out.println("6. Search by name and company");
            System.out.println("7. Search by name, company, and exact price");
            System.out.println("0. Return");
            String choice = ConsoleInput.readMenuChoice(scanner, "Choose an option: ",
                    "0", "1", "2", "3", "4", "5", "6", "7");
            if (choice == null) {
                return;
            }
            if (choice.isEmpty()) {
                continue;
            }

            try {
                switch (choice) {
                    case "1" -> printProducts(productService.getAllProducts());
                    case "2" -> {
                        System.out.print("Product ID: ");
                        printProduct(productService.getProductById(scanner.nextLine().trim()));
                    }
                    case "3" -> {
                        System.out.print("Product name: ");
                        printProducts(productService.searchByName(scanner.nextLine()));
                    }
                    case "4" -> {
                        System.out.print("Product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Exact price: ");
                        printProducts(productService.searchByNameAndExactPrice(name, readPrice(scanner)));
                    }
                    case "5" -> {
                        System.out.print("Product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Minimum price: ");
                        BigDecimal minimum = readPrice(scanner);
                        System.out.print("Maximum price: ");
                        printProducts(productService.searchByNameAndPriceRange(name, minimum, readPrice(scanner)));
                    }
                    case "6" -> {
                        System.out.print("Product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Company: ");
                        printProducts(productService.searchByNameAndCompany(name, scanner.nextLine()));
                    }
                    case "7" -> {
                        System.out.print("Product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Company: ");
                        String company = scanner.nextLine();
                        System.out.print("Exact price: ");
                        printProducts(productService.searchByNameCompanyAndPrice(name, company, readPrice(scanner)));
                    }
                    case "0" -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Validated menu option was not handled: " + choice);
                }
            } catch (ProductNotFoundException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private BigDecimal readPrice(Scanner scanner) {
        BigDecimal price = new BigDecimal(scanner.nextLine().trim());
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return price;
    }

    private void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        products.forEach(this::printProduct);
    }

    private void printProduct(Product product) {
        System.out.printf("%s | %s | %s | INR %s | Stock: %d%n",
                product.getProductId(), product.getName(), product.getCompany(),
                product.getPrice().toPlainString(), product.getStock());
    }
}
