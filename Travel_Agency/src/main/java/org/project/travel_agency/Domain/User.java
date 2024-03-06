package org.project.travel_agency.Domain;

public class User extends Entity<Long>{
    private final String userName;
    private final String firstName;
    private final String lastName;
    public User(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
