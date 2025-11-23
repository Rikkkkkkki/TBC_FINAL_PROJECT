package ge.tbc.testautomation.tests.playwright;

import ge.tbc.testautomation.playwright.data.Constants;
import ge.tbc.testautomation.playwright.steps.OffersSteps;
import ge.tbc.testautomation.playwright.util.ScreenshotUtils;
import ge.tbc.testautomation.runners.playwright.BaseTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OffersTest extends BaseTest {
    OffersSteps offerSteps;

    @BeforeMethod
    public void createContext() {
        offerSteps = new OffersSteps(page);
        offerSteps.goToTbc()
                .acceptCookies()
                .hoverNavItem()
                .clickOffersBtn()
                .goToOffers();
    }

    @Test(description = "KAN-T3: Applying and removing filters on Offers page")
    public void verifyApplyingAndRemovingFilters() {
        offerSteps.selectSubType(Constants.SUBTYPE_NAME)
                .removeFilters();

        offerSteps.applyFilter(Constants.FILTER_NAME)
                .verifyOffersMatchFilter(Constants.FILTER_NAME);

        ScreenshotUtils.takeScreenshot(page, "after_applying_filter");

        offerSteps.removeFilters()
                .verifyFiltersRemoved(Constants.FILTER_NAME);

        ScreenshotUtils.takeScreenshot(page, "after_removing_filter");
    }

    @AfterMethod
    public void cleanup(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtils.takeScreenshot(page, "FAILED_" + result.getName());
        }
    }
}
