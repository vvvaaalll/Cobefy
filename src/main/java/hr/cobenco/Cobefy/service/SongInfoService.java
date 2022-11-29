package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.model.SongInfo;
import hr.cobenco.Cobefy.repository.SongInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
