package com.thc.sprbasic2025.service.impl;

import com.thc.sprbasic2025.domain.Permissiondetail;
import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.dto.PermissiondetailDto;
import com.thc.sprbasic2025.exception.NoMatchingDataException;
import com.thc.sprbasic2025.mapper.PermissiondetailMapper;
import com.thc.sprbasic2025.repository.PermissiondetailRepository;
import com.thc.sprbasic2025.service.PermissiondetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissiondetailServiceimpl implements PermissiondetailService {

    final PermissiondetailRepository permissiondetailRepository;
    final PermissiondetailMapper permissiondetailMapper;

    @Override
    public void toggle(PermissiondetailDto.ToggleReqDto param, Long reqUserId) {
        if(param.getAlive()){
            //켜주세요!
            create(PermissiondetailDto.CreateReqDto.builder().permissionId(param.getPermissionId()).target(param.getTarget()).func(param.getFunc()).build(), reqUserId);
        } else {
            //지워주세요!
            Permissiondetail permissiondetail = permissiondetailRepository.findByPermissionIdAndTargetAndFunc(param.getPermissionId(), param.getTarget(), param.getFunc());
            if(permissiondetail != null){
                permissiondetail.setDeleted(true);
                permissiondetailRepository.save(permissiondetail);
            }
        }
    }

    /**/

    @Override
    public DefaultDto.CreateResDto create(PermissiondetailDto.CreateReqDto param, Long reqUserId) {
        Permissiondetail permissiondetail = permissiondetailRepository.findByPermissionIdAndTargetAndFunc(param.getPermissionId(), param.getTarget(), param.getFunc());
        if(permissiondetail != null){
            if(permissiondetail.getDeleted()){
                permissiondetail.setDeleted(false);
                permissiondetailRepository.save(permissiondetail);
            }
            return permissiondetail.toCreateResDto();
        }

        DefaultDto.CreateResDto res = permissiondetailRepository.save(param.toEntity()).toCreateResDto();
        return res;
    }

    @Override
    public void update(PermissiondetailDto.UpdateReqDto param, Long reqUserId) {
        Permissiondetail permissiondetail = permissiondetailRepository.findById(param.getId()).orElse(null);
        if(permissiondetail == null){
            throw new NoMatchingDataException("no data");
        }
        permissiondetail.update(param);
        permissiondetailRepository.save(permissiondetail);
    }

    @Override
    public void delete(DefaultDto.DeleteReqDto param, Long reqUserId) {
        update(PermissiondetailDto.UpdateReqDto.builder().id(param.getId()).deleted(true).build(), reqUserId);
    }

    public PermissiondetailDto.DetailResDto get(DefaultDto.DetailReqDto param, Long reqUserId) {
        PermissiondetailDto.DetailResDto res = permissiondetailMapper.detail(param.getId());
        return res;
    }
    @Override
    public PermissiondetailDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId) {
        return get(param, reqUserId);
    }

    @Override
    public List<PermissiondetailDto.DetailResDto> list(PermissiondetailDto.ListReqDto param, Long reqUserId) {
        return detailList(permissiondetailMapper.list(param), reqUserId);
    }
    public List<PermissiondetailDto.DetailResDto> detailList(List<PermissiondetailDto.DetailResDto> list, Long reqUserId){
        List<PermissiondetailDto.DetailResDto> newList = new ArrayList<>();
        for(PermissiondetailDto.DetailResDto each : list){
            newList.add(get(DefaultDto.DetailReqDto.builder().id(each.getId()).build(), reqUserId));
        }
        return newList;
    }

    @Override
    public DefaultDto.PagedListResDto pagedList(PermissiondetailDto.PagedListReqDto param, Long reqUserId) {
        DefaultDto.PagedListResDto res = param.init(permissiondetailMapper.pagedListCount(param));
        res.setList(detailList(permissiondetailMapper.pagedList(param), reqUserId));
        return res;
    }

    @Override
    public List<PermissiondetailDto.DetailResDto> scrollList(PermissiondetailDto.ScrollListReqDto param, Long reqUserId) {
        param.init();
        return detailList(permissiondetailMapper.scrollList(param), reqUserId);
    }
}
