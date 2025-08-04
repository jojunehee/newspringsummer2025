package com.thc.sprbasic2025.mapper;

import com.thc.sprbasic2025.dto.PermissionDto;

import java.util.List;

public interface PermissionMapper {
	PermissionDto.DetailResDto detail(Long id);
	List<PermissionDto.DetailResDto> list(PermissionDto.ListReqDto param);

	List<PermissionDto.DetailResDto> pagedList(PermissionDto.PagedListReqDto param);
	int pagedListCount(PermissionDto.PagedListReqDto param);
	List<PermissionDto.DetailResDto> scrollList(PermissionDto.ScrollListReqDto param);


}