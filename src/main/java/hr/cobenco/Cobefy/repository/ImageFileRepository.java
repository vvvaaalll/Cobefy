package hr.cobenco.Cobefy.repository;

import hr.cobenco.Cobefy.model.storage.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

        }
