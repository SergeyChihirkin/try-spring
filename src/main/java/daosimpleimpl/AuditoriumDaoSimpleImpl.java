package daosimpleimpl;

import dao.AuditoriumDao;
import domain.Auditorium;

import java.util.HashSet;
import java.util.Set;

public class AuditoriumDaoSimpleImpl implements AuditoriumDao {
    private Set<Auditorium> auditoriums = new HashSet<>();

    public AuditoriumDaoSimpleImpl(Set<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    @Override
    public Set<Auditorium> getAll() {
        return auditoriums;
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoriums.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }
}
