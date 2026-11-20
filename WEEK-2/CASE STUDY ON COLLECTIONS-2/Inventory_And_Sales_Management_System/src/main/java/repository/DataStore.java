
package repository;

import model.Company;
import model.Product;
import model.Transaction;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    // Company Directory
    private static final Map<Integer, Company> companies = new HashMap<>();

    // Product Catalog
    private static final Map<Integer, Product> products = new HashMap<>();

    // Inventory Tracker
    private static final Map<Integer, Integer> inventory = new HashMap<>();

    // Transaction Log
    private static final Map<String, Transaction> transactions = new HashMap<>();

    // Getters for accessing collections

    public static Map<Integer, Company> getCompanies() {
        return companies;
    }

    public static Map<Integer, Product> getProducts() {
        return products;
    }

    public static Map<Integer, Integer> getInventory() {
        return inventory;
    }

    public static Map<String, Transaction> getTransactions() {
        return transactions;
    }
}
