package zadanie.java;

import zadanie.java.model.Order;
import zadanie.java.model.Picker;

import java.util.List;

public class Pickers {
    private List<Picker> pickers;

    public void setPickers(List<String> pickers) {
        this.pickers = pickers.stream().map(Picker::new).toList();
    }

    public boolean assignOrder(Order order) {
        for (var picker : pickers) {
            boolean isAssigned = picker.assign(order);
            if (isAssigned) return true;
        }
        return false;
    }
}
