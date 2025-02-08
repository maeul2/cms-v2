package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "DESK_ID")),
        @AttributeOverride(name = "name", column = @Column(name = "DESK_NAME")),
        @AttributeOverride(name = "department.id", column = @Column(name = "DESK_DEPT_ID")),
        @AttributeOverride(name = "department.name", column = @Column(name = "DESK_DEPT_NAME"))
})
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
class Desk extends User {
    Desk(String deskId, String deskName, Department deskDept, boolean isDesk) {
        super(deskId, deskName, deskDept, isDesk);
    }
}
