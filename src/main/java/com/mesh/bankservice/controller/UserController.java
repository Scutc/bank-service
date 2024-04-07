package com.mesh.bankservice.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;

import com.mesh.bankservice.controller.mapper.UserDtoMapper;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.dto.EmailDataDto;
import com.mesh.bankservice.model.dto.UpdateEmailDto;
import com.mesh.bankservice.model.dto.UsersDto;
import com.mesh.bankservice.service.EmailDataService;
import com.mesh.bankservice.service.UserService;
import com.mesh.bankservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailDataService emailDataService;
    private final UserDtoMapper userDtoMapper;
    private final JwtUtil jwtUtil;

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

    @PostMapping("/email")
    ResponseEntity<Void> addEmail(@RequestHeader(value = "Authorization") String authHeader,
                                  @RequestBody @Valid EmailDataDto emailDataDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        emailDataService.addEmail(emailDataDto.getEmail(), Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/email/{currentEmail}")
    ResponseEntity<Void> updateEmail(@RequestHeader(value = "Authorization") String authHeader,
                                     @PathVariable("currentEmail") String currentEmail,
                                     @RequestBody @Valid UpdateEmailDto updateEmailDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        emailDataService.updateEmail(currentEmail, updateEmailDto.getNewEmail(), Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
