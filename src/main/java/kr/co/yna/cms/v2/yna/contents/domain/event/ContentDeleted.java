package kr.co.yna.cms.v2.yna.contents.domain.event;

import kr.co.yna.cms.v2.yna.common.event.Event;
import kr.co.yna.cms.v2.yna.contents.domain.entity.AbstractEvent;

@Event(history = true, name = "삭제")
public class ContentDeleted extends AbstractEvent {
}
