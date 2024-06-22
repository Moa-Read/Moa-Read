package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.ApplicationDao;
import dongduk.cs.moaread.domain.Application;
import dongduk.cs.moaread.dto.application.request.CreateApplicationReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationDao applicationDao;

    /* 중고 도서 판매 신청 */
    public void createApplication(CreateApplicationReqDto applicationReqDto) {
        Application application = new Application();
        application.setUserId(applicationReqDto.getUserId());
        application.setTotalQuantity(applicationReqDto.getTotalQuantity());
        application.setAccountBank(applicationReqDto.getAccountBank());
        application.setAccountNumber(applicationReqDto.getAccountNumber());
        application.setCreatedAt(LocalDateTime.now());
        application.setStatus("PENDING");

        applicationDao.insertApplication(application);
    }

    /* 중고 도서 판매 신청 취소 */
    public void cancelApplication(int id) {
        applicationDao.deleteApplication(id);
    }

    /* 중고 도서 판매 신청 내역 전체 조회 */
    public List<Application> getAllApplications() {
        return applicationDao.selectAllApplications();
    }

    /* 중고 도서 판매 신청 내역 사용자별 조회 */
    public List<Application> getApplicationsByUser(String userId) {
        return applicationDao.selectApplicationsByUser(userId);
    }

    /* 중고 도서 판매 신청 내역 상세 조회 */
    public Application getApplicationById(int id) {
        return applicationDao.selectApplicationById(id);
    }

    /* 신청이 사용자 소유인지 확인 */
    public boolean isOwnedByUser(int id, String userId) {
        Application application = applicationDao.selectApplicationById(id);
        return application.getUserId().equals(userId);
    }

    /* 관리자 기능 - 중고 도서 판매 신청 심사 */
    public void reviewApplication(int id, String status) {
        Application application = applicationDao.selectApplicationById(id);
        application.setStatus(status);
        applicationDao.updateApplication(application);
    }
}
