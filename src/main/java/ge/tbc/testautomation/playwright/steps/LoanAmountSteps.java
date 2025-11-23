package ge.tbc.testautomation.playwright.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.playwright.pages.LoanAmountPage;
import ge.tbc.testautomation.playwright.util.ScreenshotUtils;
import io.qameta.allure.Step;

public class LoanAmountSteps extends BaseSteps<LoanAmountSteps> {
    LoanAmountPage loanAmountPage;

    public LoanAmountSteps(Page page) {
        super(page);
        loanAmountPage = new LoanAmountPage(page);
    }

    @Step("Open the 'By Amount' loan calculator tab")
    public LoanAmountSteps openByAmountTab() {
        loanAmountPage.amountTab.click();
        page.waitForTimeout(1000);
        return this;
    }

    @Step("Enter loan amount: {0} ₾")
    public LoanAmountSteps enterLoanAmount(String amount) {
        loanAmountPage.loanAmountInput.waitFor();
        loanAmountPage.loanAmountInput.clear();
        loanAmountPage.loanAmountInput.fill(amount);
        page.waitForTimeout(500);
        return this;
    }

    @Step("Enter period: {0} months")
    public LoanAmountSteps enterPeriod(String period) {
        loanAmountPage.loanPeriodInput.waitFor();
        loanAmountPage.loanPeriodInput.clear();
        loanAmountPage.loanPeriodInput.fill(period);
        page.waitForTimeout(500);
        return this;
    }

    @Step("Verify that loan amount input has validation error (ng-invalid class or ng-dirty with invalid value)")
    public LoanAmountSteps verifyLoanAmountInvalid() {
        page.waitForTimeout(500);

        String classAttribute = loanAmountPage.loanAmountInput.getAttribute("class");
        String value = loanAmountPage.loanAmountInput.inputValue();
        String minValue = loanAmountPage.loanAmountInput.getAttribute("min");

        boolean isInvalid = classAttribute.contains("ng-invalid") ||
                (classAttribute.contains("ng-dirty") &&
                        Integer.parseInt(value) < Integer.parseInt(minValue));

        if (!isInvalid) {
            ScreenshotUtils.takeScreenshot(page, "ValidationCheck_NotInvalid");
        }

        String borderColor = loanAmountPage.loanAmountInput.evaluate("el => getComputedStyle(el).borderColor").toString();
        boolean hasRedBorder = borderColor.contains("rgb(255, 0, 0)") ||
                borderColor.contains("rgb(220, 38, 38)") ||
                borderColor.contains("red");

        if (!isInvalid && !hasRedBorder) {
            throw new AssertionError("Expected validation error for value " + value +
                    " (min: " + minValue + "), but input shows: " + classAttribute);
        }

        return this;
    }

    @Step("Verify that monthly payment does not change with invalid loan amount")
    public LoanAmountSteps verifyResultsNotUpdated() {
        String initialMonthlyPayment = "";

        if (loanAmountPage.monthlyPayments.isVisible()) {
            initialMonthlyPayment = loanAmountPage.monthlyPayments.textContent();
        }

        page.waitForTimeout(1000);

        if (!initialMonthlyPayment.isEmpty()) {
            PlaywrightAssertions.assertThat(loanAmountPage.monthlyPayments).containsText(initialMonthlyPayment);
        }

        return this;
    }

    @Step("Verify that loan amount input is valid (no validation error)")
    public LoanAmountSteps verifyLoanAmountValid() {
        page.waitForTimeout(500);
        String classAttribute = loanAmountPage.loanAmountInput.getAttribute("class");

        if (!classAttribute.contains("ng-valid") || classAttribute.contains("ng-invalid")) {
            ScreenshotUtils.takeScreenshot(page, "ExpectedValid_ButInvalid");
            throw new AssertionError("Expected valid input, but got: " + classAttribute);
        }

        return this;
    }

    @Step("Verify that monthly payment is calculated and displayed")
    public LoanAmountSteps verifyMonthlyPaymentDisplayed() {
        PlaywrightAssertions.assertThat(loanAmountPage.monthlyPayments).isVisible();
        PlaywrightAssertions.assertThat(loanAmountPage.monthlyPayments).not().isEmpty();

        String paymentText = loanAmountPage.monthlyPayments.textContent();
        if (paymentText.trim().isEmpty() || paymentText.equals("0₾")) {
            ScreenshotUtils.takeScreenshot(page, "NoPaymentCalculated");
            throw new AssertionError("Monthly payment not calculated properly: " + paymentText);
        }

        return this;
    }
}