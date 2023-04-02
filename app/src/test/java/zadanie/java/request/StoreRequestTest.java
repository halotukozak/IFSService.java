package zadanie.java.request;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class StoreRequestTest {

    @Test
    void fromJSON() {
        try {
            StoreRequest.fromJSON(
                    """
                    {
                    "pickers": [
                    "picker-1",
                    "picker-2"
                    ],
                    "pickingStartTime": "06:00",
                    "pickingEndTime": "08:30"
                    }"""
                  );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


}