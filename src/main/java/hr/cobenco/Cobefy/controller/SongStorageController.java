package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.SongFileDto;
import hr.cobenco.Cobefy.message.ResponseMessage;
import hr.cobenco.Cobefy.model.storage.SongFile;
import hr.cobenco.Cobefy.service.SongStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/song-file/")
@RequiredArgsConstructor
public class SongStorageController {

    private final SongStorageService songStorageService;


    @Operation(description = "Upload song",
            summary = "Upload song file to sound file storage service. Requires Admin rights.")
    @ResponseBody
    @PostMapping(name = "/upload")
    public ResponseEntity<ResponseMessage> uploadSongFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if (file.getContentType().contains("audio")) {
                message = String.valueOf(songStorageService.store(file).getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
            } else {
                message = "Wrong file type";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Operation(description = "Returns list of all song files", summary = "Returns list of all song files.")
    @ResponseBody
    @GetMapping(name = "/files")
    public ResponseEntity<List<SongFileDto>> getListOfSongFiles() {
        List<SongFileDto> files = songStorageService.getAllFiles().map(songFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/song-file/files/")
                    .path(String.valueOf(songFile.getId()))
                    .toUriString();

            return new SongFileDto(
                    songFile.getId(),
                    songFile.getName(),
                    fileDownloadUri,
                    songFile.getType(),
                    songFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @Operation(description = "Returns song file", summary = "Returns song file found by id.")
    @ResponseBody
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getSongFile(@PathVariable long id) {

        SongFile songFile = songStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + songFile.getName() + "\"")
                .body(songFile.getData());
    }

    @Operation(description = "Deletes song file", summary = "Deletes song file by id. Requires Admin rights.")
    @ResponseBody
    @DeleteMapping("/files/{id}/delete")
    public ResponseEntity<ResponseMessage> deleteSongFile(@PathVariable long id) {
        String message = "";
        try {

            songStorageService.delete(id);

            message = "Song file deleted successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete song file";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


}
