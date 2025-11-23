package ge.tbc.testautomation.tests.playwright;

import ge.tbc.testautomation.playwright.data.Constants;
import ge.tbc.testautomation.playwright.steps.LoanSteps;
import ge.tbc.testautomation.runners.playwright.BaseTest;
import jdk.jfr.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Description("Calculate Monthly Payment (By Income Mode)[KAN-T6]")
public class IncomeLoanTest extends BaseTest {
    LoanSteps loanSteps;

    @BeforeClass
    public void SetUp() {
        page.navigate(Constants.TBC_LOAN_URL);
        loanSteps = new LoanSteps(page);
    }

    @Test
    public void incomeLoanTest() {
        loanSteps.openByIncomeTab().
                enterIncome(Constants.TBC_LOAN_INCOME).
                enterLoanPeriod(Constants.TBC_LOAN_PERIOD).
                checkLoanPeriod(Constants.TBC_LOAN_PERIOD).
                checkMonthlyContribution(Constants.TBC_LOAN_INCOME).
                checkBaseInterestRate(Constants.BASE_LOAN_INTEREST_RATE).
                checkEffectiveInterestRate(Constants.EFFECTIVE_LOAN_INTEREST);
    }
}
