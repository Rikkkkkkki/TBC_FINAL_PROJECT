package ge.tbc.testautomation.api.client;

import ge.tbc.testautomation.api.data.models.request.OffersRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OffersAPI extends TBCBaseAPI {
    public Response getAllRequests(OffersRequest emptyOffersRequest) {
        return given()
                .spec(REQ_SPEC)
                .body(emptyOffersRequest)
                .post("/marketing/entries/offer");
    }  
    
    public Response getRequestWithFilters(OffersRequest offersRequest) {
        return given()
                .spec(REQ_SPEC)
                .body(offersRequest)
                .post("/marketing/entries/offer");
    }
}
