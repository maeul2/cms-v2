package kr.co.yna.cms.v2.yna.contents.domain.service;

import kr.co.yna.cms.v2.yna.contents.domain.entity.User;

public interface UserInfoService {
    User findUserById(String userId);

    User getCurrentUser();
}
