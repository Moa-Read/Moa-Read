package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Application;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationMapper {
    void insertApplication(Application application);
    void deleteApplication(int id);
    List<Application> selectAllApplications();
    List<Application> selectApplicationsByUser(String userId);
    Application selectApplicationById(int id);
    void updateApplication(Application application);
}
