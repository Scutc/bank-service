package com.mesh.bankservice.controller;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.mesh.bankservice.mapper.UserDtoMapper;
import com.mesh.bankservice.model.UsersPage;
import com.mesh.bankservice.model.dto.EmailDataDto;
import com.mesh.bankservice.model.dto.PhoneDataDto;
import com.mesh.bankservice.model.dto.UpdateEmailDto;
import com.mesh.bankservice.model.dto.UpdatePhoneDto;
import com.mesh.bankservice.model.dto.UsersDto;
import com.mesh.bankservice.service.EmailDataService;
import com.mesh.bankservice.service.PhoneDataService;
import com.mesh.bankservice.service.UserService;
import com.mesh.bankservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final PhoneDataService phoneDataService;
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
        UsersPage usersPage = userService.findByParams(phoneNumber, email, name, dateOfBirth, pageSize, pageNumber);
        UsersDto usersDto = userDtoMapper.fromUsersPage(usersPage);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping("/email")
    ResponseEntity<Void> addEmail(@RequestHeader(value = "Authorization") String authHeader,
                                  @Valid @RequestBody EmailDataDto emailDataDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        emailDataService.add(emailDataDto.getEmail(), Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/email/{currentEmail}")
    ResponseEntity<Void> updateEmail(@RequestHeader(value = "Authorization") String authHeader,
                                     @PathVariable("currentEmail") String currentEmail,
                                     @Valid @RequestBody UpdateEmailDto updateEmailDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        emailDataService.update(currentEmail, updateEmailDto.getNewEmail(), Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/email/{email}")
    ResponseEntity<Void> deleteEmail(@RequestHeader(value = "Authorization") String authHeader,
                                     @PathVariable("email") String email) {
        String userId = jwtUtil.extractUserId(authHeader);
        emailDataService.delete(email, Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/phone")
    ResponseEntity<Void> addPhone(@RequestHeader(value = "Authorization") String authHeader,
                                  @Valid @RequestBody PhoneDataDto phoneDataDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        phoneDataService.add(phoneDataDto.getPhone(), Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/phone/{currentPhone}")
    ResponseEntity<Void> updatePhone(@RequestHeader(value = "Authorization") String authHeader,
                                     @PathVariable("currentPhone") String currentPhone,
                                     @Valid @RequestBody UpdatePhoneDto updatePhoneDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        phoneDataService.update(currentPhone, updatePhoneDto.getNewPhone(), Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/phone/{phone}")
    ResponseEntity<Void> deletePhone(@RequestHeader(value = "Authorization") String authHeader,
                                     @PathVariable("phone") String phone) {
        String userId = jwtUtil.extractUserId(authHeader);
        phoneDataService.delete(phone, Long.valueOf(userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
