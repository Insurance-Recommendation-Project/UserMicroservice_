package com.example.userr.services;

import com.example.userr.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.lsi.entities.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {



	Users save(Users users);
	List<Users> getAll();
	Users update(String id, Users users);
	Optional<Users> getById(String id);
	Optional<Users> getByUsername(String username);


}
