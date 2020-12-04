package banking;

import java.util.Random;

public class Card {
    String number;
    Integer pin;
    Integer balance;
    String bin; //Bank Identification Number

    public Card(String BIN) {
        if (BIN.matches("\\d{6}")) {
            this.bin = BIN;
        } else {
            System.out.println("Incorrect BIN! It must contain exactly six digits");
            this.bin = null;
            return;
        }
        setNumber();
        setPin();
        setBalance(0);
    }

    public void setNumber() {
        Random random = new Random();
        int accountId = random.nextInt(900_000_000) + 100_000_000; // Account Identifier
        int checksum = random.nextInt(10);
        this.number = this.bin + accountId + checksum;
    }

    public void setPin() {
        Random random = new Random();
        this.pin = random.nextInt(9_000) + 1000;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public Integer getPin() {
        return pin;
    }

    public Integer getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number='" + number + '\'' +
                ", pin=" + pin +
                ", balance=" + balance +
                '}';
    }
}
