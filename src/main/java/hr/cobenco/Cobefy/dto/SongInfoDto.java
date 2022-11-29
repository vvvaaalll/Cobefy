package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SongInfoDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("song-name")
    private String name;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("url")
    private String url;

}
