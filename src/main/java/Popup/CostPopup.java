package Popup;

public class CostPopup extends Popup {

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

    public void showPopup(String title, String message, String currency, int cost) {
        setTitle(title);
        setDescription(formatMessage(message, currency, cost));
        setNoButtonAction(e -> hidePopup());
        setVisible(true);
    }

    public void hidePopup() {
        setVisible(false);
    }

    private String formatMessage(String message, String currency, int cost) {
        return message + cost + " " + currency + "?";
    }

}
