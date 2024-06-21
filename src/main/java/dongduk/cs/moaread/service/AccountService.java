package dongduk.cs.moaread.service;

import dongduk.cs.moaread.dao.AccountDao;
import dongduk.cs.moaread.domain.Account;
import dongduk.cs.moaread.domain.enums.Role;
import dongduk.cs.moaread.domain.enums.Status;
import dongduk.cs.moaread.dto.account.request.SignupReqDto;
import dongduk.cs.moaread.dto.account.request.UpdateReqDto;
import dongduk.cs.moaread.dto.account.response.ProfileResDto;
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

    /* 로그인 */
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

    /* 프로필 조회 */
    public ProfileResDto getProfile(String id) {
        Account account = accountDao.findAccountById(id);

        ProfileResDto profileResDto = new ProfileResDto();
        profileResDto.setId(account.getId());
        profileResDto.setName(account.getName());
        profileResDto.setPhone(account.getPhone());
        profileResDto.setEmail(account.getEmail());
        profileResDto.setAddress(account.getAddress());
        profileResDto.setDetailedAddress(account.getDetailedAddress());
        profileResDto.setZip(account.getZip());

        return profileResDto;
    }

    /* 회원 정보 수정 */
    public int updateProfile(String id, UpdateReqDto updateReqDto) {
        Account updateAccount = new Account();
        updateAccount.setId(id);
        updateAccount.setPassword(passwordEncoder.encode(updateReqDto.getPassword()));
        updateAccount.setName(updateReqDto.getName());
        updateAccount.setPhone(updateReqDto.getPhone());
        updateAccount.setEmail(updateReqDto.getEmail());
        updateAccount.setAddress(updateReqDto.getAddress());
        updateAccount.setDetailedAddress(updateReqDto.getDetailedAddress());
        updateAccount.setZip(updateReqDto.getZip());
        updateAccount.setUpdatedAt(LocalDateTime.now());

        return accountDao.updateAccount(updateAccount);
    }

    /* 회원 탈퇴 */
    public int withdraw(String id) {
        return accountDao.updateStatus(id);
    }
}
