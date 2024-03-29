import org.junit.Before;
import org.junit.Test;
import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;
import ru.itmo.banks.client.ClientBuilder;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

public class DepositTransactionsTest {
    private CentralBank service;
    private Bank bank;

    @Before
    public void setup() {
        service = new CentralBank();
        HashMap<Integer, Double> interests = new HashMap<>();

        interests.put(5000, 3.0);
        interests.put(50000, 4.0);
        interests.put(10000, 5.0);

        bank = service.createBank("AlfaBank", 3, interests, 20, 30000);
    }

    @Test
    public void replenishTest() {
        var client = service.addClientToBank(new ClientBuilder().setNameAndSurname("Annemarija", "Repenko").build(), bank.getId());
        var account = service.createAccount(client, bank.getId(), "deposit", 5000);

        bank.replenishMoney(account.getId(), 10000);

        assertEquals(15000, account.getBalance());
    }

    @Test
    public void withdrawTest() {
        var client = service.addClientToBank(new ClientBuilder().setNameAndSurname("Annemarija", "Repenko").build(), bank.getId());
        var account = service.createAccount(client, bank.getId(), "deposit", 5000);

        bank.withdrawMoney(account.getId(), 30);
        assertEquals(4970, account.getBalance());
    }

    @Test
    public void transferTest() {
        var client1 = service.addClientToBank(new ClientBuilder().setNameAndSurname("Annemarija", "Repenko").build(), bank.getId());
        var account1 = service.createAccount(client1, bank.getId(), "debit", 20000);
        var client2 = service.addClientToBank(new ClientBuilder().setNameAndSurname("Ksenia", "Vasutinskaya").build(), bank.getId());
        var account2 =service.createAccount(client2, bank.getId(), "debit", 10);

        bank.transferMoney(account1.getId(), account2.getId(), 10000);

        assertEquals(10000, account1.getBalance());
        assertEquals(10010, account2.getBalance());
    }

    @Test
    public void undoTest() {
        var client = service.addClientToBank(new ClientBuilder().setNameAndSurname("Annemarija", "Repenko").build(), bank.getId());
        var account = service.createAccount(client, bank.getId(), "deposit", 5000);

        bank.withdrawMoney(account.getId(), 10);
        bank.undoTransaction(account.getId());

        assertEquals(5000, account.getBalance());
    }
}
