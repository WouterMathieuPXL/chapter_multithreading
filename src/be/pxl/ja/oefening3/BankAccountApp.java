package be.pxl.ja.oefening3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BankAccountApp {
    private static final String CONFIG_FILE = "resources/oefening3.properties";
    private static final String LOG_FILE = "resources/oefening3.log";

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Path.of(CONFIG_FILE))) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int startBalance = Integer.parseInt(properties.getProperty("account.balance"));
        int numberOfUsers = Integer.parseInt(properties.getProperty("account.users"));
        int numberOfTransactions = Integer.parseInt(properties.getProperty("user.transactions"));
        int transactionLimit = Integer.parseInt(properties.getProperty("transaction.limit"));

        BufferedWriter logger = Files.newBufferedWriter(Path.of(LOG_FILE),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        BankAccount bankAccount = new BankAccount("&%Q(&$Q*", startBalance, logger);
        User[] users = new User[numberOfUsers];
        for (int i = 0; i < numberOfUsers; i++) {
            users[i] = new User("User_" + (i + 1), bankAccount, numberOfTransactions, transactionLimit);
            users[i].start();
        }
        for (User user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        logger.close();

        List<String> userTransactions = Arrays.stream(users).map(user -> user.name + ": " + numberOfTransactions).toList();
        System.out.println(String.join(", ", userTransactions));
        System.out.println("Balance: " + bankAccount.getBalance());
        System.out.println("Start balance: " + startBalance);
        System.out.println("Total in: " + bankAccount.getTotalIn());
        System.out.println("Total out: " + bankAccount.getTotalOut());
        System.out.println("Total should be: " + (startBalance + bankAccount.getTotalIn() - bankAccount.getTotalOut()));
    }
}
