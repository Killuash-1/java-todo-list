package br.com.killuash1.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
/**
 * Modificador
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {
  
  @Autowired
  private IUserRepository userRepository;
  @PostMapping("/")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity create(@RequestBody UserModel userModel){
    var user = this.userRepository.findByUsername(userModel.getUsername());
    if (user != null) {
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
    }
    var passwordHashed = BCrypt.withDefaults()
    .hashToString(12, userModel.getPassword().toCharArray());
    userModel.setPassword(passwordHashed);
    var userCreated = this.userRepository.save(userModel);
    
  return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

}


}