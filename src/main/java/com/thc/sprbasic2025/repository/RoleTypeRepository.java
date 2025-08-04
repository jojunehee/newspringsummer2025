package com.thc.sprbasic2025.repository;

import com.thc.sprbasic2025.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleTypeRepository extends JpaRepository<RoleType, String>{
	RoleType findByTypeName(String typeName);
}
