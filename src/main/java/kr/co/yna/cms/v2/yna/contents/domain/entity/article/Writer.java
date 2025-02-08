package kr.co.yna.cms.v2.yna.contents.domain.entity.article;

import jakarta.persistence.Embeddable;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
class Writer extends User {
    Writer(String writerId, String writerName, Department writerDept, boolean isDesk) {
        super(writerId, writerName, writerDept, isDesk);
    }
}
