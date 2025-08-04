package com.thc.sprbasic2025.domain;

import com.thc.sprbasic2025.dto.DefaultDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
public class RefreshToken extends AuditingFields{
    Long userId;
    @Column(unique=true) String content;

    protected RefreshToken(){}
    private RefreshToken(Long userId, String content){
        this.userId = userId;
        this.content = content;
    }
    public static RefreshToken of(Long userId, String content){
        return new RefreshToken(userId, content);
    }

    public DefaultDto.CreateResDto toCreateResDto() {
        return DefaultDto.CreateResDto.builder().id(getId()).build();
    }

}
