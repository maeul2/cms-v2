package kr.co.yna.cms.v2.yna.contents.application;

import kr.co.yna.cms.v2.yna.contents.application.cmd.Command;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;

public abstract class ContentManageSupporter<T extends Content, C extends Command> extends ContentManageService<T, C> {
    @Override
    protected void lock(T content) {
        //do nothing
    }

    @Override
    protected T handler(T content) {
        return content;
    }

    @Override
    protected void process(T content) {
        //do nothing
    }

    @Override
    protected void distribute(T content) {
        //do nothing
    }

    @Override
    protected void index(T content) {
        //do nothing
    }

    @Override
    protected void unLock(T content) {
        //do nothing
    }
}
