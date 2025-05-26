package org.epam.learn.elastic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    private String name;
    private LocalDate dob;
    private Address address;
    private String email;
    private String[] skills;
    private long experience;
    private double rating;
    private String description;
    private boolean verified;
    private long salary;

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String[] getSkills() {
        return skills;
    }

    public long getExperience() {
        return experience;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVerified() {
        return verified;
    }

    public long getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dob=" + dob +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", skills=" + Arrays.toString(skills) +
                ", experience=" + experience +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", verified=" + verified +
                ", salary=" + salary +
                '}';
    }
}
