package ge.tbc.testautomation.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OffersPage {
    public final Locator readMoreBtn;
    public final Locator resetButton;
    public final Locator offers;
    public final Locator offerBadges;
    public final Locator offerTitle;

    public OffersPage(Page page) {
        this.readMoreBtn = page.locator("a[href='/en/offers/all-offers?segment=All&filters=ProductType!TBCCard,TBCCardConcept'] button")
                .first();
        this.resetButton = page.locator("button.filter__button");
        this.offers = page.locator("app-marketing-list a tbcx-pw-card");
        this.offerBadges = page.locator("app-marketing-list a tbcx-pw-card tbcx-pw-text-badge");
        this.offerTitle = page.locator("app-marketing-list a tbcx-pw-card h3.tbcx-pw-card__title");
    }
}

