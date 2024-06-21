package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.AccountDao;
import dongduk.cs.moaread.domain.Account;
import dongduk.cs.moaread.domain.enums.Role;
import dongduk.cs.moaread.domain.enums.Status;
import dongduk.cs.moaread.dto.account.request.SignupReqDto;
import dongduk.cs.moaread.exception.DuplicatedIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountDao accountDao;
    private final PasswordEncoder passwordEncoder;

    /* 회원 가입 */
    public int signUp(SignupReqDto signupReqDto) throws DuplicatedIdException {
        // 아이디 중복 검사
        if (isDuplicate(signupReqDto.getId())) {
            throw new DuplicatedIdException();
        }

        // new account 생성
        Account newAccount = new Account();
        newAccount.setId(signupReqDto.getId());
        newAccount.setPassword(passwordEncoder.encode(signupReqDto.getPassword()));
        newAccount.setName(signupReqDto.getName());
        newAccount.setPhone(signupReqDto.getPhone());
        newAccount.setEmail(signupReqDto.getEmail());
        newAccount.setAddress(signupReqDto.getAddress());
        newAccount.setDetailedAddress(signupReqDto.getDetailedAddress());
        newAccount.setZip(signupReqDto.getZip());
        newAccount.setStatus(Status.ACTIVE);
        newAccount.setRole(Role.USER);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setUpdatedAt(LocalDateTime.now());
        newAccount.setBlogUrl("/blog/" + signupReqDto.getId());

        return accountDao.insertAccount(newAccount);
    }

    /* 아이디 중복 검사 */
    public boolean isDuplicate(String id) {
        Account account = accountDao.findAccountById(id);

        System.out.println(account.getId());

        if (account != null) {
            return true;
        }

        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountDao.findAccountById(username);

        if (account == null) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(("ADMIN")));
        } else {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }

        return new User(account.getId(), account.getPassword(), authorities);
    }
}
