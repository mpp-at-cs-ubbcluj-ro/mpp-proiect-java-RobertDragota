package org.project.travel_agency.Domain;

public class Client extends Entity<Long>{
    private final String name;
    private final String email;

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
