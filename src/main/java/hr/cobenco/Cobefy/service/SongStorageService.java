package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.dto.SongDto;
import hr.cobenco.Cobefy.model.Song;
import hr.cobenco.Cobefy.model.SongInfo;
import hr.cobenco.Cobefy.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SongStorageService implements FileStorageService{

    private final Path root = Paths.get("uploads");
    private final SongRepository songRepository;

    public SongDto mapEntityToSongDto(Song song) throws SQLException, IOException {

        SongDto songDto = new SongDto();

        MultipartFile multipartFile;

        String destPath = System.getProperty("java.io.tmpdir");

        songDto.setName(song.getFileName());
        songDto.setType(song.getType());
        songDto.setData(song.getData());


        return songDto;
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    @Override
    public void save(MultipartFile file) {

        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
        }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
