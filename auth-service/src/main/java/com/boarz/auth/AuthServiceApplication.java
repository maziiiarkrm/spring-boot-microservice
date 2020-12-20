package com.boarz.auth;

import com.boarz.auth.entity.Role;
import com.boarz.auth.entity.User;
import com.boarz.auth.entity.UserUsernameType;
import com.boarz.auth.repo.RoleRepo;
import com.boarz.auth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired private RoleRepo roleRepo;
	@Autowired private UserRepo userRepo;
	@Autowired private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void createAdmin(){
		if (!roleRepo.existsByName("ADMIN")){
			roleRepo.save(new Role("USER","User role of auth-server"));
			Role adminRole = roleRepo.save(new Role("ADMIN", "Admin role of auth-server"));
			User adminUser = new User();
			adminUser.setUsername("09110000000");
			adminUser.setPhoneNumber("09110000000");
			adminUser.setPassword(passwordEncoder.encode("ADMIN_USER_PASSWORD"));
			adminUser.setActive(true);
			adminUser.setVerified(true);
			adminUser.setRoles(Arrays.asList(adminRole));
			adminUser.setType(UserUsernameType.PHONE_NUMBER);
			userRepo.save(adminUser);
		}
	}

}
