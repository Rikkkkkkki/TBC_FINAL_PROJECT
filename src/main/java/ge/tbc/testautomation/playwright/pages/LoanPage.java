package ge.tbc.testautomation.playwright.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoanPage {
    public final Locator incomeTab,
            incomeMonthInput,
            incomeInput,
            finalAmount,
            monthLabel,
            monthlyPayments,
            baseInterestRate,
            effectiveInterestRate;

    public LoanPage(Page page) {
        incomeTab=page.locator("//span[text()='By Income']");
        incomeInput=page.locator("//input[@class='input ng-untouched ng-pristine ng-valid']").first();
        incomeMonthInput=page.locator("//input[@class='input ng-untouched ng-pristine ng-valid']").last();
        finalAmount=page.locator("//div[@class='tbcx-pw-calculated-info__top-title ng-tns-c3805417866-5 ng-star-inserted']");
        monthLabel=page.locator("//div[normalize-space(text())='Period']/following-sibling::div");
        monthlyPayments=page.locator("//div[normalize-space(text())='Monthly contribution']/following-sibling::div");
        baseInterestRate=page.locator("//div[normalize-space(text())='Interest rate']/following-sibling::div");
        effectiveInterestRate=page.locator("//div[normalize-space(text())='Effective Interest rate']/following-sibling::div");
    }
}
