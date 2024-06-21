package dongduk.cs.moaread.dto.account.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResDto {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String detailedAddress;
    private String zip;
}
