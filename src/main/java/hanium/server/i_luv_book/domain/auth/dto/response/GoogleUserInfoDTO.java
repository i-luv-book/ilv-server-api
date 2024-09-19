package hanium.server.i_luv_book.domain.auth.dto.response;

import lombok.Data;
import lombok.ToString;
/**
 * @author Young9
 */
@Data
@ToString
public class GoogleUserInfoDTO {
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
