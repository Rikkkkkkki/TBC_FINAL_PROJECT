package ge.tbc.testautomation.playwright.util;

import java.text.DecimalFormat;

public class HelperMethods {
    public static String formatMoney(double amount) {
        if (amount == Math.floor(amount)) {
            DecimalFormat df = new DecimalFormat("#,##0");
            return df.format(amount) + "₾";
        } else {
            double truncated = Math.floor(amount * 100) / 100.0;
            DecimalFormat df = new DecimalFormat("#,##0.##");
            return df.format(truncated) + "₾";
        }
    }
}
