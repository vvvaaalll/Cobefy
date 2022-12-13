package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.model.ImageFile;
import hr.cobenco.Cobefy.model.SongFile;
import hr.cobenco.Cobefy.repository.ImageFileRepository;
import hr.cobenco.Cobefy.repository.SongFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    private final ImageFileRepository imageFileRepository;

    public ImageFile store(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ImageFile imageFile = new ImageFile(fileName, file.getContentType(), file.getBytes());

        return imageFileRepository.save(imageFile);
    }

    public ImageFile getFile(long id) {
        return imageFileRepository.findById(id).get();
    }

    public Stream<ImageFile> getAllFiles() {
        return imageFileRepository.findAll().stream();
    }

    public void delete(long id){ imageFileRepository.deleteById(id); }
}
