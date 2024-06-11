package jun.invitation.domain.priority.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.domain.priority.dao.PriorityRepository;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.dto.PriorityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @PersistenceContext
    private EntityManager em;

    public Priority savePriority(PriorityDto priorityDto) {

        Priority priority = createPriority(priorityDto);

        priorityRepository.save(priority);

        return priority;
    }

    private static Priority createPriority(PriorityDto priorityDto) {
        return Priority.builder()
                .article(priorityDto.getArticle())
                .thumbnail(priorityDto.getThumbnail())
                .contact(priorityDto.getContact())
                .weddingDate(priorityDto.getWeddingDate())
                .weddingPlace(priorityDto.getWeddingPlace())
                .transport(priorityDto.getTransport())
                .guestbook(priorityDto.getGuestbook())
                .account(priorityDto.getAccount())
                .gallery(priorityDto.getGallery())
                .build();
    }

    public void delete(Priority priority) {
        priorityRepository.delete(priority);
    }
}
