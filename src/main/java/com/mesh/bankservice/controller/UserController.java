package com.mesh.bankservice.controller;

import javax.validation.constraints.Min;
import java.time.LocalDate;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.dto.UsersDto;
import com.mesh.bankservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public ResponseEntity<UsersDto> getUsers(@RequestParam("phone") String phoneNumber,
                                             @RequestParam("email") String email,
                                             @RequestParam("name") String name,
                                             @RequestParam("dateOfBirth") LocalDate dateOfBirth,
                                             @RequestParam("pageSize") @Min(1) Integer pageSize,
                                             @RequestParam("pageNumber") @Min(0) Integer pageNumber) {
        Page<User> usersPage= userService.findUsersByParams(phoneNumber, email, name, dateOfBirth, pageSize, pageNumber);
    }

}
