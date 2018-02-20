package serviceimpl;

import dao.AuditoriumDao;
import domain.Auditorium;
import service.AuditoriumService;

import java.util.Set;

public class AuditoriumServiceSimpleImpl implements AuditoriumService {
    private AuditoriumDao auditoriumDao;

    public void setAuditoriumDao(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    @Override
    public Set<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoriumDao.getByName(name);
    }
}
