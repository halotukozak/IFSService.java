package zadanie.java.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import zadanie.java.model.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class OrderTest {

    @Test
    void fromJSON() {
        try {
            List<Order> orders = Order.fromJSON("""
                    [
                    {
                    "orderId": "order-1",
                    "orderValue": "52.40",
                    "pickingTime": "PT12M",
                    "completeBy": "15:30"
                    },
                    {
                    "orderId": "order-2",
                    "orderValue": "82.40",
                    "pickingTime": "PT17M",
                    "completeBy": "14:00"
                    }
                    ]
                    """);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}