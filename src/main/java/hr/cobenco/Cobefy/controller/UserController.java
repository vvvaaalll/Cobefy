package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.CreateUserDto;
import hr.cobenco.Cobefy.dto.Mapper;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.dto.UserDto;
import hr.cobenco.Cobefy.model.user.User;
import hr.cobenco.Cobefy.service.UserService;
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



    @PostMapping("/signup")
    public void signUpUser(@RequestBody final CreateUserDto createUserDto) {
        this.userService.signUp(Mapper.CreateUserToEntity(createUserDto));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.getById(id), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}/favorites")
    public ResponseEntity<List<SongInfoDto>> getFavorites(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.getFavorites(id), HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody final User user) {
        return new ResponseEntity<>(this.userService.update(user), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<UserDto> deactivateUserById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.deactivate(id), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create-admin")
    public void createAdminAccount() {
        userService.createAdmin();
    }
}
