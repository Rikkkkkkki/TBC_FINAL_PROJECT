package ge.tbc.testautomation.playwright.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.playwright.pages.LoanPage;
import ge.tbc.testautomation.playwright.util.HelperMethods;
import io.qameta.allure.Step;


public class LoanSteps extends BaseSteps<LoanSteps>{
    LoanPage loanPage;

    public LoanSteps(Page page) {
        super(page);
        loanPage = new LoanPage(page);
    }

    @Step("Open the 'By Income' loan calculator tab")
    public LoanSteps openByIncomeTab() {
        loanPage.incomeTab.click();
        return this;
    }

    @Step("Enter monthly income: {0} ₾")
    public LoanSteps enterIncome(String incomeAmount) {
        loanPage.incomeInput.fill(incomeAmount);
        return this;
    }

    @Step("Enter loan period: {0} months")
    public LoanSteps enterLoanPeriod(String months) {
        loanPage.incomeMonthInput.fill(months);
        return this;
    }
    
    @Step("Check that loan period is displayed Correctly")
    public LoanSteps checkLoanPeriod(String months) {
        PlaywrightAssertions.assertThat(loanPage.monthLabel).containsText(months);
        return this;
    }


    @Step("Check That the monthly contribution is calculated and displayed correctly")
    public LoanSteps checkMonthlyContribution(String amount) {
        double incomeValue=Double.parseDouble(amount);
        double expectedContribution = incomeValue < 1500 ? incomeValue * 0.25 : incomeValue * 0.50;
        String formattedExpected = HelperMethods.formatMoney(expectedContribution);
        PlaywrightAssertions.assertThat(loanPage.monthlyPayments).containsText(String.valueOf(formattedExpected));
        return this;
    }
    
    @Step("Check that the base interest rate is displayed correctly")
    public LoanSteps checkBaseInterestRate(String expectedBaseInterest) {
        PlaywrightAssertions.assertThat(loanPage.baseInterestRate)
                .containsText(expectedBaseInterest);
        PlaywrightAssertions.assertThat(loanPage.baseInterestRate).isVisible();
        return this;
    }
    
    @Step("Check that the effective interest rate is displayed correctly")
    public LoanSteps checkEffectiveInterestRate(String expectedEffectiveInterest) {
        PlaywrightAssertions.assertThat(loanPage.effectiveInterestRate)
                .containsText(expectedEffectiveInterest);
        PlaywrightAssertions.assertThat(loanPage.effectiveInterestRate)
                .isVisible();
        return this;
    }
}
