package training.supportbank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
          public static void main(String[] args) {
              ArrayList<Transaction> transactions = new ArrayList<>();
              Accounts funds = new Accounts();
              Scanner scanner = new Scanner(System.in);
              String option = null;

              try {
                  File myObj = new File("Transactions2014.csv");
                  Scanner myReader = new Scanner(myObj);
                  myReader.nextLine();
                  while (myReader.hasNextLine()) {
                      String data = myReader.nextLine();

                      Scanner commer = new Scanner(data);
                      commer.useDelimiter(",");
                      String date = commer.next();
                      String from = commer.next();
                      String to = commer.next();
                      String narritive = commer.next();
                      String amount = commer.next();

                      Transaction newImport= new Transaction(date, from, to, narritive, Float.parseFloat(amount));

                      transactions.add(newImport);
                  }
                  myReader.close();
              }  catch (FileNotFoundException e) {
                  System.out.println("An error occurred.");
                  e.printStackTrace();
              }

              for (Transaction transaction : transactions) {
                  funds.doesAccountExist(transaction.to);
                  if (funds.returnAccountStatus()) {   // ADD MONEY TO ACCOUNT
                      funds.moneyIn(transaction.to, transaction.amount);
                  } else {
                      funds.createAccountWithDeposit(transaction.to, transaction.amount);
                  }
                  funds.doesAccountExist(transaction.from);
                  if (funds.returnAccountStatus()) { // TAKE MONEY FROM ACCOUNT
                      funds.moneyOut(transaction.from, transaction.amount);
                  } else {
                      funds.createAccountWithDeduction(transaction.from, transaction.amount);
                  }
              }

              while (!Objects.equals(option, "E")){
                  System.out.println("What would you like to do? 1.List All, 2.List [Account name]");
                  option = scanner.nextLine();
                  String[] split = option.split(" ", 2);
                  String checkAll = (split[0]);
                  String chosenName = (split[1]);
                  if (checkAll.equals("List") && chosenName.equals("All")) {
                      funds.printAccounts();
                  }else if (checkAll.equals("List")){
                      for (Transaction transaction : transactions) {
                          if ((chosenName.equals(transaction.from)) || (chosenName.equals(transaction.to))){
                              System.out.println(transaction.date + "   From: " + transaction.from + "   To: " + transaction.to + "   For: " + transaction.narritive + "   Amount: £" + transaction.amount);
                          }
                      }
                  }

              }

          }


}
