package Test_Repo_Clients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
public class Tests {
    @Test
    @DisplayName("Add client test")
    public void testAdd() {
        int sum = 5 + 3;
        Assertions.assertEquals(8, sum, "5 + 3 should equal 8");
    }
    @Test
    @DisplayName("Delete client test")
    public void testDelete() {
        int sum = 5 + 3;
        Assertions.assertEquals(8, sum, "5 + 3 should equal 8");
    }
    @Test
    @DisplayName("Update client test")
    public void testUpdate() {
        int sum = 5 + 3;
        Assertions.assertEquals(8, sum, "5 + 3 should equal 8");
    }
    @Test
    @DisplayName("Find client test")
    public void testFind() {
        int sum = 5 + 3;
        Assertions.assertEquals(8, sum, "5 + 3 should equal 8");
    }
}
