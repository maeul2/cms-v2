package kr.co.yna.cms.v2.yna.authority.application.dto;

import lombok.Builder;

@Builder
public record CreateResult(
        long authId,
        String name,
        String detail,
        boolean isDesk,
        boolean isEnabled,
        String target,
        String action,
        String authorities,
        String creatorId
) {
    @Override
    public String toString() {
        return "CreateResult{" +
                "authId=" + this.authId +
                ", name=" + this.name +
                ", detail=" + this.detail +
                ", desk=" + this.isDesk +
                ", enabled=" + this.isEnabled +
                ", targets=" + this.target +
                ", actions=" + this.action +
                ", authorizations=" + this.authorities +
                ", creator=" + this.creatorId +
                '}';
    }
}
