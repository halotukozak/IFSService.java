package zadanie.java.model;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.PriorityQueue;

@Getter
public class Picker {
    private final String pickerId;

    private static LocalTime pickingStartTime;
    private static LocalTime pickingEndTime;

    private final PriorityQueue<Order> scheduleByEnd;
    private final PriorityQueue<Order> scheduleByStart;


    public static void setTime(LocalTime pickingStartTime, LocalTime pickingEndTime) {
        Picker.pickingStartTime = pickingStartTime;
        Picker.pickingEndTime = pickingEndTime;
    }

    public Picker(String id) {
        this.pickerId = id;
        this.scheduleByStart = new PriorityQueue<>(Comparator.comparing(Order::getStartTime));
        this.scheduleByEnd = new PriorityQueue<>(Comparator.comparing(Order::getEndTime).reversed());
    }


    public boolean assign(Order order) {
        LocalTime endTime = getMaximum(order.getCompleteBy());
        Duration duration = order.getPickingTime();
        LocalTime startTime = endTime.minus(duration);

        if (scheduleByEnd.isEmpty() || !scheduleByEnd.peek().getEndTime().isAfter(startTime)) {
            addOrder(order, startTime);
            return true;
        }

        endTime = scheduleByStart.peek().getStartTime();
        startTime = endTime.minus(duration);

        if (!startTime.isBefore(pickingStartTime)) {
            addOrder(order, startTime);
            return true;
        }

        return moveAndAdd(order);
    }

    private LocalTime getMaximum(LocalTime time) {
        return time.isBefore(pickingEndTime) ? time : pickingEndTime;
    }


    private boolean moveAndAdd(Order order) {
        PriorityQueue<Order> tmp = new PriorityQueue<>(Comparator.comparing(Order::getCompleteBy).reversed());
        tmp.addAll(scheduleByEnd.stream().toList());
        tmp.add(order);
        LocalTime lastEnd = getMaximum(tmp.peek().getCompleteBy());
        if (pickingStartTime.plusSeconds(tmp.stream().mapToLong(it -> it.getPickingTime().getSeconds()).sum()).isAfter(lastEnd))
            return false;

        order.setPickerId(pickerId);

        while (!tmp.isEmpty()) {
            Order it = tmp.poll();
            it.reassign(lastEnd);
            lastEnd = it.getStartTime();
        }
        addToQueues(order);

        return true;
    }


    private void addOrder(Order order, LocalTime startTime) {
        order.assign(startTime, pickerId);
        addToQueues(order);
    }

    private void addToQueues(Order order) {
        this.scheduleByStart.add(order);
        this.scheduleByEnd.add(order);
    }

}
