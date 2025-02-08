package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
public class Department {
    private final String id;
    private final String name;

    public Department(String id, String name) {
        Assert.hasText(id, "id must not be empty");
        Assert.hasText(name, "name must not be empty");

        this.id = id;
        this.name = name;
    }

    String getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Department that)) return false;

        return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + this.id +
                ", name=" + this.name +
                '}';
    }
}
