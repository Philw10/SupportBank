package training.supportbank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Bank {
    String name;
    Float amount;
    Float total;
    Boolean accountExists;
    Hashtable<String, Float> accounts = new Hashtable<>();

    public Bank() {
        accountExists = false;
    }

    public void printAccounts() {
        Iterator<Map.Entry<String, Float>> itr = accounts.entrySet().iterator();

        Map.Entry<String, Float> entry;
        while (itr.hasNext()) {
            entry = itr.next();
            System.out.println(entry.getKey() + " = £" + entry.getValue());
        }

    }

    public boolean returnAccountStatus() {
        return accountExists;
    }

    public void doesAccountExist(String name) {
        accountExists = accounts.containsKey(name);
    }

    public void createAccount(String name) {
        this.name = name;
        this.amount = 0.0f;
        accounts.put(name, this.amount);
    }

    public void moneyIn(String name, Float amount) {
        this.name = name;
        this.amount = amount;
        this.total = accounts.get(name) + amount;
        Float f = Bank.roundFloat(total);
        accounts.put(name, f);
    }


    public void moneyOut(String name, Float amount) {
        this.name = name;
        this.amount = amount;
        this.total = accounts.get(name) - amount;
        Float f = Bank.roundFloat(total);
        accounts.put(name, f);
    }

    private static float roundFloat(Float f) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }
}





