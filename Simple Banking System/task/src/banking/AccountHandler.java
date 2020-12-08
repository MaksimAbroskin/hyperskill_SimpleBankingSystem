package banking;

public class AccountHandler {

    static Card createAccount() {
        Card newCard = new Card();
        if (newCard.number.matches("\\d{16}")) {
            System.out.println("\nYour card has been created\n" +
                    "Your card number:\n" + newCard.getNumber());
            System.out.println("Your card PIN:\n" + newCard.getPin());
            return newCard;
        } else {
            return null;
        }
    }

}
