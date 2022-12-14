package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.model.storage.ImageFile;
import hr.cobenco.Cobefy.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    private final ImageFileRepository imageFileRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public ImageFile store(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ImageFile imageFile = new ImageFile(fileName, file.getContentType(), file.getBytes());

        return imageFileRepository.save(imageFile);
    }

    @PreAuthorize("hasRole('USER')")
    public ImageFile getFile(long id) {
        return imageFileRepository.findById(id).get();
    }

    @PreAuthorize("hasRole('USER')")
    public Stream<ImageFile> getAllFiles() {
        return imageFileRepository.findAll().stream();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(long id){ imageFileRepository.deleteById(id); }
}
