
import exception.CompanyNotFoundException;
import exception.InsufficientStockException;
import exception.OutOfStockException;
import model.Company;
import model.Product;
import model.Transaction;
import repository.DataStore;
import service.CompanyService;
import service.ProductService;
import util.InputUtil;
import service.InventoryService;
import model.OrderItem;
import service.SalesService;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        int choice;
        CompanyService companyService = new CompanyService();
        ProductService productService = new ProductService();
        InventoryService inventoryService = new InventoryService();
        SalesService salesService = new SalesService();


        do {
            System.out.println("\n===== INVENTORY & SALES MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Company");
            System.out.println("2. Update Company");
            System.out.println("3. Add Product");
            System.out.println("4. Update Product");
            System.out.println("5. View Product Details");
            System.out.println("6. Check Available Stock");
            System.out.println("7. Restock Product");
            System.out.println("8. Process Customer Sale");
            System.out.println("9. View Transaction Details");
            System.out.println("0. Exit");

            choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 0:
                    System.out.println("Exiting application...");
                    break;
                case 1:
                {
                    int companyId = InputUtil.getInt("Enter Company ID: ");
                    String companyName = InputUtil.getString("Enter Company Name: ");
                    String contact = InputUtil.getString("Enter Contact Details: ");
                    String location = InputUtil.getString("Enter Location: ");

                    Company company = new Company(
                            companyId, companyName, contact, location
                    );

                    companyService.addCompany(company);
                    break;
            }
                case 2: {
                    try {
                        int id = InputUtil.getInt("Enter Company ID to update: ");

                        String name = InputUtil.getString("Enter New Company Name: ");
                        String contact = InputUtil.getString("Enter New Contact Details: ");
                        String location = InputUtil.getString("Enter New Location: ");

                        companyService.updateCompany(id, name, contact, location);

                    } catch (CompanyNotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 3: {
                    int productId = InputUtil.getInt("Enter Product ID: ");

                    String productName = InputUtil.getString("Enter Product Name: ");

                    BigDecimal price = InputUtil.getBigDecimal("Enter Unit Price: ");

                    int companyId = InputUtil.getInt("Enter Company ID: ");

                    String category = InputUtil.getString("Enter Category: ");

                    int stock = InputUtil.getInt("Enter Initial Stock: ");

                    Product product = new Product(
                            productId, productName, price, companyId, category
                    );

                    productService.addProduct(product, stock);
                    break;
                }
                case 4: {
                    int productId = InputUtil.getInt("Enter Product ID to update: ");

                    String name = InputUtil.getString("Enter New Product Name: ");

                    BigDecimal price = InputUtil.getBigDecimal("Enter New Unit Price: ");

                    String category = InputUtil.getString("Enter New Category: ");

                    productService.updateProduct(productId, name, price, category);
                    break;
                }

                case 5: {
                    System.out.println("1. Search by Product ID");
                    System.out.println("2. Search by Product Name");

                    int searchChoice = InputUtil.getInt("Enter choice: ");

                    if (searchChoice == 1) {
                        int productId = InputUtil.getInt("Enter Product ID: ");
                        productService.priceLookup(productId);
                    }
                    else if (searchChoice == 2) {
                        String productName = InputUtil.getString("Enter Product Name: ");
                        productService.priceLookup(productName);
                    }
                    else {
                        System.out.println("Invalid choice!");
                    }

                    break;
                }

                case 6: {
                    int productId = InputUtil.getInt("Enter Product ID: ");

                    int stock = inventoryService.getAvailableStock(productId);

                    if (stock >= 0) {
                        System.out.println("Available Stock: " + stock);
                    }

                    break;
                }
                case 7: {
                    int productId = InputUtil.getInt("Enter Product ID: ");

                    int quantity = InputUtil.getInt("Enter Quantity to Restock: ");

                    inventoryService.restockProduct(productId, quantity);
                    break;
                }
                case 8: {
                    List<OrderItem> orderItems = new ArrayList<>();

                    int count = InputUtil.getInt("Enter number of different products: ");

                    if (count <= 0) {
                        System.out.println("Invalid number of products!");
                        break;
                    }

                    for (int i = 0; i < count; i++) {

                        System.out.println("\n Product " + (i + 1));

                        int productId = InputUtil.getInt("Enter Product ID: ");

                        int quantity = InputUtil.getInt("Enter Quantity: ");

                        orderItems.add(new OrderItem(productId, quantity));
                    }

                    try {
                        salesService.processSale(orderItems);

                    } catch (OutOfStockException e) {
                        System.out.println("Alert: " + e.getMessage());
                    }
                    catch (InsufficientStockException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 9: {
                    String transactionId = InputUtil.getString("Enter Transaction ID: ");

                    Transaction transaction =
                            DataStore.getTransactions().get(transactionId);

                    if (transaction == null) {
                        System.out.println("Transaction not found!");
                        break;
                    }

                    System.out.println("\n========== TRANSACTION DETAILS ==========");
                    System.out.println("Transaction ID: " + transaction.getTransactionId());
                    System.out.println("Timestamp: " + transaction.getTimestamp());

                    System.out.println("\nProducts:");

                    for (OrderItem item : transaction.getOrderItems()) {
                        Product product = DataStore.getProducts().get(item.getProductId());

                        System.out.println(
                                product.getProductName() + " x " + item.getQuantity()
                        );
                    }

                    System.out.println("Total Amount: ₹" + transaction.getTotalAmount());
                    break;
                }

                default:
                    System.out.println("Feature implementation pending.");
            }


        } while (choice != 0);
    }
}
