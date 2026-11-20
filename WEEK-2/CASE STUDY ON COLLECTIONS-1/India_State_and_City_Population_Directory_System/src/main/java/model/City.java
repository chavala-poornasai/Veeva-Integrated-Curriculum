
package model;

public class City {

    private String cityName;
    private long population;

    public City(String cityName, long population) {
        this.cityName = cityName;
        this.population = population;
    }

    public String getCityName() {
        return cityName;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return cityName + " : " + population;
    }
}
