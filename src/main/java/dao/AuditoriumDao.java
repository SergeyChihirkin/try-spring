package dao;

import domain.Auditorium;

import java.util.HashSet;
import java.util.Set;

public interface AuditoriumDao {
    Set<Auditorium> getAll();

    Auditorium getByName(String name);
}
