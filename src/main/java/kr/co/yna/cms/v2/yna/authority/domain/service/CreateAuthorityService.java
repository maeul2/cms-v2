/*
package kr.co.yna.cms.v2.yna.authority.domain.service;

import kr.co.yna.cms.v2.yna.authority.domain.entity.Authorization;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Role;
import kr.co.yna.cms.v2.yna.authority.domain.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAuthorityService {
    private final AuthorityRepository authorityRepository;

    public CreateAuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authorization createAuthorization(Authorization authorization) {
        return (Authorization) this.authorityRepository.save(authorization);
    }

    public Role createRole(Role role) {
        return (Role) this.authorityRepository.save(role);
    }
}
*/
