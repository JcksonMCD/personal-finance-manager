package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.model.User;
import com.jackson.personal_finance_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserRegistrationDTO> createUser(@RequestBody UserRegistrationDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(
            @PathVariable long id,
            @RequestBody UserRegistrationDTO user
    ){
        return ResponseEntity.ok(userService.editUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        userService.deleteUserById(id);

        return ResponseEntity.ok("User deleted at id: "+ id);
    }
}
