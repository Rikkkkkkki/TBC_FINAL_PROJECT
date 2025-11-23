package ge.tbc.testautomation.api.client;

import ge.tbc.testautomation.api.data.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class TBCBaseAPI {
    public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(Constants.TBC_BASE_API_URL)
            .setContentType(ContentType.JSON)
            .addHeader("Origin", Constants.ORIGIN_HEADER)
            .addHeader("X-XSRF-TOKEN", Constants.XSRF_TOKEN_HEADER)
            .addHeader("Cookie", Constants.COOKIE_HEADER)
            .log(LogDetail.ALL)
            .build()
            .filter(new ResponseLoggingFilter());
}
