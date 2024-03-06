package org.project.travel_agency.Domain;

public class Account extends Entity<Tuple<Long,String>>{
    private final String  user;
    private final String password;

    public Account(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
