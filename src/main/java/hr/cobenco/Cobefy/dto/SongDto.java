package hr.cobenco.Cobefy.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Blob;

@Data
public class SongDto {


    private String name;

    private String type;

    private byte[] data;

}
