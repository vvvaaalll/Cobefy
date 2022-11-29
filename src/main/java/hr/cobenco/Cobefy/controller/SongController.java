package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.PostSongInfoDto;
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> addSong(@RequestBody PostSongInfoDto postSongInfoDto) throws RuntimeException{
        String message = "";
        try {
                songInfoService.add(postSongInfoDto);
            message = "Song" + postSongInfoDto.getName() + "successfully added!";
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Failed to add song!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }




}

