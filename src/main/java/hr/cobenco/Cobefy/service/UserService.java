package hr.cobenco.Cobefy.service;

import hr.cobenco.Cobefy.dto.Mapper;
import hr.cobenco.Cobefy.dto.SongInfoDto;
import hr.cobenco.Cobefy.dto.UserDto;
import hr.cobenco.Cobefy.exeptions.UserException;
import hr.cobenco.Cobefy.model.user.Role;
import hr.cobenco.Cobefy.model.user.User;
import hr.cobenco.Cobefy.repository.RoleRepository;
import hr.cobenco.Cobefy.repository.SongInfoRepository;
import hr.cobenco.Cobefy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SongInfoService songInfoService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void signUp(final User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findRoleByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    @PreAuthorize("hasRole('USER')")
    public void deleteUser(final long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserException("Can't get. User not found!")
        );
        this.userRepository.delete(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createAdmin(final long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserException("Can't get. User not found!")
        );
        Role adminRole = roleRepository.findRoleByName("ADMIN");
        user.getRoles().add(adminRole);
        return Mapper.userToDto(this.userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDto revokeAdmin(final long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserException("Can't get. User not found!")
        );
        Role adminRole = roleRepository.findRoleByName("ADMIN");
        user.getRoles().remove(adminRole);
        return Mapper.userToDto(this.userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDto> getAll() {
        Collection<User> users = this.userRepository.findAll();
        return users.stream()
                .map(Mapper::userToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    public UserDto getById(final Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new UserException("Can't get. User not found!")
        );
        return Mapper.userToDto(user);
    }

    @PreAuthorize("hasRole('USER')")
    public List<SongInfoDto> getFavorites(final String userName) {
        User user = userRepository.findByUsername(userName);
        return user.getFavorites().stream()
                .map(Mapper::songInfoToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    public void addToFavorites(final Long songId, final String userName) {
        User user = userRepository.findByUsername(userName);
        user.getFavorites().add(Mapper.songInfoDtoToEntity(songInfoService.getById(songId)));
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('USER')")
    public void removeFromFavorites(final Long songId, final String userName) {
        User user = userRepository.findByUsername(userName);
        user.getFavorites().remove(Mapper.songInfoDtoToEntity(songInfoService.getById(songId)));
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('USER')")
    public UserDto update(final User user) {
        this.userRepository.findById(user.getId()).orElseThrow(
                () -> new UserException("Can't update. User not found!")
        );
        this.userRepository.save(user);
        return Mapper.userToDto(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDto toggleActivateDeactivate(final Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new UserException("Can't deactivate. User not found!")
        );
        user.setIsActive(!user.getIsActive());
        return Mapper.userToDto(user);
    }


    public User findOne(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(final User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

}
