package hr.cobenco.Cobefy.dto;

import hr.cobenco.Cobefy.model.user.User;

public final class ToDtoConverter {



    public static UserDto userToDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getIsActive(),
                user.getAvatarUrl(),
                user.getDateOfSignUp(),
                user.getRoles()
        );
    }

}