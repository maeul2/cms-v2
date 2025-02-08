/*
package kr.co.yna.cms.v2.yna.authority.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "AUTH_TARGET")
@Entity
public class Target {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARGET_ID", nullable = false, updatable = false)
    @Id
    private final Long id;
    @Column(name = "CODE", nullable = false, unique = true)
    private String code;
    @Column(name = "DESC")
    private String description;

    public Target(String code, String description) {
        Assert.hasText(code, "code must not be null or empty");

        this.id = null;
        this.code = code;
        this.description = description;
    }

    public void changeCode(String newCode) {
        Assert.hasText(newCode, "newCode must not be null or empty");

        this.code = newCode;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }
}
*/
