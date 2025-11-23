package ge.tbc.testautomation.tests.playwright;

import ge.tbc.testautomation.playwright.data.Constants;
import ge.tbc.testautomation.playwright.steps.LoanAmountSteps;
import ge.tbc.testautomation.playwright.util.ScreenshotUtils;
import ge.tbc.testautomation.runners.playwright.BaseTest;
import jdk.jfr.Description;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Description("Validation for Out-of-Range Loan Amount (Based on 'By Amount' Mode) [KAN-T7]")
public class LoanAmountValidationTest extends BaseTest {
    LoanAmountSteps loanAmountSteps;

    @BeforeClass
    public void SetUp() {
        page.navigate(Constants.TBC_LOAN_URL);
        loanAmountSteps = new LoanAmountSteps(page);
    }

    @Test(description = "Verify that the calculator highlights loan amounts below the minimum and prevents calculation")
    public void validateOutOfRangeLoanAmount() {
        loanAmountSteps.openByAmountTab().
                enterPeriod(Constants.LOAN_PERIOD_12).
                enterLoanAmount(Constants.INVALID_LOAN_AMOUNT).
                verifyLoanAmountInvalid().
                verifyResultsNotUpdated();

        ScreenshotUtils.takeScreenshot(page, "InvalidLoanAmount_150GEL");

        loanAmountSteps.enterLoanAmount(Constants.VALID_MIN_LOAN_AMOUNT).
                verifyLoanAmountValid().
                verifyMonthlyPaymentDisplayed();

        ScreenshotUtils.takeScreenshot(page, "ValidLoanAmount_1000GEL");
    }

    @AfterMethod
    public void cleanup(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtils.takeScreenshot(page, "FAILED_" + result.getName());
        }
    }
}
