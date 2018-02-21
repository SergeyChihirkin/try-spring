package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Event extends DomainObject {
    private String name;
    private double basePrice;
    private EventRating rating;
    private NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

    public void assignAuditorium(LocalDateTime dateTime, Auditorium auditorium) {
        auditoriums.put(dateTime, auditorium);
    }

    public boolean removeAuditoriumAssignment(LocalDateTime dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    public boolean airsOnDateTime(LocalDateTime dateTime) {
        return auditoriums.keySet().stream().anyMatch(dt -> dt.equals(dateTime));
    }

    public boolean airsOnDate(LocalDate date) {
        return auditoriums.keySet().stream().anyMatch(dt -> dt.toLocalDate().equals(date));
    }

    public boolean airsOnDates(LocalDate from, LocalDate to) {
        return auditoriums.keySet().stream().
                anyMatch(dt -> dt.toLocalDate().compareTo(from) >= 0 && dt.toLocalDate().compareTo(to) <= 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public NavigableMap<LocalDateTime, Auditorium> getAuditoriums() {
        return auditoriums;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
