package hr.cobenco.Cobefy.repository;

import hr.cobenco.Cobefy.model.song.SongInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongInfoRepository extends JpaRepository<SongInfo, Long> {

}
