package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.dto.SongDto;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.model.Song;
import hr.cobenco.Cobefy.model.SongInfo;
import hr.cobenco.Cobefy.repository.SongInfoRepository;
import hr.cobenco.Cobefy.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@Service
@RequiredArgsConstructor
public class SongInfoService {

    private final SongInfoRepository songInfoRepository;

    public SongInfoDto add(final SongInfoDto songInfoDto){
        SongInfo songInfo = new SongInfo();
        songInfo.setName(songInfoDto.getName());
        songInfo.setArtist(songInfoDto.getArtist());

        return mapEntityToSongInfoDto(this.songInfoRepository.save(songInfo));

    }


    public SongInfoDto mapEntityToSongInfoDto(SongInfo songInfo){

        SongInfoDto songInfoDto = new SongInfoDto();
        songInfoDto.setName(songInfo.getName());
        songInfoDto.setArtist(songInfo.getArtist());

        return songInfoDto;
    }


}
