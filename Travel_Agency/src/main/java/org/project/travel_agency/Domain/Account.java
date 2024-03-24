package org.project.travel_agency.Domain;

public class Account extends Entity<Long> {
    private String name;
    private String password;

    public Account(String name, String password) {
        this.name = name;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
