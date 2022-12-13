package hr.cobenco.Cobefy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageFileDto {

    @JsonProperty("id")
    private long id;
    @JsonProperty("image-name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("type")
    private String type;
    @JsonProperty("file-size")
    private long size;

    public ImageFileDto(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

}
