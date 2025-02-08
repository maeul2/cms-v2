/*
package kr.co.yna.cms.v2.yna.authority.domain.service;

import kr.co.yna.cms.v2.yna.authority.domain.entity.Authorization;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Role;
import kr.co.yna.cms.v2.yna.authority.domain.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchAuthorityService {
    private final AuthorityRepository authorityRepository;

    public SearchAuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Set<Authorization> searchAuthorizations(Collection<Long> authIds) {
        return this.authorityRepository.findByIdIn(authIds).stream()
                .filter(authority -> authority instanceof Authorization)
                .map(Authorization.class::cast)
                .collect(Collectors.toSet());
    }

    public Set<Role> searchRoles(Collection<Long> roleIds) {
        return this.authorityRepository.findByIdIn(roleIds).stream()
                .filter(authority -> authority instanceof Role)
                .map(Role.class::cast)
                .collect(Collectors.toSet());
    }
}
*/
