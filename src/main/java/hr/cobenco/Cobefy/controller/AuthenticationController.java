package hr.cobenco.Cobefy.controller;

import hr.cobenco.Cobefy.dto.AuthDto;
import hr.cobenco.Cobefy.dto.Mapper;
import hr.cobenco.Cobefy.dto.UserDto;
import hr.cobenco.Cobefy.model.user.AuthToken;
import hr.cobenco.Cobefy.model.user.LoginUser;
import hr.cobenco.Cobefy.model.user.User;
import hr.cobenco.Cobefy.security.TokenProvider;
import hr.cobenco.Cobefy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/token")
public class AuthenticationController {
    public String currentUsername;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;


    @Operation(description = "Generate user token", summary = "log in by providing username and password.")
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        currentUsername = jwtTokenUtil.getUsernameFromToken(token);
        AuthDto authDto = new AuthDto(new AuthToken(token),
                Mapper.userToDto(userService.findOne(jwtTokenUtil.getUsernameFromToken(token))));
        return ResponseEntity.ok(authDto);
    }

    @Operation(description = "Returns current user", summary = "Returns currently logged in user.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest request) {
        User user = userService.findOne(request.getUserPrincipal().getName());

        UserDto userDto = Mapper.userToDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
