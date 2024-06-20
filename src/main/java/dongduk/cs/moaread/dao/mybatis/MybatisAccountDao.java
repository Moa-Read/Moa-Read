package dongduk.cs.moaread.dao.mybatis;

import dongduk.cs.moaread.dao.AccountDao;
import dongduk.cs.moaread.dao.mybatis.mapper.AccountMapper;
import dongduk.cs.moaread.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisAccountDao implements AccountDao {
    @Autowired
    private AccountMapper accountMapper;

    public int insertAccount(Account account) throws DataAccessException {
        return accountMapper.insertAccount(account);
    }

    public Account findAccountById(String id) throws DataAccessException {
        return accountMapper.findAccountById(id);
    }
}
