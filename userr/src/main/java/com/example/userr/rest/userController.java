package com.example.userr.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.userr.entities.ERole;
import com.example.userr.entities.Role;
import com.example.userr.entities.Users;
import com.example.userr.repositories.RoleRepository;
import com.example.userr.repositories.TokenRepository;
import com.example.userr.repositories.UserRepository;
import com.example.userr.request.SignupRequest;
import com.example.userr.response.MessageResponse;
import com.example.userr.services.UserService;
/*
import org.lsi.entities.Role;
import org.lsi.entities.ERole;
import org.lsi.entities.Users;
import org.lsi.services.UserService;
import org.lsi.repositories.UserRepository;
import org.lsi.repositories.RoleRepository;
import org.lsi.request.SignupRequest;
import org.lsi.response.MessageResponse;
import org.lsi.repositories.TokenRepository;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class userController {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userServ;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository ;
	@PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(@RequestBody SignupRequest users) {
		if (userRepository.existsByUsername(users.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		Set<String> strRoles = users.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			ArrayList<String> listRoles = new ArrayList<>(strRoles);

			switch (listRoles.get(0)) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
			}
		}

		Users user = new Users(users.getNom(), users.getPrenom(), users.getUsername(), encoder.encode(users.getPassword()),
				users.getCIN(), users.getTel(), users.getAccount_address());
		user.setRoles(roles);
		user = userServ.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
		Users user1 = tokenRepository.findByToken(token).get().getUser();
		ERole erole = ERole.ROLE_ADMIN;
		ERole baserole = ERole.ROLE_ADMIN;
		for (Role b : user1.getRoles()) {
			erole = b.getName();
		}
		if (erole.equals(baserole)) {
			List<Users> users = userServ.getAll();
			if (users.size() > 0) {
				return new ResponseEntity<>(users, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<Users> updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @RequestBody SignupRequest users) {
		Users user2 = tokenRepository.findByToken(token).get().getUser();
		ERole erole = ERole.ROLE_ADMIN;
		ERole baserole = ERole.ROLE_ADMIN;
		ERole baserole1 = ERole.ROLE_USER;
		for (Role b : user2.getRoles()) {
			erole = b.getName();
		}
		if (erole.equals(baserole) || erole.equals(baserole1)) {
			Set<String> strRoles = users.getRoles();
			Set<Role> roles = new HashSet<>();
			if (strRoles == null) {
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				ArrayList<String> listRoles = new ArrayList<>(strRoles);

				switch (listRoles.get(0)) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			}

			//Users user = new Users(users.getNom(), users.getPrenom(), users.getUsername(), users.getCIN(), users.getTel(), users.getAccount_address());
			Users user = new Users(users.getNom(), users.getPrenom(),users.getPassword(), users.getUsername(), users.getCIN(), users.getTel(), users.getAccount_address());

			user.setRoles(roles);
			if (users.getPassword() != null && users.getPassword().length() != 0) {
				System.out.println(users.getPassword().length());
				user.setPassword(encoder.encode(users.getPassword()));
			}
			Users user1 = userServ.update(id, user);
			return new ResponseEntity<>(user1, HttpStatus.OK);
		}
		return null;
	}



	@GetMapping("/getUserById/{id}")
	public ResponseEntity<Users> getUserById(@RequestHeader("Authorization") String token, @PathVariable("id") String id) {
		Users user1 = tokenRepository.findByToken(token).get().getUser();
		ERole erole = ERole.ROLE_ADMIN;
		ERole baserole = ERole.ROLE_ADMIN;
		for (Role b : user1.getRoles()) {
			erole = b.getName();
		}
		if (erole.equals(baserole)) {
			Optional<Users> users = userServ.getById(id);

			if (users.isPresent()) {
				return new ResponseEntity<>(users.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@GetMapping("/getUserByUsername/{username}")
	public ResponseEntity<Users> getUserByUsername(@RequestHeader("Authorization") String token, @PathVariable("username") String username) {
		Users user1 = tokenRepository.findByToken(token).get().getUser();
		ERole erole = ERole.ROLE_ADMIN;
		ERole baserole = ERole.ROLE_ADMIN;
		for (Role b : user1.getRoles()) {
			erole = b.getName();
		}
		if (erole.equals(baserole)) {
			Optional<Users> users = userServ.getByUsername(username);

			if (users.isPresent()) {
				return new ResponseEntity<>(users.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
}
