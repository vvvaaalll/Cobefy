package hr.cobenco.Cobefy.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Blob;

@Data
@NoArgsConstructor
@Entity
@Table(name = "song")
@DynamicInsert
@DynamicUpdate
public class Song {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "song_blob")
    private Blob songBlob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "songInfo_id", referencedColumnName = "id")
    private SongInfo songInfo;


}
