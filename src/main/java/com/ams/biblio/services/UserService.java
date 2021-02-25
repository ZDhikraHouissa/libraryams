package com.ams.biblio.services;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ams.biblio.entities.Role;
import com.ams.biblio.entities.User;
import com.ams.biblio.repositories.RoleRepository;
import com.ams.biblio.repositories.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.save(new Role("USER"));
		sendEmail(user.getEmail());
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	void sendEmail(String emailTo) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(emailTo);

		msg.setSubject("Bienvenue Chez BookStore");
		msg.setText("Bienvenue Chez BookStore, vous pouvez désormais vous connecter à la plateforme via https://libraryams2021.herokuapp.com/");

		javaMailSender.send(msg);
	}

}
