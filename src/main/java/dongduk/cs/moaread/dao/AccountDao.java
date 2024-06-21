package dongduk.cs.moaread.dao;

import dongduk.cs.moaread.domain.Account;
import org.springframework.dao.DataAccessException;

public interface AccountDao {
    int insertAccount(Account account) throws DataAccessException;

    Account findAccountById(String id) throws DataAccessException;

    int updateAccount(Account account) throws DataAccessException;
}
