try-spring V.1.0:

Spring application for managing a movie theater, which allows for admins to enter events, view purchased tickets, and for users to register, view events with air dates and times, get ticket price, buy tickets.

The XML configuration of Spring was used. Configuration with annotations will be created in the next versions.

DAO classes for storing data in simple static maps were created. In the next versions they will be replaced with classes for storing data in DB.

There is no fancy application UI for now.

Services and their descriptions that the application provide:

UserService - Manages registered users.
Methods: 
  save, remove, getById, getUserByEmail, getAll

EventService - Manages events (movie shows). Event contains only basic information, like name, base price for tickets, rating (high, mid, low). Event can be presented on several dates and several times within each day. For each dateTime an Event will be presented only in single Auditorium.
Methods: 
  save, remove, getById, getByName, getAll,
  AuditoriumService - Returns info about auditoriums and places

Since auditorium information is usually static, it is stored in the property file. The information that is stored:
  name
  number of seats
  vip seats (comma-separated list of expensive seats)
Information from the properties file is injected into the AuditoriumService
Methods:
  getAll(), getByName()

BookingService - Manages tickets, prices, bookings
Methods:
  getTicketsPrice(event, dateTime, user, seats) - returns total price for buying all tickets for specified event on specific date and time for specified seats.
    User is needed to calculate discount (see below)
    Event is needed to get base price, rating
    Vip seats cost more than regular seats (For example, 2xBasePrice)
    All prices for high rated movies are higher (For example, 1.2xBasePrice)
  bookTicket(tickets) - Ticket contains information about event, air dateTime, seat, and user. The user could be registered or not. If user is registered, then booking information is stored for that user (in the tickets collection).       Purchased tickets for particular event are stored.
  getPurchasedTicketsForEvent(event, dateTime) - get all purchased tickets for event for specific date and Time

DiscountService - Counts different discounts for purchased tickets
Methods:
  getDiscount(user, event, dateTime, numberOfTickets) - returns total discount (from 0 to 100) that can be applied to the user buying specified number of tickets for specific event and air dateTime

DiscountStrategy - single class with logic for calculating discount
  Birthday strategy - give 5% if user has birthday within 5 days of air date
  Every 10th ticket - give 50% for every 10th ticket purchased by user. This strategy can also be applied for not-registered users if 10 or more tickets are bought
All discount strategies are injected as list into the DiscountService. The getDiscount method executes each strategy to get max available discount. Discounts are not add up. So, if one strategy returns 20% and another 30%, final discount would be 30%.