package banking;

import java.util.Random;

public class Card {
    String number;
    String pin;
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
        int accountId = random.nextInt(1_000_000_000); // Account Identifier
        int checksum = generateChecksum(bin + accountId);
        this.number = this.bin + String.format("%09d", accountId) + checksum;
    }

    public void setPin() {
        Random random = new Random();
        this.pin = String.format("%04d", random.nextInt(10_000));
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public Integer getBalance() {
        return balance;
    }

    public int generateChecksum(String number) {
        char[] chDigits = number.toCharArray();
        int[] digits = new int[chDigits.length];
        int sum = 0;

        for (int i = 0; i < chDigits.length; i++) {
            digits[i] = Character.getNumericValue(chDigits[i]);
            if (i % 2 == 0) {
                digits[i] *= 2;
                if (digits[i] > 9) {
                    digits[i] -= 9;
                }
            }
            sum += digits[i];
        }
        return 10 - sum % 10;
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
