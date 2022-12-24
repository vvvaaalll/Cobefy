package hr.cobenco.Cobefy.model.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String lastName;

    @NotNull
    private Boolean isActive = true;

    private String avatarUrl;

    @NotNull
    private LocalDate dateOfSignUp = LocalDate.now();

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;

    public User (String username, String email,
                 String password, String firstName,
                 String lastName, String avatarUrl) {
      this.username = username;
      this.email = email;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.avatarUrl = avatarUrl;
    }
}
