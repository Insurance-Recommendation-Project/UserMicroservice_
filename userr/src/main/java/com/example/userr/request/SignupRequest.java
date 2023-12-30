package com.example.userr.request;

import java.util.Set;


public class SignupRequest {
       private String username;
 
    private String nom;
    

    private String prenom;
    

    private String tel;
   // private String email;
 
    /*public String getEmail() {
		return email;
	}*/

	/*public void setEmail(String email) {
		this.email = email;
	}*/

	private String account_address;
    
    private Set<String> roles;
    
    private String CIN;
    
    public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	private String password;
    
  
    public String getAccount_address() {
		return account_address;
	}

	public void setAccount_address(String acount_address) {
		this.account_address = acount_address;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRole(Set<String> roles) {
      this.roles = roles;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
    
    
}
