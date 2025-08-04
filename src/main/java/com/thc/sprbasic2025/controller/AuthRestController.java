package com.thc.sprbasic2025.controller;

import com.thc.sprbasic2025.security.AuthService;
import com.thc.sprbasic2025.security.ExternalProperties;
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

    final AuthService authService;
    final ExternalProperties externalProperties;

    @PostMapping("")
    public ResponseEntity<Void> getAccessToken(HttpServletRequest request){
        String accessToken =null;
        String prefix = externalProperties.getTokenPrefix();
        //리프레시 토큰이 유요한지 확인!
        String refreshToken = request.getHeader("RefreshToken");
        System.out.println("1 refreshToken : " + refreshToken);
        if(refreshToken != null && refreshToken.startsWith(prefix)){
            refreshToken = refreshToken.substring(prefix.length());
            System.out.println("2 refreshToken : " + refreshToken);
            accessToken = authService.issueAccessToken(refreshToken);
        }
        return ResponseEntity.status(HttpStatus.OK).header(externalProperties.getAccessKey(), prefix + accessToken).build();
    }

}
