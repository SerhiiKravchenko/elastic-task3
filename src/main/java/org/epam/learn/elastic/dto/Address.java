package org.epam.learn.elastic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    private String country;
    private String town;

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", town='" + town + '\'' +
                '}';
    }
}
