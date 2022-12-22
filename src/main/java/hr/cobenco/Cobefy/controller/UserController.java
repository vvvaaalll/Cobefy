package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.UserDto;
import hr.cobenco.Cobefy.model.user.User;
import hr.cobenco.Cobefy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;



    @PostMapping("/signup")
    public void signUpUser(@RequestBody final User user) {
        this.userService.signUp(user);
    }

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.getById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody final User user) {
        return new ResponseEntity<>(this.userService.update(user), HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<UserDto> deactivateUserById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.userService.deactivate(id), HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public void createAdminAccount() {
        userService.createAdmin();
    }
}
