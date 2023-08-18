package TaskManagementSystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import TaskManagementSystem.entity.Login;
import TaskManagementSystem.service.LoginService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
	@Autowired
	LoginService logServ;

	@PostMapping("/authenticate")
	public ResponseEntity<?> checkCredentials(@RequestBody Login theLog) {
		Login log = this.logServ.findByKey(theLog.getUsername(), theLog.getPassword());
		return ResponseEntity.ok(log);
	}

	@PostMapping
	public ResponseEntity<?> newCredential(@Valid @RequestBody Login theLog, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder errors = new StringBuilder();
			bindingResult.getFieldErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
			return ResponseEntity.badRequest().body(errors.toString());
		}
		Login log = this.logServ.save(theLog);
		return ResponseEntity.ok(log);
	}

	@GetMapping
	public ResponseEntity<?> getAllCredentials() {
		return ResponseEntity.ok(this.logServ.findAll());
	}

	@DeleteMapping
	public String deleteCredential(@RequestBody Login theLog) {
		this.logServ.deleteByKey(theLog.getUsername());
		return "Credentials Deleted";
	}
}