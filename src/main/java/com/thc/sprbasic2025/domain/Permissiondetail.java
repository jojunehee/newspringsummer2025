package com.thc.sprbasic2025.domain;

import com.thc.sprbasic2025.dto.DefaultDto;
import com.thc.sprbasic2025.dto.PermissiondetailDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(indexes = {
        @Index(columnList = "deleted")
}
        ,uniqueConstraints= {
        @UniqueConstraint(name = "UQ_permissiondetail_permissionId_target_func", columnNames = {"permissionId", "target", "func"})
}
)
@Entity
public class Permissiondetail extends AuditingFields{
    Long permissionId;
    String target; // 어떤 테이블에 대한 권한을 줄지 ex) notice, user, faq
    Integer func; // 110은 입력, 120은 수정, 200은 조회!

    protected Permissiondetail(){}
    private Permissiondetail(Long permissionId, String target, Integer func){
        this.permissionId = permissionId;
        this.target = target;
        this.func = func;
    }
    public static Permissiondetail of(Long permissionId, String target, Integer func){
        return new Permissiondetail(permissionId, target, func);
    }

    public DefaultDto.CreateResDto toCreateResDto() {
        return DefaultDto.CreateResDto.builder().id(getId()).build();
    }

    public void update(PermissiondetailDto.UpdateReqDto param){
        if(param.getDeleted() != null){ setDeleted(param.getDeleted()); }
        if(param.getTarget() != null){ setTarget(param.getTarget()); }
        if(param.getFunc() != null){ setFunc(param.getFunc()); }
    }
}
