package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.Mapper;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.dto.UserDto;
import hr.cobenco.Cobefy.model.user.User;
import hr.cobenco.Cobefy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class Favorites {

    private final UserService userService;

    @Operation(description = "Get list of user song favorites",
            summary = "Returns list of all favorite songs of user with provided id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    public ResponseEntity<List<SongInfoDto>> getFavorites(HttpServletRequest request) {
        return new ResponseEntity<>(this.userService.getFavorites(request.getUserPrincipal().getName()), HttpStatus.OK);
    }

    @Operation(description = "Add song to favorites", summary = "Add song to favorites for user with provided id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{id}")
    public ResponseEntity<?> addToFavorites(@PathVariable final Long id,
                                            HttpServletRequest request) {
        this.userService.addToFavorites(id, request.getUserPrincipal().getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Remove song from favorites",
            summary = "Remove song from favorites for user with provided id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable final Long id,
                                                 HttpServletRequest request) {
        this.userService.removeFromFavorites(id, request.getUserPrincipal().getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
