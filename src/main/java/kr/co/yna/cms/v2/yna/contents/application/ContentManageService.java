package kr.co.yna.cms.v2.yna.contents.application;

import jakarta.transaction.Transactional;
import kr.co.yna.cms.v2.yna.contents.application.cmd.Command;
import kr.co.yna.cms.v2.yna.contents.application.dto.ContentDTO;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import org.springframework.util.Assert;

public abstract class ContentManageService<T extends Content, C extends Command> {
    //템플릿 메소드 패턴
    @Transactional
    public ContentDTO manage(C cmd) {
        Assert.notNull(cmd, "command must not be null");

        //불러오기
        T content = load(cmd);

        //락
        lock(content);

        //이벤트 핸들러
        T handler = handler(content);

        //이벤트 기반 작업
        action(handler, cmd);

        //상태 설정
        status(handler);

        //이벤트 처리
        process(handler);

        //데스킹 완료 시
        if (Content.Status.DESK_DONE == content.getStatus()) {
            //배부
            distribute(content);

            //인덱싱
            index(content);
        }

        //저장
        content = store(content);   //객체 참조. 근데 자바는 Call by Value만 있다네?? Call by Value of the Reference 이게 뭔 개소리야!!!

        //락 해제
        unLock(content);

        return new ContentDTO(content);
    }

    protected abstract T load(C cmd);

    protected abstract void lock(T content);

    protected abstract T handler(T content);

    protected abstract void action(T content, C cmd);

    private void status(T content) {
        content.setStatus();
    }

    protected abstract void process(T content);

    protected abstract void distribute(T content);

    protected abstract void index(T content);

    protected abstract T store(T input);

    protected abstract void unLock(T content);
}
