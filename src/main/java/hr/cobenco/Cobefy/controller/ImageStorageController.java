package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.ImageFileDto;
import hr.cobenco.Cobefy.message.ResponseMessage;
import hr.cobenco.Cobefy.model.storage.ImageFile;
import hr.cobenco.Cobefy.service.ImageStorageService;
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
@RequestMapping("/api/image-file/")
@RequiredArgsConstructor
public class ImageStorageController {


    private final ImageStorageService imageStorageService;

    @ResponseBody
    @PostMapping(name = "/upload")
    public ResponseEntity<ResponseMessage> uploadSongFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if (file.getContentType().contains("image")) {
                message = String.valueOf(imageStorageService.store(file).getId());
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
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
    public ResponseEntity<List<ImageFileDto>> getListOfSongFiles() {
        List<ImageFileDto> files = imageStorageService.getAllFiles().map(imageFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/image-file/files/")
                    .path(String.valueOf(imageFile.getId()))
                    .toUriString();

            return new ImageFileDto(
                    imageFile.getId(),
                    imageFile.getName(),
                    fileDownloadUri,
                    imageFile.getType(),
                    imageFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @ResponseBody
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable long id) {

        ImageFile imageFile = imageStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getName() + "\"")
                .body(imageFile.getData());
    }

    @ResponseBody
    @DeleteMapping("/files/{id}/delete")
    public ResponseEntity<ResponseMessage> deleteImageFile(@PathVariable long id) {
        String message = "";
        try {

            imageStorageService.delete(id);

            message = "Image deleted successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete image file";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
