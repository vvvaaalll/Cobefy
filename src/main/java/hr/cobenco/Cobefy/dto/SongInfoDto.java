package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongInfoDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("song-name")
    private String name;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song-url")
    private String songUrl;
    @JsonProperty("image-url")
    private String imageUrl;

}
