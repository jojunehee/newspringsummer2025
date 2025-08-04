package com.thc.sprbasic2025.controller;

import com.thc.sprbasic2025.util.TokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthRestController {

    final TokenFactory tokenFactory;

    @PostMapping("")
    public ResponseEntity<Void> getAccessToken(HttpServletRequest request){
        //리프레시 토큰이 유요한지 확인!
        String refreshToken = request.getHeader("RefreshToken");
        String accessKey = tokenFactory.generateAccessKey(refreshToken);

        return ResponseEntity.status(HttpStatus.OK).header("Authorization",accessKey).build();
    }

}
