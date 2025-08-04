package com.thc.sprbasic2025.controller;

import com.thc.sprbasic2025.dto.PermissionDto;
import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.security.PrincipalDetails;
import com.thc.sprbasic2025.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/permission")
@RestController
public class PermissionRestController {

    final PermissionService permissionService;
    public PermissionRestController(PermissionService permissionService){
        this.permissionService = permissionService;
    }

    public Long getReqUserId(PrincipalDetails principalDetails){
        Long reqUserId = null;
        if(principalDetails != null){
            reqUserId = principalDetails.getUser().getId();
        }
        return reqUserId;
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody PermissionDto.CreateReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(permissionService.create(params, reqUserId));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody PermissionDto.UpdateReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        permissionService.update(params, reqUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody DefaultDto.DeleteReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        permissionService.delete(params, reqUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/detail")
    public ResponseEntity<PermissionDto.DetailResDto> detail(DefaultDto.DetailReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(permissionService.detail(params, reqUserId));
    }


    //@PreAuthorize("permitAll()")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<PermissionDto.DetailResDto>> list(PermissionDto.ListReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(permissionService.list(params, reqUserId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pagedList")
    public ResponseEntity<DefaultDto.PagedListResDto> pagedList(PermissionDto.PagedListReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(permissionService.pagedList(params, reqUserId));
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/scrollList")
    public ResponseEntity<List<PermissionDto.DetailResDto>> scrollList(PermissionDto.ScrollListReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(permissionService.scrollList(params, reqUserId));
    }

}
