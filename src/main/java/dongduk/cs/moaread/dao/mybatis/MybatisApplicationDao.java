package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.ApplicationDao;
import dongduk.cs.moaread.dao.mybatis.mapper.ApplicationMapper;
import dongduk.cs.moaread.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisApplicationDao implements ApplicationDao {
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public void insertApplication(Application application) throws DataAccessException {
        applicationMapper.insertApplication(application);
    }

    @Override
    public void deleteApplication(int id) throws DataAccessException {
        applicationMapper.deleteApplication(id);
    }

    @Override
    public List<Application> selectAllApplications() throws DataAccessException {
        return applicationMapper.selectAllApplications();
    }

    @Override
    public List<Application> selectApplicationsByUser(String userId) throws DataAccessException {
        return applicationMapper.selectApplicationsByUser(userId);
    }

    @Override
    public Application selectApplicationById(int id) throws DataAccessException {
        return applicationMapper.selectApplicationById(id);
    }

    @Override
    public void updateApplication(Application application) throws DataAccessException {
        applicationMapper.updateApplication(application);
    }
}
