
package service;

import exception.CompanyNotFoundException;
import model.Company;
import repository.DataStore;

public class CompanyService {

    // Add a new company

    public void addCompany(Company company) {

        if (company == null ||
                company.getCompanyName() == null ||
                company.getCompanyName().isBlank() ||
                company.getContactDetails() == null ||
                company.getContactDetails().isBlank() ||
                company.getLocation() == null ||
                company.getLocation().isBlank()) {

            System.out.println("Invalid company details!");
            return;
        }

        int id = company.getCompanyId();

        if (DataStore.getCompanies().containsKey(id)) {
            System.out.println("Company already exists!");
            return;
        }

        DataStore.getCompanies().put(id, company);

        System.out.println("Company registered successfully!");
    }


    // Update existing company details


    public void updateCompany(int id, String name, String contact, String location) {

        Company company = DataStore.getCompanies().get(id);

        if (company == null) {
            throw new CompanyNotFoundException(
                    "Company not found with ID: " + id
            );
        }

        if (name == null || name.isBlank() ||
                contact == null || contact.isBlank() ||
                location == null || location.isBlank()) {

            System.out.println("Invalid company details!");
            return;
        }

        company.setCompanyName(name);
        company.setContactDetails(contact);
        company.setLocation(location);

        System.out.println("Company details updated successfully!");
    }


}
