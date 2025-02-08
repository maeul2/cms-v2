package kr.co.yna.cms.v2.yna.contents.infra;

import kr.co.yna.cms.v2.yna.contents.application.exception.UserNotFoundException;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import kr.co.yna.cms.v2.yna.contents.domain.service.UserInfoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("contentUserInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public User findUserById(String userId) {
        return Optional.of(new User(userId, "이우석", new Department("001", "기사SW개발부"), true))
                .orElseThrow(() -> new UserNotFoundException("User not found - " + userId));
    }

    @Override
    public User getCurrentUser() {
        return Optional.of(new User("2022028", "이우석", new Department("001", "기사SW개발부"), true))
                .orElseThrow(() -> new IllegalStateException("로그인한 사용자가 없습니다."));
    }
}
