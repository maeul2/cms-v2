/*
package kr.co.yna.cms.v2.yna.authority.controller;

import jakarta.validation.Valid;
import kr.co.yna.cms.v2.yna.authority.application.AuthorityService;
import kr.co.yna.cms.v2.yna.authority.application.dto.CreateCommand;
import kr.co.yna.cms.v2.yna.authority.application.dto.CreateResult;
import kr.co.yna.cms.v2.yna.authority.controller.req.CreateAuthorityReq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("/v2/authority")
public class AuthorityController {
    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    public ResponseEntity<CreateResult> createAuthorization(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CreateAuthorityReq req
    ) {
        CreateResult result = this.authorityService.createAuthorization(
                CreateCommand.builder()
                        .name(req.getName())
                        .detail(req.getDetail())
                        .desk(req.isDesk())
                        .enabled(req.isEnabled())
                        .target(req.getTarget())
                        .action(req.getAction())
                        .creatorId(userDetails.getUsername()).build()
        );

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/authority/{authId}")
                .buildAndExpand(result.authId());

        return ResponseEntity.created(uriComponents.toUri()).body(result);
    }

    @PostMapping("/role")
    public ResponseEntity<CreateResult> createRole(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid CreateAuthorityReq req
    ) {
        CreateResult result = this.authorityService.createRole(
                CreateCommand.builder()
                        .name(req.getName())
                        .detail(req.getDetail())
                        .desk(req.isDesk())
                        .enabled(req.isEnabled())
                        .authorizations(req.getAuthorizations())
                        .creatorId(userDetails.getUsername()).build()
        );

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/v2/authority/role/{roleId}")
                .buildAndExpand(result.authId());

        return ResponseEntity.created(uriComponents.toUri()).body(result);
    }
}
*/
