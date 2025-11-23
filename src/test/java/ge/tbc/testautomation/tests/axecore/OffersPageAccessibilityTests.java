package ge.tbc.testautomation.tests.axecore;

import com.deque.html.axecore.playwright.AxeBuilder;
import ge.tbc.testautomation.playwright.steps.OffersSteps;
import ge.tbc.testautomation.runners.playwright.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OffersPageAccessibilityTests extends BaseTest {
    OffersSteps steps;
    AxeBuilder builder;

    @BeforeClass
    public void navigateToOffers() {
        steps = new OffersSteps(page);

        steps.goToTbc()
                .acceptCookies()
                .hoverNavItem()
                .clickOffersBtn()
                .goToOffers();

        builder = new AxeBuilder(page);
    }

    @Test
    @Description("Perform audit by axe core for accessibility of offers page")
    public void axeCoreAuditTest() {
        try {
            builder.analyze();
        } catch (RuntimeException e) {
            Assert.fail("Axe audit returned with too many issues");
        }
    }
}
