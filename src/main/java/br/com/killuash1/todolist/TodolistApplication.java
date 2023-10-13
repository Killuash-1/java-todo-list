package br.com.killuash1.todolist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.killuash1.todolist.user.IUserRepository;
import br.com.killuash1.todolist.user.UserModel;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
	CommandLineRunner initDataBase(IUserRepository userRepository){
		return args ->{
			UserModel u = new UserModel();
			u.setUsername("admin");
			u.setName("Admin");
			u.setPassword("admin");
			var passwordHashed = BCrypt.withDefaults()
		 .hashToString(12, u.getPassword().toCharArray());
			u.setPassword(passwordHashed);
			userRepository.save(u);
		};
	}

}
