import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class CurrencyConverterGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setBounds(30, 30, 100, 25);
        frame.add(amountLabel);
        JTextField amountField = new JTextField();
        amountField.setBounds(150, 30, 150, 25);
        frame.add(amountField);
        String[] currencies = {
                "INR", "USD", "EUR", "GBP", "JPY", "CAD", "AUD",
                "CHF", "CNY", "NZD", "ZAR", "SGD", "AED", "RUB", "SEK", "NOK",
                "BRL", "MXN", "KRW", "THB", "MYR", "IDR", "PHP", "EGP", "TRY", "SAR"
        };

        JComboBox<String> fromBox = new JComboBox<>(currencies);
        fromBox.setBounds(30, 70, 100, 25);
        frame.add(fromBox);
        JComboBox<String> toBox = new JComboBox<>(currencies);
        toBox.setBounds(150, 70, 100, 25);
        frame.add(toBox);
        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(30, 110, 100, 30);
        frame.add(convertButton);
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setBounds(30, 160, 300, 25);
        frame.add(resultLabel);
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    String from = (String) fromBox.getSelectedItem();
                    String to = (String) toBox.getSelectedItem();
                    double rate = getExchangeRate(from, to);
                    double result = amount * rate;
                    resultLabel.setText(amount + " " + from + " = " + String.format("%.2f", result) + " " + to);
                } catch (Exception ex) {
                    resultLabel.setText("Invalid input. Please enter a number.");
                }
            }
        });
        frame.setVisible(true);
    }
    public static double getExchangeRate(String from, String to) {
        try {
            String apiKey = "f7b4a86c900760845666ad01"; // <-- Replace this with your actual API key
            String urlStr = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to;
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject json = new JSONObject(response.toString());
            if (json.getString("result").equals("success")) {
                return json.getDouble("conversion_rate");
            } else {
                throw new Exception("API result error");
            }

        } catch (Exception e) {
            System.out.println("API failed: " + e.getMessage());
            return getFallbackRate(from, to);
        }
    }
    public static double getFallbackRate(String from, String to) {
        if (from.equals(to)) return 1.0;
        switch (from + "-" + to) {
            case "USD-INR": return 83.2;
            case "INR-USD": return 1 / 83.2;
            case "EUR-INR": return 89.5;
            case "INR-EUR": return 1 / 89.5;
            case "GBP-INR": return 104.1;
            case "INR-GBP": return 1 / 104.1;
            case "USD-EUR": return 0.93;
            case "EUR-USD": return 1.08;
            case "USD-JPY": return 155.3;
            case "JPY-USD": return 1 / 155.3;
            case "INR-JPY": return 1.87;
            case "JPY-INR": return 1 / 1.87;
            case "USD-CAD": return 1.36;
            case "CAD-USD": return 1 / 1.36;
            case "USD-AUD": return 1.49;
            case "AUD-USD": return 1 / 1.49;
            case "USD-CHF": return 0.91;
            case "CHF-USD": return 1 / 0.91;
            case "USD-CNY": return 7.24;
            case "CNY-USD": return 1 / 7.24;
            case "USD-NZD": return 1.62;
            case "NZD-USD": return 1 / 1.62;
            case "USD-ZAR": return 18.5;
            case "ZAR-USD": return 1 / 18.5;
            case "USD-SGD": return 1.35;
            case "SGD-USD": return 1 / 1.35;
            case "USD-AED": return 3.67;
            case "AED-USD": return 1 / 3.67;
            case "USD-RUB": return 89.5;
            case "RUB-USD": return 1 / 89.5;
            case "USD-SEK": return 10.4;
            case "SEK-USD": return 1 / 10.4;
            case "USD-NOK": return 10.7;
            case "NOK-USD": return 1 / 10.7;
            case "INR-CAD": return 0.016;
            case "CAD-INR": return 1 / 0.016;
            case "INR-AUD": return 0.018;
            case "AUD-INR": return 1 / 0.018;
            case "INR-CHF": return 0.011;
            case "CHF-INR": return 1 / 0.011;
            case "INR-CNY": return 0.087;
            case "CNY-INR": return 1 / 0.087;
            case "INR-NZD": return 0.020;
            case "NZD-INR": return 1 / 0.020;
            case "INR-ZAR": return 0.045;
            case "ZAR-INR": return 1 / 0.045;
            case "INR-SGD": return 0.016;
            case "SGD-INR": return 1 / 0.016;
            case "INR-AED": return 0.044;
            case "AED-INR": return 1 / 0.044;
            case "INR-RUB": return 1.1;
            case "RUB-INR": return 1 / 1.1;
            case "INR-SEK": return 0.12;
            case "SEK-INR": return 1 / 0.12;
            case "INR-NOK": return 0.11;
            case "NOK-INR": return 1 / 0.11;
            case "USD-BRL": return 5.2;
            case "BRL-USD": return 1 / 5.2;
            case "USD-MXN": return 18.3;
            case "MXN-USD": return 1 / 18.3;
            case "USD-KRW": return 1370.5;
            case "KRW-USD": return 1 / 1370.5;
            case "USD-THB": return 36.2;
            case "THB-USD": return 1 / 36.2;
            case "USD-MYR": return 4.7;
            case "MYR-USD": return 1 / 4.7;
            case "USD-IDR": return 16000.0;
            case "IDR-USD": return 1 / 16000.0;
            case "USD-PHP": return 56.2;
            case "PHP-USD": return 1 / 56.2;
            case "USD-EGP": return 47.3;
            case "EGP-USD": return 1 / 47.3;
            case "USD-TRY": return 32.9;
            case "TRY-USD": return 1 / 32.9;
            case "USD-SAR": return 3.75;
            case "SAR-USD": return 1 / 3.75;
            default: return 1.0;
        }
    }
}
