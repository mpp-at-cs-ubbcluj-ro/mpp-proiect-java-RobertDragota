package org.project.travel_agency.Domain;

public class Account extends Entity<Tuple<Long,String>>{
    private  Client  client;
    private  String password;

    public Account(Client client, String password) {
        this.client= client;
        this.password = password;
        super.setId(new Tuple<>(client.getId(),password));
    }

    public Client getClient() {
        return client;
    }

    public String getPassword() {
        return password;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
