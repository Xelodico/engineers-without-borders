package Popup;

import BoardGame.ResourceType;
import GameSystem.GameSystem;

/**
 * The CostPopup class represents a popup window that displays a message with a
 * cost and currency.
 *
 * @author Nathan Watkins
 */
public class CostPopup extends Popup {

    /**
     * Constructs a CostPopup with the specified title, message, currency, and cost.
     *
     * @param title    the title of the popup
     * @param message  the message to be displayed in the popup
     * @param currency the currency type to be displayed
     * @param cost     the cost to be displayed
     */
    public CostPopup(String title, String message, String currency, int cost) {
        super(title, message, "Yes", "No",
                e -> {
                    // Handle Yes button action
                    System.out.println("Yes button clicked");
                }, e -> {
                    // Handle No button action
                    System.out.println("No button clicked");
                });
    }

    /**
     * Displays the popup with the specified title, message, currency, and cost.
     *
     * @param title    the title of the popup
     * @param message  the message to be displayed in the popup
     * @param currency the currency type to be displayed
     * @param cost     the cost to be displayed
     */
    public void showPopup(String title, String message, ResourceType currency, int cost) {
        setTitle(title);
        setDescription(formatMessage(message, currency, cost));
        setNoButtonAction(e -> hidePopup());
        setVisible(true);
    }

    /**
     * Hides the popup.
     */
    public void hidePopup() {
        setVisible(false);
    }

    /**
     * Formats the message to include the cost and currency.
     *
     * @param message  the base message
     * @param currency the currency type
     * @param cost     the cost
     * @return the formatted message
     */
    private String formatMessage(String message, ResourceType currency, int cost) {

        int currentAmount = 0;
        if (currency == ResourceType.MONEY) {
            currentAmount = GameSystem.getPlayerAt().getMoney();
        } else {
            currentAmount = GameSystem.getPlayerAt().getResource(currency);
        }

        return message + cost + " " + currency.toString() + "?\n You currently have " + currentAmount + " " + currency
                + ".";
    }
}