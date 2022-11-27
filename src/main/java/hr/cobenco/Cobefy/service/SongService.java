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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final SongInfoRepository songInfoRepository;

    public SongInfoDto add(final MultipartFile multipartFile,
                       final SongInfoDto songInfoDto) throws RuntimeException, IOException, SQLException {

        Song song = new Song();
        song.setSongInfo(new SongInfo(songInfoDto.getName(), songInfoDto.getArtist()));
        byte[] bytes = multipartFile.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        song.setSongBlob(blob);

        this.songRepository.save(song);
        return songInfoDto;

    }


    public SongDto mapEntityToSongDto(Song song) throws SQLException, IOException {

        SongDto songDto = new SongDto();

        MultipartFile multipartFile;

        String destPath = System.getProperty("java.io.tmpdir");

        multipartFile = new MockMultipartFile(
                song.getSongInfo().getName(),
                song.getSongInfo().getName(),
                "audio/mpeg",
                song.getSongBlob().getBinaryStream().readAllBytes()
        );


        songDto.setSongFile(multipartFile);


        return songDto;
    }


}
