package hr.cobenco.Cobefy.dto;

import lombok.Data;

@Data
public class SongFileDto {

    private String name;
    private String url;
    private String type;
    private long size;

    public SongFileDto(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

}
