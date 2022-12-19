package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.Validator;

@RestController
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Validator validator;

    @Autowired
    public SecurityController(UserRepository userRepository, PasswordEncoder encoder, Validator validator) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.validator = validator;
    }

    @PostMapping("/api/register")
    public ResponseEntity register(@RequestBody User user) {
        if (!validator.validateEmail(user.getEmail()) || !validator.validatePassword(user.getPassword()) || userRepository.existsById(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
