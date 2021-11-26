package training.supportbank;

public class Transaction {
    String date;
    String from;
    String to;
    String narrative;
    Float amount;

    public Transaction(String date, String from, String to, String narrative, Float amount) {
        this.date = date;  //this instance of
        this.from = from;
        this.to = to;
        this.narrative = narrative;
        this.amount = amount;
    }
}
