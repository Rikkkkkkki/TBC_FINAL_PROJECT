package ge.tbc.testautomation.api.steps;

import ge.tbc.testautomation.api.client.OffersAPI;
import ge.tbc.testautomation.api.data.models.request.OfferFilters;
import ge.tbc.testautomation.api.data.models.request.OffersRequest;
import ge.tbc.testautomation.api.data.models.response.ResponseOfferShortened;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class OffersAPISteps {
    OffersAPI offersAPI = new OffersAPI();
    private final OffersRequest template = OffersRequest.builder()
            .segment("All")
            .pageSize(10)
            .pageIndex(0)
            .locale("en-US")
            .filter(new ArrayList<>())
            .build();

    @Step("Get offers with given filters: {0}")
    public List<ResponseOfferShortened> getOffersWithFilters(List<OfferFilters> filters) {
        OffersRequest filtered = template.filter(filters);
        Response response = offersAPI.getRequestWithFilters(filtered);

        return response.then()
                .extract()
                .response()
                .jsonPath()
                .getList("list", ResponseOfferShortened.class);
    }

    @Step("Get full size of filtered request: {0}")
    public int getFilteredResponseFullSize(List<OfferFilters> filters) {
        return offersAPI.getAllRequests(template.filter(filters))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getInt("pagingDetails.totalCount");
    }
    
    @Step("Get full size of unfiltered request")
    public int getUnfilteredFullSize() {
        return offersAPI.getAllRequests(template)
                .then()
                .extract()
                .response()
                .jsonPath()
                .getInt("pagingDetails.totalCount");
    }
}
