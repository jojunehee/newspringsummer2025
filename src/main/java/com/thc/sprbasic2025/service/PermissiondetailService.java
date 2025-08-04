package com.thc.sprbasic2025.service;

import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.dto.PermissiondetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissiondetailService {
    void toggle(PermissiondetailDto.ToggleReqDto param, Long reqUserId);
    /**/
    DefaultDto.CreateResDto create(PermissiondetailDto.CreateReqDto param, Long reqUserId);
    void update(PermissiondetailDto.UpdateReqDto param, Long reqUserId);
    void delete(DefaultDto.DeleteReqDto param, Long reqUserId);
    PermissiondetailDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId);
    List<PermissiondetailDto.DetailResDto> list(PermissiondetailDto.ListReqDto param, Long reqUserId);
    DefaultDto.PagedListResDto pagedList(PermissiondetailDto.PagedListReqDto param, Long reqUserId);
    List<PermissiondetailDto.DetailResDto> scrollList(PermissiondetailDto.ScrollListReqDto param, Long reqUserId);
}
