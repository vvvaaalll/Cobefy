package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
public class SongInfoUpdateDto {

    @JsonProperty("song-name")
    private Optional<String> optionalOfName = Optional.empty();

    @JsonProperty("artist")
    private Optional<String> optionalOfArtist = Optional.empty();

    @JsonProperty("url")
    private Optional<String> optionalOfUrl = Optional.empty();

}
