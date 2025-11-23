package ge.tbc.testautomation.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BasePage {
    public final Locator personalNavItem;
    public final Locator offersBtn;
    public final Locator acceptCookiesBtn;
    
    public BasePage(Page page) {
        personalNavItem = page.locator("//a//child::div[text()=' Personal ']");
        offersBtn = page.locator("//div[@class='tbcx-pw-mega-menu-quick-acitons-item__content']" +
                "//child::span[text()=' Offers']").first(); 
        acceptCookiesBtn = page.locator("//div[@class='tbcx-pw-cookie-consent__actions']//button").first();
    }
}
