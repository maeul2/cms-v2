package kr.co.yna.cms.v2.yna.lock.infra;

import kr.co.yna.cms.v2.yna.lock.domain.entity.User;
import kr.co.yna.cms.v2.yna.lock.domain.service.UserInfoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("lockUserInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public User getCurrentUser() {
        return Optional.of(new User("2022028", "이우석", "001", "기사SW개발부"))
                .orElseThrow(() -> new IllegalStateException("로그인한 사용자가 없습니다."));
    }
}
