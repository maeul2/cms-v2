package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "UPDATER_ID")),
        @AttributeOverride(name = "name", column = @Column(name = "UPDATER_NAME")),
        @AttributeOverride(name = "department.id", column = @Column(name = "UPDATER_DEPT_ID")),
        @AttributeOverride(name = "department.name", column = @Column(name = "UPDATER_DEPT_NAME"))
})
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
class Updater extends User {
    Updater(String updaterId, String updaterName, Department updaterDept, boolean isDesk) {
        super(updaterId, updaterName, updaterDept, isDesk);
    }
}
