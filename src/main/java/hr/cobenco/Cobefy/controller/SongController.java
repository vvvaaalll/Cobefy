package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.SongDto;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.message.ResponseMessage;
import hr.cobenco.Cobefy.service.SongInfoService;
import hr.cobenco.Cobefy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

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
    @PostMapping(path = "song", consumes = MediaType.MULTIPART_MIXED_VALUE)
    public ResponseEntity<ResponseMessage> addSong(@RequestParam("file") MultipartFile multipartFile, @RequestPart SongInfoDto songInfoDto) throws RuntimeException, SQLException, IOException {
        String message = "";
        try {
            songService.save(multipartFile, songInfoDto);

            message = "Uploaded the file successfully: " + multipartFile.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + multipartFile.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "song-info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SongInfoDto addSongInfo(@RequestBody SongInfoDto songInfoDto) throws RuntimeException {
        return this.songInfoService.add(songInfoDto);
    }



}

