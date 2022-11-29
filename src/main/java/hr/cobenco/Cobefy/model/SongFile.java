package hr.cobenco.Cobefy.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "song")
@DynamicInsert
@DynamicUpdate
public class SongFile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "song_blob")
    private byte[] data;


    public SongFile(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

}
