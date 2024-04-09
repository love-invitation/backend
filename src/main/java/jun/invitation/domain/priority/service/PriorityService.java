package jun.invitation.domain.priority.service;

import jun.invitation.domain.priority.dao.PriorityRepository;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.dto.PriorityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public Long savePriority(PriorityDto priorityDto) {

        Priority priority = createPriority(priorityDto);

        return priorityRepository.save(priority).getId();
    }

    private static Priority createPriority(PriorityDto priorityDto) {
        return Priority.builder()
                .article(priorityDto.getArticle())
                .thumbnail(priorityDto.getThumbnail())
                .contact(priorityDto.getContact())
                .reservation_date(priorityDto.getReservation_date())
                .reservation_place(priorityDto.getReservation_place())
                .transportation(priorityDto.getTransportation())
                .guestbook(priorityDto.getGuestbook())
                .build();
    }

}
