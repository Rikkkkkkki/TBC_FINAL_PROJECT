package ge.tbc.testautomation.playwright.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.playwright.data.Constants;
import ge.tbc.testautomation.playwright.pages.BasePage;
import io.qameta.allure.Step;

public class BaseSteps<T extends BaseSteps<T>> {
    BasePage basePage;
    Page page;

    public BaseSteps(Page page) {
        this.basePage = new BasePage(page);
        this.page = page;
    }

    @Step("Hover on personal nav item for megamenu")
    public T hoverNavItem() {
        basePage.personalNavItem.hover();

        return (T) this;
    }

    @Step("Navigate to offers through megamenu")
    public T clickOffersBtn() {
        basePage.offersBtn.click();

        return (T) this;
    }

    @Step("Navigate to tbc's website")
    public T goToTbc() {
        page.navigate(Constants.TBC_URL);

        return (T) this;
    }

    @Step("Accept cookies")
    public T acceptCookies() {
        basePage.acceptCookiesBtn.click();

        return (T) this;
    }
}
