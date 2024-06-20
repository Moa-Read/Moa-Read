package dongduk.cs.moaread.dao.mybatis.mapper;

import dongduk.cs.moaread.domain.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    int insertAccount(Account account);

    Account findAccountById(String id);
}
