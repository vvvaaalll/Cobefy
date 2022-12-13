package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class PostSongInfoDto {

    @JsonProperty("song-name")
    private String name;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song-url")
    private String songUrl;

    @JsonProperty("image-url")
    private String imageUrl;
}
