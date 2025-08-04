package com.thc.sprbasic2025.repository;

import com.thc.sprbasic2025.domain.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleTypeRepository extends JpaRepository<UserRoleType, String> {
}