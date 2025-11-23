package ge.tbc.testautomation.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoanAmountPage {
    public final Locator amountTab,
            loanAmountInput,
            loanPeriodInput,
            monthlyPayments,
            baseInterestRate,
            effectiveInterestRate;

    public LoanAmountPage(Page page) {
        amountTab = page.locator("//span[text()='By Amount']");

        loanAmountInput = page.locator("input.input[type='number'][min='500'][max='150000']");
        loanPeriodInput = page.locator("input.input[type='number'][min='6'][max='72']");

        monthlyPayments = page.locator(".tbcx-pw-calculated-info__top-title").first();
        baseInterestRate = page.locator(".tbcx-pw-calculated-info__rows-item").nth(1).locator(".tbcx-pw-calculated-info__rows-item-info").first();
        effectiveInterestRate = page.locator(".tbcx-pw-calculated-info__rows-item").nth(1).locator(".tbcx-pw-calculated-info__rows-item-info").last();
    }
}