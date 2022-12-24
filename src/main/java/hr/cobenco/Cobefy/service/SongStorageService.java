package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.model.storage.SongFile;
import hr.cobenco.Cobefy.repository.SongFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SongStorageService{

    private final SongFileRepository songFileRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public SongFile store(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        SongFile songFile = new SongFile(fileName, file.getContentType(), file.getBytes());

        return songFileRepository.save(songFile);
    }

    @PreAuthorize("hasRole('USER')")
    public SongFile getFile(long id) {
        return songFileRepository.findById(id).get();
    }

    @PreAuthorize("hasRole('USER')")
    public Stream<SongFile> getAllFiles() {
        return songFileRepository.findAll().stream();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(long id){ songFileRepository.deleteById(id); }

}
