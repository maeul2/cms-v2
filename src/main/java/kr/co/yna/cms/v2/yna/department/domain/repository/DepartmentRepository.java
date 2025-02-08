package kr.co.yna.cms.v2.yna.department.domain.repository;

import kr.co.yna.cms.v2.yna.department.domain.entity.Department;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface DepartmentRepository extends Repository<Department, String> {
    Optional<Department> findById(String id);
}
