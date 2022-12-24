package hr.cobenco.Cobefy;

import hr.cobenco.Cobefy.model.user.Role;
import hr.cobenco.Cobefy.model.user.User;
import hr.cobenco.Cobefy.repository.RoleRepository;
import hr.cobenco.Cobefy.repository.UserRepository;
import hr.cobenco.Cobefy.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CobefyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CobefyApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(RoleRepository roleRepository, UserService userService, UserRepository userRepository) {
//		return args -> {
//
//			//Load authorities
//			Role adminRole = new Role();
//			adminRole.setName("ADMIN");
//			adminRole.setId(1L);
//			Role userRole = new Role();
//			userRole.setName("USER");
//			userRole.setId(2L);
//			roleRepository.save(adminRole);
//			roleRepository.save(userRole);
//
//			//Create test user
//			User user1 = new User();
//			user1.setUsername("user");
//			user1.setPassword("user123");
//			user1.setEmail("email@email.com");
//			Role role = roleRepository.findRoleByName("USER");
//			Set<Role> roles = new HashSet<>();
//			roles.add(role);
//			user1.setRoles(roles);
//			userService.signUp(user1);
//
//
//		};
//	}
}
