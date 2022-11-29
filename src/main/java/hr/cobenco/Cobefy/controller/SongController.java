package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.message.ResponseMessage;
import hr.cobenco.Cobefy.service.SongInfoService;
import hr.cobenco.Cobefy.service.SongStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
public class SongController {

    private final SongStorageService songStorageService;
    private final SongInfoService songInfoService;


    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "song", consumes = MediaType.MULTIPART_MIXED_VALUE)
    public ResponseEntity<ResponseMessage> addSong(@RequestParam("file") MultipartFile multipartFile, @RequestPart SongInfoDto songInfoDto) throws RuntimeException, SQLException, IOException {

        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "song-info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SongInfoDto addSongInfo(@RequestBody SongInfoDto songInfoDto) throws RuntimeException {
        return this.songInfoService.add(songInfoDto);
    }



}

