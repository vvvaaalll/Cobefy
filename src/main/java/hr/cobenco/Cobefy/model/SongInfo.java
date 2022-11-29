package hr.cobenco.Cobefy.model;

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

    @Column(name = "url")
    private String url;

    public SongInfo(String name, String artist){
        this.name = name;
        this.artist = artist;
    }

}