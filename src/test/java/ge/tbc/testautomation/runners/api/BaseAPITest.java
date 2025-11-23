package ge.tbc.testautomation.runners.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.testng.annotations.BeforeTest;

public class BaseAPITest {
    @BeforeTest
    public void setUpRestAssuredConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        RestAssured.config = RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig
                        .objectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> objectMapper));
    }
}
