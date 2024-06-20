package dongduk.cs.moaread.domain;

import dongduk.cs.moaread.domain.enums.Role;
import dongduk.cs.moaread.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Account {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String detailedAddress;
    private String zip;
    private Status status;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String blogUrl;
}
