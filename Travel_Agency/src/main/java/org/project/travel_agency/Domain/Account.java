package org.project.travel_agency.Domain;

public class Account extends Entity<Tuple<Long,String>>{
    private final Client  client;
    private final String password;

    public Account(Client client, String password) {
        this.client= client;
        this.password = password;
        super.setId(new Tuple<>(client.getId(),password));
    }
}
