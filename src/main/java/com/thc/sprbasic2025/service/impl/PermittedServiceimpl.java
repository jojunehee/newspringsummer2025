package com.thc.sprbasic2025.service.impl;

import com.thc.sprbasic2025.dto.PermissionDto;
import com.thc.sprbasic2025.exception.NoPermissionException;
import com.thc.sprbasic2025.mapper.PermissionMapper;
import com.thc.sprbasic2025.service.PermittedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PermittedServiceimpl implements PermittedService {

    final PermissionMapper permissionMapper;

    @Override
    public void isAble(String target, int func, Long userId) {
        boolean tempPermitted =  permitted(PermissionDto.PermittedReqDto.builder().userId(userId).target(target).func(func).build());
        if(!tempPermitted){
            throw new NoPermissionException("no permission");
        }
    }

    @Override
    public boolean permitted(PermissionDto.PermittedReqDto param) {
        return permissionMapper.permitted(param) > 0;
    }
}
