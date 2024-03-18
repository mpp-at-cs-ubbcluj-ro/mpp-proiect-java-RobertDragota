package Test_Repo_Clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.travel_agency.Domain.Client;
import org.project.travel_agency.Repository.DB_Repository.Repo_Client;
import org.project.travel_agency.Utility.DB_Utils;

import java.util.Optional;

public class Tests {
    static private final DB_Utils DB_connection = new DB_Utils("jdbc:postgresql://localhost:5432/Travle_Agency", "robert12", "Asmodeus011235");
    static private final Repo_Client clientRepository = new Repo_Client(DB_connection);

    @Test
    @DisplayName("Add client test")
    public void testAdd() {
        Client client = new Client("Robert", "dragotarobert10@gmail.com");

        Optional<Client> addedClient = clientRepository.add(client);

        Assertions.assertTrue(addedClient.isPresent(), "Client should be added");

        Client actualClient = addedClient.get();

        Assertions.assertEquals("Robert", actualClient.getName(), "Client name should match");
        Assertions.assertEquals("dragotarobert10@gmail.com", actualClient.getEmail(), "Client email should match");
        clientRepository.delete(actualClient);
    }

    @Test
    @DisplayName("Delete client test")
    public void testDelete() {
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        client=clientRepository.add(client).isPresent()?clientRepository.add(client).get():null;

        assert client != null;
        Optional<Client> deletedClient = clientRepository.delete(client);

        Assertions.assertTrue(deletedClient.isPresent(), "Client should be deleted");

        Optional<Client> foundClient = clientRepository.find(client.getId());
        Assertions.assertFalse(foundClient.isPresent(), "Deleted client should not be found");
    }


    @Test
    @DisplayName("Update client test")
    public void testUpdate() {
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        clientRepository.add(client);
        client=clientRepository.add(client).isPresent()?clientRepository.add(client).get():null;

        assert client != null;
        client.setName("Updated Name");
        client.setEmail("updated_email@gmail.com");
        Optional<Client> updatedClient = clientRepository.update(client);

        Assertions.assertTrue(updatedClient.isPresent(), "Client should be updated");

        Client actualClient = updatedClient.get();
        Assertions.assertEquals("Updated Name", actualClient.getName(), "Client name should be updated");
        Assertions.assertEquals("updated_email@gmail.com", actualClient.getEmail(), "Client email should be updated");
        clientRepository.delete(client);
    }


    @Test
    @DisplayName("Find client test")
    public void testFind() {
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        clientRepository.add(client);
        client=clientRepository.add(client).isPresent()?clientRepository.add(client).get():null;

        assert client != null;
        Optional<Client> foundClient = clientRepository.find(client.getId());

        Assertions.assertTrue(foundClient.isPresent(), "Client should be found");

        Client actualClient = foundClient.get();
        Assertions.assertEquals("Robert", actualClient.getName(), "Found client name should match");
        Assertions.assertEquals("dragotarobert10@gmail.com", actualClient.getEmail(), "Found client email should match");
        clientRepository.delete(client);
    }

}
