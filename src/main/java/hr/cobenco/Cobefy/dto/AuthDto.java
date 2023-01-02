package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.cobenco.Cobefy.model.user.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class AuthDto {

    @JsonProperty("Auth-token")
    private AuthToken token;
    @JsonProperty("user")
    private UserDto user;

}
