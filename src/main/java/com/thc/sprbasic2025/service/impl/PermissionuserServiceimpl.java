package com.thc.sprbasic2025.service.impl;

import com.thc.sprbasic2025.domain.Permissionuser;
import com.thc.sprbasic2025.domain.User;
import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.dto.PermissionuserDto;
import com.thc.sprbasic2025.exception.NoMatchingDataException;
import com.thc.sprbasic2025.mapper.PermissionuserMapper;
import com.thc.sprbasic2025.repository.PermissionuserRepository;
import com.thc.sprbasic2025.repository.UserRepository;
import com.thc.sprbasic2025.service.PermissionuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionuserServiceimpl implements PermissionuserService {

    final PermissionuserRepository permissionuserRepository;
    final PermissionuserMapper permissionuserMapper;
    final UserRepository userRepository;

    /**/

    @Override
    public DefaultDto.CreateResDto create(PermissionuserDto.CreateReqDto param, Long reqUserId) {
        Long userId = param.getUserId();
        if(userId == null){
            User user = userRepository.findByUsername(param.getUsername());
            if(user != null){
                param.setUserId(user.getId());
            }
        }

        Permissionuser permissionuser = permissionuserRepository.findByPermissionIdAndUserId(param.getPermissionId(), param.getUserId());
        if(permissionuser != null){
            if(permissionuser.getDeleted()){
                permissionuser.setDeleted(false);
                permissionuserRepository.save(permissionuser);
            }
            return permissionuser.toCreateResDto();
        }

        DefaultDto.CreateResDto res = permissionuserRepository.save(param.toEntity()).toCreateResDto();
        return res;
    }

    @Override
    public void update(PermissionuserDto.UpdateReqDto param, Long reqUserId) {
        Permissionuser permissionuser = permissionuserRepository.findById(param.getId()).orElse(null);
        if(permissionuser == null){
            throw new NoMatchingDataException("no data");
        }
        permissionuser.update(param);
        permissionuserRepository.save(permissionuser);
    }

    @Override
    public void delete(DefaultDto.DeleteReqDto param, Long reqUserId) {
        update(PermissionuserDto.UpdateReqDto.builder().id(param.getId()).deleted(true).build(), reqUserId);
    }

    public PermissionuserDto.DetailResDto get(DefaultDto.DetailReqDto param, Long reqUserId) {
        PermissionuserDto.DetailResDto res = permissionuserMapper.detail(param.getId());
        return res;
    }
    @Override
    public PermissionuserDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId) {
        return get(param, reqUserId);
    }

    @Override
    public List<PermissionuserDto.DetailResDto> list(PermissionuserDto.ListReqDto param, Long reqUserId) {
        return detailList(permissionuserMapper.list(param), reqUserId);
    }
    public List<PermissionuserDto.DetailResDto> detailList(List<PermissionuserDto.DetailResDto> list, Long reqUserId){
        List<PermissionuserDto.DetailResDto> newList = new ArrayList<>();
        for(PermissionuserDto.DetailResDto each : list){
            newList.add(get(DefaultDto.DetailReqDto.builder().id(each.getId()).build(), reqUserId));
        }
        return newList;
    }

    @Override
    public DefaultDto.PagedListResDto pagedList(PermissionuserDto.PagedListReqDto param, Long reqUserId) {
        DefaultDto.PagedListResDto res = param.init(permissionuserMapper.pagedListCount(param));
        res.setList(detailList(permissionuserMapper.pagedList(param), reqUserId));
        return res;
    }

    @Override
    public List<PermissionuserDto.DetailResDto> scrollList(PermissionuserDto.ScrollListReqDto param, Long reqUserId) {
        param.init();
        return detailList(permissionuserMapper.scrollList(param), reqUserId);
    }
}
