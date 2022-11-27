package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.SongDto;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.service.SongInfoService;
import hr.cobenco.Cobefy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final SongInfoService songInfoService;


    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "song", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SongInfoDto addSong(@RequestPart("info") SongInfoDto songInfoDto, @RequestPart("song") MultipartFile multipartFile) throws RuntimeException, SQLException, IOException {
        System.out.print(multipartFile.getContentType());
        return this.songService.add(multipartFile, songInfoDto);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "song-info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SongInfoDto addSongInfo(@RequestBody SongInfoDto songInfoDto) throws RuntimeException {
        return this.songInfoService.add(songInfoDto);
    }



}

