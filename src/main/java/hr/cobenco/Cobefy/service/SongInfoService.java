package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.dto.PostSongInfoDto;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.dto.SongInfoUpdateDto;
import hr.cobenco.Cobefy.model.song.SongInfo;
import hr.cobenco.Cobefy.repository.SongInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongInfoService {

    private final SongInfoRepository songInfoRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public SongInfoDto add(final PostSongInfoDto postSongInfoDto) {
        SongInfo songInfo = new SongInfo();
        songInfo.setName(postSongInfoDto.getName());
        songInfo.setArtist(postSongInfoDto.getArtist());
        songInfo.setSongUrl(postSongInfoDto.getSongUrl());
        songInfo.setImageUrl(postSongInfoDto.getImageUrl());

        return mapEntityToSongInfoDto(this.songInfoRepository.save(songInfo));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SongInfoDto patch(final long id, final SongInfoUpdateDto songInfoUpdateDto) throws RuntimeException {
        return mapEntityToSongInfoDto(this.songInfoRepository
                .save(mapUpdateSongInfoDtoToEntity(this.songInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("song not found")), songInfoUpdateDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(final long id) throws RuntimeException {
        songInfoRepository.delete(this.songInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("No song with such ID")));
    }

    @PreAuthorize("hasRole('USER')")
    public List<SongInfoDto> getAll() throws RuntimeException {

        List<SongInfoDto> usersDto = new ArrayList<SongInfoDto>();
        songInfoRepository.findAll().forEach(song -> {
            usersDto.add(mapEntityToSongInfoDto(song));
        });
        return usersDto;

    }

    @PreAuthorize("hasRole('USER')")
    public SongInfoDto getById(final long id) throws RuntimeException {

        SongInfoDto songInfoDto = new SongInfoDto();
        songInfoRepository.findById(id).orElseThrow((() -> new RuntimeException("No song with such ID")));
        return songInfoDto;

    }

    public SongInfoDto mapEntityToSongInfoDto(SongInfo songInfo) {

        SongInfoDto songInfoDto = new SongInfoDto();
        songInfoDto.setId(songInfo.getId());
        songInfoDto.setName(songInfo.getName());
        songInfoDto.setArtist(songInfo.getArtist());
        songInfoDto.setSongUrl(songInfo.getSongUrl());
        songInfoDto.setImageUrl(songInfo.getImageUrl());

        return songInfoDto;
    }

    private SongInfo mapUpdateSongInfoDtoToEntity(final SongInfo songInfoEntity, final SongInfoUpdateDto songInfoUpdateDto) throws RuntimeException {
        songInfoUpdateDto.getOptionalOfSongUrl().ifPresent(songInfoEntity::setSongUrl);
        songInfoUpdateDto.getOptionalOfImageUrl().ifPresent(songInfoEntity::setImageUrl);
        songInfoUpdateDto.getOptionalOfName().ifPresent(songInfoEntity::setName);
        songInfoUpdateDto.getOptionalOfArtist().ifPresent(songInfoEntity::setArtist);

        return songInfoEntity;
    }

}
