Functional requirement:
1.PL structure:
a.The parking lot must support multiple levels,
each with a certain number of parking spots.
b.Parking spots should be categorised by size
(e.g., small for bikes, medium for cars, large for trucks).

Vehicle Parking:
a.Vehicles can be of different types: Car, Bike, and Truck.
b.The system should assign the nearest available parking spot 
based on the vehicle type and size.

3. Ticket Management:

A parking ticket must be issued when a vehicle is parked.
The ticket should include the parking level, spot ID, and timestamp.

4. Vehicle Unparking:

The system must calculate the parking fee based on the duration of parking and the pricing strategy used.
Parking spots should be marked as available after the vehicle exits.
5. Spot Availability Management:

The system must keep track of spot availability across all levels.
6. Payment Options:

Payments can be made via cash or credit card.
The appropriate payment method should be chosen dynamically using the Strategy pattern.
7. Parking Strategies:

The system should support multiple parking strategies (e.g., nearest spot parking).
Strategies should be extensible for future needs.
8. Pricing Strategies:

The system should support different pricing strategies:
Flat rate (fixed hourly charge).
Variable rate (based on vehicle type).
9. Status Display:

The 
system should display the status of the parking lot (e.g., available spots per level).


Non-Functional Requirements:
Scalability:
The parking lot design should be scalable to support a large number of levels and spots.
2. Extensibility:

The system should be easily extensible to add:
New vehicle types.
Additional pricing or parking strategies.
Advanced payment options.
3. Maintainability:

The code should follow object-oriented design principles for easier maintenance and readability.
Dependency Injection should be used to support flexibility and testing.
4. Performance:

Spot allocation and ticket generation must be efficient to handle high traffic.
5. Fault Tolerance:

The system should handle edge cases like invalid tickets, unavailable spots, or payment failures gracefully.