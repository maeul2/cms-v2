/*
package kr.co.yna.cms.v2.yna.authority.application;

import kr.co.yna.cms.v2.yna.authority.application.dto.CreateCommand;
import kr.co.yna.cms.v2.yna.authority.application.dto.CreateResult;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Action;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Authorization;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Role;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Target;
import kr.co.yna.cms.v2.yna.authority.domain.service.CreateAuthorityService;
import kr.co.yna.cms.v2.yna.authority.domain.service.SearchAuthorityService;
import kr.co.yna.cms.v2.yna.user.domain.entity.User;
import kr.co.yna.cms.v2.yna.user.domain.service.FindUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    private final CreateAuthorityService createAuthorityService;
    private final SearchAuthorityService searchAuthorityService;
    private final FindUserService findUserService;

    public AuthorityService(CreateAuthorityService createAuthorityService, SearchAuthorityService searchAuthorityService, FindUserService findUserService) {
        this.createAuthorityService = createAuthorityService;
        this.searchAuthorityService = searchAuthorityService;
        this.findUserService = findUserService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CreateResult createAuthorization(CreateCommand cmd) {
        User creator = this.findUserService.find(cmd.creatorId()).orElseThrow();

        Authorization newAuthorization = this.createAuthorityService.createAuthorization(
                Authorization.builder()
                        .name(cmd.name())
                        .detail(cmd.detail())
                        .desk(cmd.desk())
                        .enabled(cmd.enabled())
                        .target(new Target(cmd.target().toUpperCase()))
                        .action(Action.valueOf(cmd.action().toUpperCase()))
                        .creator(creator)
                        .build()
        );

        return CreateResult.builder()
                .authId(newAuthorization.getId())
                .name(newAuthorization.getName())
                .detail(newAuthorization.getDetail())
                .isDesk(newAuthorization.isDesk())
                .isEnabled(newAuthorization.isEnabled())
                .target(newAuthorization.getTarget().toString())
                .action(newAuthorization.getAction().toString())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CreateResult createRole(CreateCommand cmd) {
        User creator = this.findUserService.find(cmd.creatorId()).orElseThrow();

        Set<Authorization> authorizations = this.searchAuthorityService.searchAuthorizations(cmd.authorizations());
        if (cmd.authorizations().size() != authorizations.size()) {
            throw new NoSuchElementException("존재하지 않는 권한입니다.");
        }

        Role newRole = this.createAuthorityService.createRole(
                Role.builder()
                        .name(cmd.name())
                        .detail(cmd.detail())
                        .desk(cmd.desk())
                        .enabled(cmd.enabled())
                        .authorities(authorizations)
                        .creator(creator)
                        .build()
        );

        return CreateResult.builder()
                .authId(newRole.getId())
                .name(newRole.getName())
                .detail(newRole.getDetail())
                .isDesk(newRole.isDesk())
                .isEnabled(newRole.isEnabled())
                .authorities(
                        authorizations
                                .stream()
                                .map(Authorization::getName)
                                .collect(Collectors.joining(","))
                ).build();
    }
}
*/
