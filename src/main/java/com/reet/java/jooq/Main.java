package com.reet.java.jooq;

import com.reet.java.jooq.generated.tables.Bank;
import com.reet.java.jooq.generated.tables.Bankaccount;
import com.reet.java.jooq.generated.tables.records.BankaccountRecord;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import static com.reet.java.jooq.generated.tables.Bankaccount.BANKACCOUNT;

public class Main {

    public static void main(String[] args) throws SQLException {
        // H2 Database Connection Details
        String jdbcUrl = "jdbc:h2:~/test";
        String username = "sa";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.H2);
            Random rand = new Random();
            int upperbound = 1000000;
            int int_random = rand.nextInt(upperbound);

            // INSERT QUERY
            dslContext.insertInto(Bank.BANK)
                    .set(Bank.BANK.BANK_ID, int_random)
                    .set(Bank.BANK.BANK_NAME, "LLC")
                    .set(Bank.BANK.ADDRESS, "123 London")
                    .execute();
            dslContext.insertInto(BANKACCOUNT)
                    .set(BANKACCOUNT.BANK_ID, int_random)
                    .set(BANKACCOUNT.ACCOUNT_ID, int_random)
                    .set(BANKACCOUNT.ACCOUNT_NUMBER, "1234543210")
                    .set(BANKACCOUNT.ACCOUNT_TYPE, "SAVINGS")
                    .set(BANKACCOUNT.BALANCE, 1000d)
                    .execute();

            dslContext.selectFrom(Bank.BANK)
                    .fetch()
                    .forEach(bank -> System.out.println(bank.getBankId() + " " + bank.getBankName() + " " + bank.getAddress()));
            dslContext.selectFrom(BANKACCOUNT)
                    .fetch()
                    .forEach(bankAccount -> System.out.println(bankAccount.getBankId() + " " + bankAccount.getAccountId()
                            + " " + bankAccount.getBalance() + " " + bankAccount.getAccountNumber()
                            + " " + bankAccount.getAccountType()));

            // SELECT BY ID QUERY
            BankaccountRecord record = dslContext.selectFrom(BANKACCOUNT)
                    .where(BANKACCOUNT.BANK_ID.eq(int_random))
                    .fetchAny();

            System.out.println(record.getBankId() + " " + record.getAccountId()
                    + " " + record.getBalance() + " " + record.getAccountNumber()
                    + " " + record.getAccountType());

            // SELECT * QUERY
            Result<BankaccountRecord> records = dslContext.selectFrom(BANKACCOUNT)
                    .where(BANKACCOUNT.BANK_ID.in(int_random))
                    .fetch();
            records.forEach(bankAccount -> System.out.println(bankAccount.getBankId() + " " + bankAccount.getAccountId()
                    + " " + bankAccount.getBalance() + " " + bankAccount.getAccountNumber()
                    + " " + bankAccount.getAccountType()));

            // Build the JOIN query
            // Aliases for the tables
            Bankaccount a = BANKACCOUNT.as("a");
            Bank b = Bank.BANK.as("b");
            Result<Record4<String, Integer, String, String>> result = dslContext.select(
                            a.ACCOUNT_NUMBER, a.ACCOUNT_ID,
                            b.BANK_NAME, b.ADDRESS)
                    .from(a)
                    .join(b).on(a.BANK_ID.eq(b.BANK_ID))
                    .fetch();

            // Process the results
            for (Record4<String, Integer, String, String> r : result) {
                String accountNumber = r.get(a.ACCOUNT_NUMBER);
                Integer accountId = r.get(a.ACCOUNT_ID);
                String bankName = r.get(b.BANK_NAME);
                String address = r.get(b.ADDRESS);

                System.out.println("Account Number: " + accountNumber);
                System.out.println("Account ID: " + accountId);
                System.out.println("Bank Name: " + bankName);
                System.out.println("Address: " + address);
                System.out.println();
            }
        }
    }
}
