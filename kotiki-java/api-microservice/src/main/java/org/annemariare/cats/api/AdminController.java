package org.annemariare.cats.api;

import org.annemariare.cats.dto.UserDto;
import org.annemariare.cats.exception.EntityAlreadyExistsException;
import org.annemariare.cats.rabbitmq.CatSender;
import org.annemariare.cats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    @Autowired
    private UserService service;

    @Autowired
    private CatSender sender;

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody UserDto user) {
        try {
            sender.send(user);
            service.register(user);
            return ResponseEntity.ok("User is successfully registered");
        } catch(EntityAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("Error -.-");
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(service.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error -.-");
        }
    }
}
