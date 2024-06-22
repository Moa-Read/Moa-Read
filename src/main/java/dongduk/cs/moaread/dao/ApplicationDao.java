package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Application;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ApplicationDao {
    void insertApplication(Application application) throws DataAccessException;
    void deleteApplication(int id) throws DataAccessException;
    List<Application> selectAllApplications() throws DataAccessException;
    List<Application> selectApplicationsByUser(String userId) throws DataAccessException;
    Application selectApplicationById(int id) throws DataAccessException;
    void updateApplication(Application application) throws DataAccessException;
}
