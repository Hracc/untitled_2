package com.agregator.Agregator.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test1")
    public ResponseEntity<String> testControl(HttpSession session) {
        return ResponseEntity.ok("УРРРВАААА");
    }
}
