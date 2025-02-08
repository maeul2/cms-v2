package kr.co.yna.cms.v2.yna.contents.infra;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.service.ContentHistoryService;
import kr.co.yna.cms.v2.yna.history.domain.service.CollectEventService;
import org.springframework.stereotype.Component;

@Component
public class ContentHistoryServiceImpl implements ContentHistoryService {
    private final CollectEventService<Content> collectEventService;

    public ContentHistoryServiceImpl(CollectEventService<Content> collectEventService) {
        this.collectEventService = collectEventService;
    }

    @Override
    public void history(Content content) {
        this.collectEventService.collect(content);
    }
}
