package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.dto.SongDto;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.model.Song;
import hr.cobenco.Cobefy.model.SongInfo;
import hr.cobenco.Cobefy.repository.SongInfoRepository;
import hr.cobenco.Cobefy.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final SongInfoRepository songInfoRepository;

    public SongDto add(final MultipartFile multipartFile,
                       final SongInfoDto songInfoDto) throws RuntimeException, IOException, SQLException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Song song = new Song(fileName, multipartFile.getContentType(), multipartFile.getBytes());

        song.setSongInfo(new SongInfo(songInfoDto.getName(), songInfoDto.getArtist()));

        return mapEntityToSongDto(this.songRepository.save(song));

    }


    public SongDto mapEntityToSongDto(Song song) throws SQLException, IOException {

        SongDto songDto = new SongDto();

        MultipartFile multipartFile;

        String destPath = System.getProperty("java.io.tmpdir");

        songDto.setName(song.getFileName());
        songDto.setType(song.getType());
        songDto.setData(song.getData());


        return songDto;
    }


}
