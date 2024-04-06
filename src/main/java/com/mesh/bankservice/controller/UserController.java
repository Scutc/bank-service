package com.mesh.bankservice.controller;

import javax.validation.constraints.Min;
import java.time.LocalDate;

import com.mesh.bankservice.controller.mapper.UserDtoMapper;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.dto.UserDto;
import com.mesh.bankservice.model.dto.UsersDto;
import com.mesh.bankservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final UserDtoMapper userDtoMapper;

    @GetMapping()
    public ResponseEntity<UsersDto> getUsers(@RequestParam(value = "phone", required = false) String phoneNumber,
                                             @RequestParam(value = "email", required = false) String email,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "dateOfBirth", required = false)
                                             @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateOfBirth,
                                             @RequestParam("pageSize") @Min(1) Integer pageSize,
                                             @RequestParam("pageNumber") @Min(0) Integer pageNumber) {
        Page<User> usersPage = userService.findByParams(phoneNumber, email, name, dateOfBirth, pageSize, pageNumber);
        UsersDto usersDto =
            userDtoMapper.fromPageUser(usersPage.getContent(), usersPage.getTotalPages(), usersPage.getSize(), usersPage.getNumber());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

}
