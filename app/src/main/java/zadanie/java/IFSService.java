package zadanie.java;

import zadanie.java.model.Order;
import zadanie.java.model.Picker;
import zadanie.java.request.StoreRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class IFSService {
    private final Pickers pickers = new Pickers();
    private final PriorityQueue<Order> orders = new PriorityQueue<>();

    private final List<Order> completedOrders = new ArrayList<>();



    protected void parseArguments(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("Provide more arguments!");
        if (args.length > 2) throw new IllegalArgumentException("Provide less arguments!");

        String storeConfigPath = args[0];
        String ordersPath = args[1];

        if (!storeConfigPath.endsWith(".json") || !ordersPath.endsWith(".json"))
            throw new IllegalArgumentException("Provide JSON file!");

        try {
            StoreRequest storeRequest = StoreRequest.fromJSON(Files.readString(Path.of(storeConfigPath)));

            Picker.setTime(storeRequest.getPickingStartTime(), storeRequest.getPickingEndTime());
            pickers.setPickers(storeRequest.getPickers());

            orders.addAll(Order.fromJSON(Files.readString(Path.of(ordersPath))));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void init() {
        while (!orders.isEmpty()) {
            Order order = orders.poll();
            if (pickers.assignOrder(order))
                completedOrders.add(order);
        }
    }

    private void printResults() {
        completedOrders.stream().sorted(Comparator.comparing(Order::getOrderId)).map(Order::toString).forEach(System.out::println);
    }

    public void run(String[] args) {
        parseArguments(args);
        init();
        printResults();
    }
}
