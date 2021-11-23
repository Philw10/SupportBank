package training.supportbank;

public class Transaction {
    String date;
    String from;
    String to;
    String narritive;
    Float amount;

    public Transaction(String date, String from, String to, String narritive, Float amount) {
        this.date = date;  //this instance of
        this.from = from;
        this.to = to;
        this.narritive = narritive;
        this.amount = amount;
    }
}
