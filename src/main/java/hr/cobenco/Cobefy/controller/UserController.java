package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.CreateUserDto;
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

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @Operation(description = "register new user", summary = "Register new user with provided attributes.")
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody final CreateUserDto createUserDto) {
        this.userService.signUp(Mapper.CreateUserToEntity(createUserDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "delete user", summary = "Delete user by id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Returns list of all users",
            summary = "Returns list of all registered users. Requires Admin rights")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Returns user found by id", summary = "Returns user found by id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.getById(id), HttpStatus.OK);
    }

    @Operation(description = "Get list of user song favorites",
            summary = "Returns list of all favorite songs of user with provided id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}/favorites")
    public ResponseEntity<List<SongInfoDto>> getFavorites(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.getFavorites(id), HttpStatus.OK);
    }

    @Operation(description = "Add song to favorites", summary = "Add song to favorites for user with provided id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{id}/favorites")
    public ResponseEntity<?> addToFavorites(@PathVariable final Long id,
                                            @RequestBody SongInfoDto songInfoDto) {
        this.userService.addToFavorites(id, songInfoDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Remove song from favorites",
            summary = "Remove song from favorites for user with provided id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}/favorites")
    public ResponseEntity<?> removeFromFavorites(@PathVariable final Long id,
                                                 @RequestBody SongInfoDto songInfoDto) {
        this.userService.removeFromFavorites(id,songInfoDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Edit user profile",
            summary = "Update user attributes.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody final User user) {
        return new ResponseEntity<>(this.userService.update(user), HttpStatus.OK);
    }

    @Operation(description = "Activate - deactivate user",
            summary = "Activate - deactivate user by id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<UserDto> toggleActivateDeactivateUserById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.toggleActivateDeactivate(id), HttpStatus.OK);
    }



    @Operation(description = "Give admin permissions",
            summary = "Give admin permissions to user with provided id. Requires Admin rights.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create-admin")
    public ResponseEntity<?> giveAdminRights(@PathVariable final Long id) {
        return new ResponseEntity<>(userService.createAdmin(id),HttpStatus.OK);
    }

    @Operation(description = "Revoke admin permissions",
            summary = "Revoke admin permissions to user with provided id. Requires Admin rights.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/revoke-admin")
    public ResponseEntity<?> revokeAdminRights(@PathVariable final Long id) {
        return new ResponseEntity<>(userService.revokeAdmin(id),HttpStatus.OK);
    }
}
