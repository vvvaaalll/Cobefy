package hr.cobenco.Cobefy.repository;

import hr.cobenco.Cobefy.model.SongFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongFileRepository extends JpaRepository<SongFile, Long> {

}
