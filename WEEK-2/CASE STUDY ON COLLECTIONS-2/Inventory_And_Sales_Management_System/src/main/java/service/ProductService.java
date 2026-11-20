
package service;

import exception.ProductNotFoundException;
import model.Company;
import model.Product;
import repository.DataStore;

import java.math.BigDecimal;

public class ProductService {

    // Add a new product


    public void addProduct(Product product, int initialStock) {

        if (product == null ||
                product.getProductName() == null ||
                product.getProductName().isBlank() ||
                product.getCategory() == null ||
                product.getCategory().isBlank()) {

            System.out.println("Invalid product details!");
            return;
        }

        int id = product.getProductId();

        if (DataStore.getProducts().containsKey(id)) {
            System.out.println("Product already exists!");
            return;
        }

        for (Product existing : DataStore.getProducts().values()) {
            if (existing.getProductName()
                    .equalsIgnoreCase(product.getProductName())) {

                System.out.println("Product name already exists!");
                return;
            }
        }

        if (!DataStore.getCompanies().containsKey(product.getCompanyId())) {
            System.out.println("Company does not exist!");
            return;
        }

        if (product.getUnitPrice() == null ||
                product.getUnitPrice().signum() <=0 ||
                initialStock < 0) {

            System.out.println("Invalid price or stock!");
            return;
        }

        DataStore.getProducts().put(id, product);
        DataStore.getInventory().put(id, initialStock);

        System.out.println("Product added successfully!");
    }


    // Update product details

    public void updateProduct(int id, String name,
                              BigDecimal price, String category) {

        Product product = DataStore.getProducts().get(id);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        if (name == null || name.isBlank() ||
                category == null || category.isBlank() ||
                price == null || price.signum() < 0) {

            System.out.println("Invalid product details!");
            return;
        }

        for (Product existing : DataStore.getProducts().values()) {

            if (existing.getProductId() != id &&
                    existing.getProductName().equalsIgnoreCase(name)) {

                System.out.println("Product name already exists!");
                return;
            }
        }

        product.setProductName(name);
        product.setUnitPrice(price);
        product.setCategory(category);

        System.out.println("Product updated successfully!");
    }

    // Price lookup with supplier details

    public void priceLookup(String productName) {

        for (Product product : DataStore.getProducts().values()) {

            if (product.getProductName().equalsIgnoreCase(productName)) {

                Company company = DataStore.getCompanies()
                        .get(product.getCompanyId());

                System.out.println("Product: " + product.getProductName());
                System.out.println("Price: " + product.getUnitPrice());
                System.out.println("Supplier: " + company.getCompanyName());
                System.out.println("Location: " + company.getLocation());

                return;
            }
        }

        System.out.println("Product not found!");
    }
    public void priceLookup(int id) {

        Product product = DataStore.getProducts().get(id);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        Company company = DataStore.getCompanies()
                .get(product.getCompanyId());

        System.out.println("Product: " + product.getProductName());
        System.out.println("Price: " + product.getUnitPrice());
        System.out.println("Supplier: " + company.getCompanyName());
        System.out.println("Location: " + company.getLocation());
    }

}
