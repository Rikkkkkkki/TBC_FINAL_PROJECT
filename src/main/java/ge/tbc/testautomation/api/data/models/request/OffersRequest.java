package ge.tbc.testautomation.api.data.models.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Data
@Accessors(fluent = true, chain = true)
@Jacksonized
@Builder
public class OffersRequest {
    private List<OfferFilters> filter;
    private int pageIndex;
    private String segment;
    private int pageSize;
    private String locale;
}