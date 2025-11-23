package ge.tbc.testautomation.api.data.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OfferFilters {
    @JsonProperty("Category:Travel")
    TRAVEL,
    @JsonProperty("Category:Home")
    Home,
    @JsonProperty("Category:Shopping")
    SHOPPING,
    @JsonProperty("Category:Developers")
    DEVELOPERS,
    @JsonProperty("ProductType:Installment")
    INSTALLMENT,
    @JsonProperty("OfferType:Discount")
    DISCOUNT
}
