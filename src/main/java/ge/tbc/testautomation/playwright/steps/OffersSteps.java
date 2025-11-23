package ge.tbc.testautomation.playwright.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.playwright.pages.OffersPage;
import ge.tbc.testautomation.playwright.util.ScreenshotUtils;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;

public class OffersSteps extends BaseSteps<OffersSteps> {
    private final Page page;
    private final OffersPage offersPage;

    public OffersSteps(Page page) {
        super(page);
        this.page = page;
        this.offersPage = new OffersPage(page);
    }
    @Step("Navigate to more offers from offers page")
    public OffersSteps goToOffers() {
        offersPage.readMoreBtn.click();

        return this;
    }

    @Step("Select subType of offers: {subtypeName}")
    public OffersSteps selectSubType(String subtypeName) {
        final int maxAttempts = 10;
        int attempts = 0;
        while (attempts < maxAttempts) {
            Locator sections = page.locator("tbcx-pw-section-title");
            for (int i = 0; i < sections.count(); i++) {
                Locator title = sections.nth(i).locator("h2.tbcx-pw-title");
                if (title.innerText().trim().equalsIgnoreCase(subtypeName.trim())) {
                    sections.nth(i).scrollIntoViewIfNeeded();
                    Locator readMoreButton = sections.nth(i).locator("tbcx-pw-button tbcx-pw-link button");
                    readMoreButton.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                    readMoreButton.click();
                    return this;
                }
            }
            page.waitForTimeout(1000);
            attempts++;
        }
        return this;
    }

    @Step("Apply filter: {filterName}")
    public OffersSteps applyFilter(String filterName) {
        Locator filterItems = page.locator("div.filter-item");
        for (int i = 0; i < filterItems.count(); i++) {
            if (filterItems.nth(i).innerText().trim().equalsIgnoreCase(filterName)) {
                filterItems.nth(i).scrollIntoViewIfNeeded();
                filterItems.nth(i).click();
                page.waitForTimeout(1000);
                return this;
            }
        }
        return this;
    }

    @Step("Remove all applied filters")
    public OffersSteps removeFilters() {
        offersPage.resetButton.click();
        page.waitForTimeout(1000);
        return this;
    }

    @Step("Verify that offers match filter: {filterName}")
    public OffersSteps verifyOffersMatchFilter(String filterName) {
        List<Locator> badgeGroups = page.locator(".tbcx-pw-badge-group").all();
        int limit = Math.min(10, badgeGroups.size());

        Map<String, List<String>> filterMap = Map.of(
                "discount", List.of("discount", "special offer", "cashback", "for youth")
        );

        List<String> allowedBadges = filterMap.getOrDefault(
                filterName.toLowerCase(),
                List.of(filterName.toLowerCase())
        );


        for (int i = 0; i < limit; i++) {
            List<String> badgeTexts = badgeGroups.get(i)
                    .locator(".tbcx-pw-text-badge")
                    .allTextContents()
                    .stream()
                    .map(text -> text.trim().toLowerCase())
                    .toList();

            boolean matches = badgeTexts.stream()
                    .anyMatch(badge -> allowedBadges.stream()
                            .anyMatch(badge::contains));

            if (!matches) {
                ScreenshotUtils.takeScreenshot(page, "OfferMismatch_" + i);
                throw new AssertionError(
                        String.format("Offer %d does not match filter '%s'. Found badges: %s",
                                i, filterName, badgeTexts)
                );
            }
        }
        return this;
    }

    @Step("Verify that filter '{filterName}' is unchecked in filters list")
    public OffersSteps verifyFiltersRemoved(String filterName) {
        page.waitForTimeout(1000);

        boolean isFilterActive = page.locator(".active-filter")
                .filter(new Locator.FilterOptions().setHasText(filterName))
                .isVisible();


        if (isFilterActive) {
            ScreenshotUtils.takeScreenshot(page, "FilterNotRemoved_" + filterName);
            throw new AssertionError("Filters not removed: " + filterName);
        }
        return this;
    }
}
