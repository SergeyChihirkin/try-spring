package service;

import domain.Auditorium;

import java.util.Set;

public interface AuditoriumService {

    Set<Auditorium> getAll();

    Auditorium getByName(String name);
}
