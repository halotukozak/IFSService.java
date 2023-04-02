# In Store Fulfillment Service - Recruitment Task 
The application prepares the schedules of picking orders for each ISF employee. The main goal is the number of picked orders and their value.
Uncompleted tasks are skipped and not included in the final report.

## Example input:
Provide absolute path to the two JSON files.

/home/tester/files/store.json

`{
    "pickers": [
        "picker-1",
        "picker-2"
    ],
    "pickingStartTime": "06:00",
    "pickingEndTime": "08:30"
}`

Attributes description:
- pickers (List<String>) - pickers' IDs who picks delivery.
- pickingStartTime (LocalTime) - time when the IFS starts working
- pickingEndTime (LocalTime) - time when the IFS stops working

/home/tester/files/orders.json

`[
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
    },
    ...
]
`
Attributes description:
- orderId (String) - order's ID
- orderValue (BigDecimal) - order's value
- pickingTime (Duration) - duration of picking the order
- completeBy (LocalTime) - time when the order has to be completed


### TODO
-[x] Parse request
-[x] Calculate priority
-[x] Add value to priority calculations
-[ ] 100% Test coverage
-[ ] Resolve conflicts with really short tasks (0-1 min.)

##### Time: 10:15-22:15 on some Sunday in early April 2023