package banking;

import java.util.Random;

public class Card {
    final String BIN = "400000";
    static int cnt;
    int id;
    String number;
    String pin;
    Integer balance;

    // TODO algorithm of calculation id
    public Card() {
        cnt++;
        this.id = cnt;
        setNumber();
        setPin();
        setBalance(0);
    }

    public Card(int id, String number, String pin, Integer balance) {
        this.id = id;
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }

    public void setNumber() {
        Random random = new Random();
        int accountId = random.nextInt(1_000_000_000); // Account Identifier
        String noChecksum = BIN + String.format("%09d", accountId);
        int checksum = generateChecksum(noChecksum);
        this.number = noChecksum + checksum;
    }

    public void setPin() {
        Random random = new Random();
        this.pin = String.format("%04d", random.nextInt(10_000));
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
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

    public static int generateChecksum(String number) {
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
        return (sum % 10 != 0) ? 10 - sum % 10 : 0;
    }

    public static boolean isCorrectCardNumber(String number) {
        if (!number.matches("\\d{16}")) {
            return false;
        }
        return Character.getNumericValue(number.charAt(15)) == generateChecksum(number.substring(0, 15));
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                "number='" + number + '\'' +
                ", pin=" + pin +
                ", balance=" + balance +
                '}';
    }
}
