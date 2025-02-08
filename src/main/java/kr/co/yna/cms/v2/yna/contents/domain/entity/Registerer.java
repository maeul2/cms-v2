package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "REGISTERER_ID", updatable = false, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "REGISTERER_NAME", updatable = false, nullable = false)),
        @AttributeOverride(name = "department.id", column = @Column(name = "REGISTERER_DEPT_ID", updatable = false, nullable = false)),
        @AttributeOverride(name = "department.name", column = @Column(name = "REGISTERER_DEPT_NAME", updatable = false, nullable = false))
})
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Embeddable
class Registerer extends User {
    Registerer(String registererId, String registererName, Department registererDept, boolean isDesk) {
        super(registererId, registererName, registererDept, isDesk);
    }
}
