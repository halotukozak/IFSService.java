package zadanie.java.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@Data
public class StoreRequest {
    @JsonProperty("pickers")
    private List<String> pickers;
    @JsonProperty("pickingStartTime")
    private LocalTime pickingStartTime;
    @JsonProperty("pickingEndTime")
    private LocalTime pickingEndTime;

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    public static StoreRequest fromJSON(String content) throws IOException {
        return mapper.readValue(content, StoreRequest.class);
    }
}
