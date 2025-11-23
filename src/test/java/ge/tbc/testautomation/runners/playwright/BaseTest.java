package ge.tbc.testautomation.runners.playwright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    public Page page;
    
    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setTimeout(10000);
        options.setHeadless(true);
        browser = playwright.chromium().launch(options);
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }
    
    @AfterClass
    public void tearDown() {
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
