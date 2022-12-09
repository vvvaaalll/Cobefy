package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.PostSongInfoDto;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.dto.SongInfoUpdateDto;
import hr.cobenco.Cobefy.message.ResponseMessage;
import hr.cobenco.Cobefy.service.SongInfoService;
import hr.cobenco.Cobefy.service.SongStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
public class SongController {

    private final SongStorageService songStorageService;
    private final SongInfoService songInfoService;

    @Operation(description = "Create song by providing link from song storage",
            summary = "Create song by providing link from song storage")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> addSong(@RequestBody PostSongInfoDto postSongInfoDto) throws RuntimeException {
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

    @Operation(description = "Returns list of songs", summary = "Returns list of songs as song info")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SongInfoDto> getAll() {
        return songInfoService.getAll();
    }

    @Operation(description = "Returns song", summary = "Returns song found by ID")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SongInfoDto getById(@PathVariable long id) {
        return songInfoService.getById(id);
    }

    @Operation(description = "Delete song by ID", summary = "deletes song found by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @DeleteMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable long id) {
        songInfoService.delete(id);
    }

    @Operation(description = "Update song info", summary = "change song info")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PatchMapping(path = "/patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SongInfoDto updateById(@PathVariable long id, @RequestBody SongInfoUpdateDto songInfo) {
        return songInfoService.patch(id, songInfo);
    }


}

