package hr.cobenco.Cobefy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CreateUserDto {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String avatarUrl;
}