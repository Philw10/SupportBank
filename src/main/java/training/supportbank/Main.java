package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.error("File Started to run");
        Scanner inputScanner = new Scanner(System.in);
        String option = null;

        List<Transaction> transactions = getTransactions();
        Bank accountTransaction = runTransactions(transactions);

        while (!Objects.equals(option, "E")) {
            System.out.println("What would you like to do? 1.List All, 2.List [Account name]");
            option = inputScanner.nextLine();
            String[] split = option.split(" ", 2);
            String checkAll = (split[0]);
            String chosenName = (split[1]);
            if (checkAll.equals("List") && chosenName.equals("All")) {
                accountTransaction.printAccounts();
            } else if (checkAll.equals("List")) {
                for (Transaction transaction : transactions) {
                    if ((chosenName.equals(transaction.from)) || (chosenName.equals(transaction.to))) {
                        System.out.println(transaction.date + "   From: " + transaction.from + "   To: " + transaction.to + "   For: " + transaction.narrative + "   Amount: £" + transaction.amount);
                    }
                }
            }
        }
    }

    private static List<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Integer lineBeingRead = 2;
        try {
            File file = new File("DodgyTransactions2015.csv");
            Scanner fileScanner = new Scanner(file);

            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {

                String data = fileScanner.nextLine();
                try {
                    Scanner comma = new Scanner(data);
                    comma.useDelimiter(",");
                    String date = comma.next();
                    String from = comma.next();
                    String to = comma.next();
                    String narrative = comma.next();
                    String amount = comma.next();

                    Transaction newImport = new Transaction(date, from, to, narrative, Float.parseFloat(amount));

                    transactions.add(newImport);
                    lineBeingRead = lineBeingRead + 1;
                } catch (Exception x) {
                    System.out.println("An error occurred on line " + lineBeingRead + " of the document. Error as follows:-");
                    System.out.println(x + ".  This line has been excluded from the accounts.");
                    LOGGER.error(x + " error on line " + lineBeingRead + " of CSV");
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            LOGGER.error("Error occurred", e);
            e.printStackTrace();
        }
        LOGGER.error("File import complete");
        return transactions;
    }

    private static Bank runTransactions(List<Transaction> transactions) {
        Bank accountTransaction = new Bank();

        for (Transaction transaction : transactions) {
            accountTransaction.doesAccountExist(transaction.to);
            if (accountTransaction.returnAccountStatus()) {   // ADD MONEY TO ACCOUNT
                accountTransaction.moneyIn(transaction.to, transaction.amount);
            } else {
                accountTransaction.createAccount(transaction.to);
                accountTransaction.moneyIn(transaction.to, transaction.amount);
            }
            accountTransaction.doesAccountExist(transaction.from);
            if (accountTransaction.returnAccountStatus()) { // TAKE MONEY FROM ACCOUNT
                accountTransaction.moneyOut(transaction.from, transaction.amount);
            } else {
                accountTransaction.createAccount(transaction.from);
                accountTransaction.moneyOut(transaction.from, transaction.amount);
            }
        }
        LOGGER.error("Account transactions complete");
        return accountTransaction;
    }

}
