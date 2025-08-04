package com.thc.sprbasic2025.domain;

import com.thc.sprbasic2025.dto.DefaultDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
public class User extends AuditingFields{
    String username;
    String password;
    String name;
    String nick;
    String phone;

    //fetch 타입 바꾸고, toString 순환 참조 수정
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<UserRoleType> userRoleType = new ArrayList<>();

    //권한 관련한 기능 추가
    public List<UserRoleType> getRoleList(){
        if(!this.userRoleType.isEmpty()){
            return userRoleType;
        }
        return new ArrayList<>();
    }

    protected User(){}
    private User(Boolean deleted, String username, String password, String name, String nick, String phone){
        this.deleted = deleted;
        this.username = username;
        this.password = password;
        this.name = name;
        this.nick = nick;
        this.phone = phone;
    }
    public static User of(String username, String password, String name, String nick, String phone){
        return new User(false, username, password, name, nick, phone);
    }
    public DefaultDto.CreateResDto toCreateResDto() {
        return DefaultDto.CreateResDto.builder().id(getId()).build();
    }
}
