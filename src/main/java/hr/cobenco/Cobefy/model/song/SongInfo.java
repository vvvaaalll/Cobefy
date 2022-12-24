package hr.cobenco.Cobefy.model.song;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "song_info")
@DynamicInsert
@DynamicUpdate
public class SongInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "artist")
    private String artist;

    @Column(name = "song_url")
    private String songUrl;

    @Column(name = "image_url")
    private String imageUrl;

    public SongInfo(String name, String artist){
        this.name = name;
        this.artist = artist;
    }

}