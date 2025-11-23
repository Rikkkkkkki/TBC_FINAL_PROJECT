package ge.tbc.testautomation.tests.api;

import ge.tbc.testautomation.runners.api.BaseAPITest;
import ge.tbc.testautomation.api.data.models.request.OfferFilters;
import ge.tbc.testautomation.api.data.models.response.ResponseOfferShortened;
import ge.tbc.testautomation.api.steps.OffersAPISteps;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Test(description = "Applying and Removing filters on Offers API [KAN-T4]")
public class APITests extends BaseAPITest {
    OffersAPISteps apiSteps = new OffersAPISteps();

    @Test(description = "Apply HOME and DISCOUNT Offers with api.")
    @Description("Ensure when multiple filters are assigned the returned list contains matching offers.")
    public void offersMatchFilterTest() {
        List<OfferFilters> filters = List.of(OfferFilters.Home, OfferFilters.DISCOUNT);

        List<ResponseOfferShortened> response = apiSteps.getOffersWithFilters(filters);

        assertThat(response
                .stream()
                .map(ResponseOfferShortened::title)
                .toList(), everyItem(containsStringIgnoringCase("discount")));
    }

    @Test(description = "Retrieve all offers and filtered offers with DEVELOPERS filter")
    @Description("Ensure applying a filter decreases the size of the returned list.")
    public void filtersChangeListSizeTest() {
        int unfilteredSize = apiSteps.getUnfilteredFullSize();

        int filteredResponse = apiSteps.getFilteredResponseFullSize(List.of(OfferFilters.DEVELOPERS));

        assertThat(unfilteredSize, greaterThan(filteredResponse));
    }

    @Test(description = "Retrieve offers with SHOPPING filter and SHOPPING and TRAVEL filters.")
    @Description("Ensure applying multiple filters returns a larger list than a single filter.")
    public void multipleFiltersReturnMoreThanOneTest() {
        int oneFilterResponse = apiSteps.getFilteredResponseFullSize(List.of(OfferFilters.SHOPPING));

        int multipleFilterResponse = apiSteps.getFilteredResponseFullSize(List.of(OfferFilters.SHOPPING, OfferFilters.TRAVEL));
        
        assertThat(oneFilterResponse, lessThan(multipleFilterResponse));
    }
}
