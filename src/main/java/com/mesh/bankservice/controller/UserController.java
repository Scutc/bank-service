package com.mesh.bankservice.controller;

import javax.validation.constraints.Min;
import java.time.LocalDate;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.dto.UserDto;
import com.mesh.bankservice.model.dto.UsersDto;
import com.mesh.bankservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UsersDto> getUsers(@RequestParam(value = "phone", required = false) String phoneNumber,
                                             @RequestParam(value = "email", required = false) String email,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
                                             @RequestParam("pageSize") @Min(1) Integer pageSize,
                                             @RequestParam("pageNumber") @Min(0) Integer pageNumber) {
        Page<User> usersPage= userService.findByParams(phoneNumber, email, name, dateOfBirth, pageSize, pageNumber);
        UserDto userDto = new UserDto();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
