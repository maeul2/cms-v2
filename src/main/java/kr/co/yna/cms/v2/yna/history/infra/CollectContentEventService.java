package kr.co.yna.cms.v2.yna.history.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import kr.co.yna.cms.v2.yna.common.event.Event;
import kr.co.yna.cms.v2.yna.contents.domain.entity.AbstractEvent;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.history.domain.entity.ContentEvent;
import kr.co.yna.cms.v2.yna.history.domain.repository.ContentEventRepository;
import kr.co.yna.cms.v2.yna.history.domain.service.CollectEventService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class CollectContentEventService implements CollectEventService<Content> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new Jdk8Module());
    }

    private final ContentEventRepository contentEventRepository;

    public CollectContentEventService(ContentEventRepository contentEventRepository) {
        this.contentEventRepository = contentEventRepository;
    }

    @Override
    public void collect(Content content) {
        Assert.notNull(content, "Content must not be null");

        if (CollectionUtils.isEmpty(content.getEventList())) {
            return;
        }

        List<AbstractEvent> eventList = content.getEventList();
        int nextSeq = this.contentEventRepository.findMaxSeqBySourceId(content.getContentId().getValue()).orElse(0) + 1;

        List<ContentEvent> contentEventList = IntStream.range(0, eventList.size())
                .mapToObj(idx -> {
                    AbstractEvent event = eventList.get(idx);

                    String payload;

                    try {
                        payload = objectMapper.writeValueAsString(event);
                    } catch (JsonProcessingException e) {
                        payload = e.getMessage();
                    }

                    return ContentEvent.builder()
                            .sourceId(content.getContentId().getValue())
                            .sourceType(content.getClass().getSimpleName())
                            .eventType(event.getType())
                            .seq(nextSeq + idx)
                            .payload(payload)
                            .build();
                })
                .toList();

        this.contentEventRepository.saveAll(contentEventList);
    }

    private static boolean getRehydration(AbstractEvent event) {
        return event.getClass().getAnnotation(Event.class).rehydration();
    }
}
