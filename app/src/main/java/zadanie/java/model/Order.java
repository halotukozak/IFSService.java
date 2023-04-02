package zadanie.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Order implements Comparable<Order> {
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("orderValue")
    private BigDecimal orderValue;
    @JsonProperty("pickingTime")
    private Duration pickingTime;
    @JsonProperty("completeBy")
    private LocalTime completeBy;

    @JsonIgnore
    private int priority;
    @JsonIgnore
//    excluding
    private LocalTime startTime;
    @JsonIgnore
//    including
    private LocalTime endTime;
    @JsonIgnore
    private String pickerId;
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    public int getPriority() {
        if (this.priority == 0) {
            BigDecimal minutes = BigDecimal.valueOf(pickingTime.getSeconds() / 60);
            if (minutes.equals(BigDecimal.ZERO)) this.priority = Integer.MAX_VALUE;
            else {
                BigDecimal orderPart = orderValue.divide(minutes, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100));
                BigDecimal completeByPart = new BigDecimal(LocalTime.MAX.getHour() - completeBy.getHour());

                this.priority = orderPart.add(completeByPart).intValue();
            }
        }
        return this.priority;

    }

    public static List<Order> fromJSON(String content) throws IOException {
        return mapper.readValue(content, new TypeReference<>() {
        });
    }


    @Override
    public int compareTo(Order o) {
        return o.getPriority() - this.getPriority();
    }

    @Override
    public String toString() {
        return getPickerId() + " " + getOrderId() + " " + getStartTime();
    }

    public void assign(LocalTime startTime, String pickerId) {
        this.setStartTime(startTime);
        this.setEndTime(startTime.plus(getPickingTime()));
        this.pickerId = pickerId;
    }

    public void reassign(LocalTime lastTime) {
        this.setStartTime(lastTime.minus(getPickingTime()).plusMinutes(1));
        this.setEndTime(lastTime);
    }
}
