package com.thc.sprbasic2025.service;

import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.dto.PermissionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermittedService {
    void isAble(String target, int func, Long userId);
    boolean permitted(PermissionDto.PermittedReqDto param);
}
