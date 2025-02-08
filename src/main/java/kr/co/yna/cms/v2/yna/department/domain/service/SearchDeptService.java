package kr.co.yna.cms.v2.yna.department.domain.service;

import kr.co.yna.cms.v2.yna.department.domain.entity.Department;
import kr.co.yna.cms.v2.yna.department.domain.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchDeptService {
    private final DepartmentRepository departmentRepository;

    public SearchDeptService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Optional<Department> search(String deptId) {
        return this.departmentRepository.findById(deptId);
    }
}
