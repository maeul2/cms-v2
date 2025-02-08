/*
package kr.co.yna.cms.v2.yna.authority.infra;

import kr.co.yna.cms.v2.security.GrantedAuthority;
import kr.co.yna.cms.v2.security.UserPrincipal;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Action;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Authority;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Target;
import kr.co.yna.cms.v2.yna.authority.domain.repository.AuthorityRepository;
import kr.co.yna.cms.v2.yna.authority.domain.service.CheckAuthorityService;
import kr.co.yna.cms.v2.yna.authority.domain.service.exception.UnAuthenticatedException;
import kr.co.yna.cms.v2.yna.authority.domain.service.exception.UnAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
public class CheckGrantedAuthorityService implements CheckAuthorityService {
    private final AuthorityRepository authorityRepository;

    public CheckGrantedAuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public boolean checkDesk(Target target, Action action) {
        Assert.notNull(target, "대상이 null일 수 없습니다.");
        Assert.notNull(action, "행위가 null일 수 없습니다.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UnAuthenticatedException("인증이 필요합니다!!!");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Authority authority = this.authorityRepository.findByIdIn(
                        userPrincipal.getAuthorities().stream()
                                .map(GrantedAuthority::getId)
                                .collect(Collectors.toSet())
                ).stream()
                .filter(auth -> auth.hasTargetAction(target, action))
                .findAny()
                .orElseThrow(() -> new UnAuthorizedException("권한이 없습니다!!! - " + target + ":" + action));

        return authority.isDesk();
    }
}
*/
