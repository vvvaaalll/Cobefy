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

    public static User CreateUserToEntity(final CreateUserDto user) {
        return new User(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatarUrl()
        );
    }

}