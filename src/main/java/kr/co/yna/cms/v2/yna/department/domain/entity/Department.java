package kr.co.yna.cms.v2.yna.department.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
public class Department {
    @Id
    @Column(name = "DEPT_ID", updatable = false)
    private final String id;
    @Column(name = "DEPT_NAME")
    private String name;

    public Department(String id, String name) {
        Assert.hasText(id, "부서 아이디가 유효하지 않습니다.");
        Assert.isTrue(3 ==  id.length(), "부서 아이디 길이는 3글자 입니다.");
        Assert.hasText(name, "부서명이 유효하지 않습니다.");

        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        Assert.hasText(name, "부서명은 한 글자 이상의 문자열이어야 합니다!!!");

        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Department{id=" + this.id + ", name=" + this.name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
