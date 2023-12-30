package com.example.userr.services;
/*
import org.lsi.entities.Users;
import org.lsi.repositories.UserRepository;
*/
import com.example.userr.entities.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

 import com.example.userr.entities.Users;
 import com.example.userr.repositories.UserRepository;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRep;
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public Users save(Users users) {
		users = userRep.save(users);
		return users;
	}


	@Override
	public List<Users> getAll() {
		// TODO Auto-generated method stub
		return userRep.findAll();
	}

	@Override
	public Users update(String id, Users users) {
		// TODO Auto-generated method stub
		Long longId = Long.parseLong(id);
		Users existingUser = usersRepository.findById(longId).orElse(null);

		//Users existingUser = userRep.findById(id).orElse(null);
		if (existingUser != null) {
			existingUser.setNom(users.getNom());
			existingUser.setPrenom(users.getPrenom());
			existingUser.setCIN(users.getCIN());
			existingUser.setUsername(users.getUsername());
			if (users.getPassword() != null && users.getPassword().length() != 0) {
				System.out.println("Password");
				existingUser.setPassword(users.getPassword());
			}
			existingUser.setTel(users.getTel());
			existingUser.setRoles(users.getRoles());
			userRep.save(existingUser);
		}
		return existingUser;
	}

	@Override
	public Optional<Users> getById(String id) {
		Long longId1 = Long.parseLong(id);
		return usersRepository.findById(longId1);

	}



	@Override
	public Optional<Users> getByUsername(String username) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRep.findByUsername(username));
	}

}
