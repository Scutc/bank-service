package com.mesh.bankservice.controller;

import com.mesh.bankservice.model.dto.TransferDto;
import com.mesh.bankservice.service.AccountService;
import com.mesh.bankservice.service.TransferService;
import com.mesh.bankservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{toUser}")
    public ResponseEntity<Void> transferMoney(@RequestHeader(value = "Authorization") String authHeader,
                                              @PathVariable("toUser") Long toUserId,
                                              @RequestBody TransferDto transferDto) {
        String userId = jwtUtil.extractUserId(authHeader);
        accountService.transferMoney(Long.valueOf(userId), toUserId, transferDto.getValue());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
