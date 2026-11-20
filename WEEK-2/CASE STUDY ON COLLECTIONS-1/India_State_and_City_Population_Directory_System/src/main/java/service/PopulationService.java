
package service;

import model.City;
import java.util.HashMap;

public class PopulationService {

    private HashMap<String, HashMap<String, City>> populationMap;

    public PopulationService() {
        populationMap = new HashMap<>();
    }

    // Add a new state
    public void addState(String stateName) {
        if (populationMap.containsKey(stateName)) {
            System.out.println("State already exists!");
            return;
        }

        populationMap.put(stateName, new HashMap<>());
        System.out.println("State added successfully!");
    }

    // Add a new city to an existing state
    public void addCity(String stateName, String cityName, long population) {

        if (!populationMap.containsKey(stateName)) {
            System.out.println("State does not exist!");
            return;
        }

        HashMap<String, City> cities = populationMap.get(stateName);

        if (cities.containsKey(cityName)) {
            System.out.println("City already exists in this state!");
            return;
        }

        cities.put(cityName, new City(cityName, population));

        System.out.println("City added successfully!");
    }

    public void updatePopulation(String stateName, String cityName, long newPopulation) {

        if (!populationMap.containsKey(stateName)) {
            System.out.println("State does not exist!");
            return;
        }

        HashMap<String, City> cities = populationMap.get(stateName);

        if (!cities.containsKey(cityName)) {
            System.out.println("City does not exist!");
            return;
        }

        City city = cities.get(cityName);

        city.setPopulation(newPopulation);

        System.out.println("Population updated successfully!");
    }

    public void searchCity(String stateName, String cityName) {

        if (!populationMap.containsKey(stateName)) {
            System.out.println("State does not exist!");
            return;
        }

        HashMap<String, City> cities = populationMap.get(stateName);

        if (!cities.containsKey(cityName)) {
            System.out.println("City does not exist!");
            return;
        }

        City city = cities.get(cityName);

        System.out.println("City: " + city.getCityName());
        System.out.println("Population: " + city.getPopulation());
    }

    public void displayStatePopulation(String stateName) {

        if (!populationMap.containsKey(stateName)) {
            System.out.println("State does not exist!");
            return;
        }

        HashMap<String, City> cities = populationMap.get(stateName);

        long totalPopulation = 0;

        System.out.println("State: " + stateName);
        System.out.println("Cities and their populations:");

        for (City city : cities.values()) {

            System.out.println(city.getCityName() + " : " + city.getPopulation());

            totalPopulation += city.getPopulation();
        }

        System.out.println("Total Population: " + totalPopulation);
    }

    public void searchCityGlobally(String cityName) {

        boolean found = false;

        for (String state : populationMap.keySet()) {

            HashMap<String, City> cities = populationMap.get(state);

            if (cities.containsKey(cityName)) {

                City city = cities.get(cityName);

                System.out.println("State: " + state);
                System.out.println("City: " + city.getCityName());
                System.out.println("Population: " + city.getPopulation());

                found = true;
            }
        }

        if (!found) {
            System.out.println("City not found in any state!");
        }
    }

    public void filterCitiesByPopulation(long threshold) {

        int count = 0;

        System.out.println("Cities with population greater than " + threshold);

        for (String state : populationMap.keySet()) {

            HashMap<String, City> cities = populationMap.get(state);

            for (City city : cities.values()) {

                if (city.getPopulation() > threshold) {

                    System.out.println(state + " -> " +
                            city.getCityName() + " : " +
                            city.getPopulation());

                    count++;
                }
            }
        }

        if (count == 0) {
            System.out.println("No cities found above the given threshold!");
        } else {
            System.out.println("Total matching cities: " + count);
        }
    }

    


}
