package dongduk.cs.moaread.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Application {
    private int id;
    private String userId;
    private int totalQuantity;
    private String accountBank;
    private String accountNumber;
    private LocalDateTime createdAt;
    private String status;
}
