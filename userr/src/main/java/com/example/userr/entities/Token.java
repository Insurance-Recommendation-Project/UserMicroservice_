package com.example.userr.entities;

import com.example.userr.services.UserService;
import jakarta.persistence.*;

/*
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
*/
@Entity
@Table(name = "user_token")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NotBlank
//	@Size(max = 20)
	private String token;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;  // Change from Users to User

	public Token() {
	}

	public Token(String jwt, Users user) {
		this.token = jwt;
		this.user = user;
	}

	public Token(Long id, String token, Users user) {
		super();
		this.id = id;
		this.token = token;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
