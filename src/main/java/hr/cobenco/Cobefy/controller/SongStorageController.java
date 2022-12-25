package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.SongFileDto;
import hr.cobenco.Cobefy.message.ResponseMessage;
import hr.cobenco.Cobefy.model.storage.SongFile;
import hr.cobenco.Cobefy.service.SongStorageService;
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

    @ResponseBody
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getSongFile(@PathVariable long id) {

        SongFile songFile = songStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + songFile.getName() + "\"")
                .body(songFile.getData());
    }

    @ResponseBody
    @DeleteMapping("/files/{id}/delete")
    public ResponseEntity<ResponseMessage> deleteImageFile(@PathVariable long id) {
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
