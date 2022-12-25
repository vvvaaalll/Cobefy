package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.cobenco.Cobefy.model.song.SongInfo;
import hr.cobenco.Cobefy.model.user.User;

public final class Mapper {



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

    public static SongInfoDto songInfoToDto(final SongInfo songInfo){
        return new SongInfoDto(
                songInfo.getId(),
                songInfo.getName(),
                songInfo.getArtist(),
                songInfo.getSongUrl(),
                songInfo.getImageUrl()
        );

    }

    public static SongInfo songInfoDtoToEntity(final SongInfoDto songInfoDto){
        return new SongInfo(
                songInfoDto.getId(),
                songInfoDto.getName(),
                songInfoDto.getArtist(),
                songInfoDto.getSongUrl(),
                songInfoDto.getImageUrl()
        );

    }

    public static SongInfo mapUpdateSongInfoDtoToEntity(final SongInfo songInfoEntity, final SongInfoUpdateDto songInfoUpdateDto) throws RuntimeException {
        songInfoUpdateDto.getOptionalOfSongUrl().ifPresent(songInfoEntity::setSongUrl);
        songInfoUpdateDto.getOptionalOfImageUrl().ifPresent(songInfoEntity::setImageUrl);
        songInfoUpdateDto.getOptionalOfName().ifPresent(songInfoEntity::setName);
        songInfoUpdateDto.getOptionalOfArtist().ifPresent(songInfoEntity::setArtist);

        return songInfoEntity;
    }

}