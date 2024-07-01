package jun.invitation.domain.priority.service;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.priority.dao.PriorityRepository;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.dto.PriorityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public void create(List<PriorityDto> priorityDtos, Invitation invitation) {
        priorityDtos.forEach(
                p -> new Priority(p.getName(), p.getPriority())
                        .register(invitation)
        );
    }

    public void delete(Priority priority) {
        priorityRepository.delete(priority);
    }

    public void delete(Long productId){
        priorityRepository.deleteByProductId(productId);
    }

    public void update(List<PriorityDto> newPriority, List<Priority> currentPriority) {
        Map<String, Integer> map = newPriority.stream()
                .collect(Collectors.toMap(PriorityDto::getName, PriorityDto::getPriority));

        currentPriority.forEach( p -> p.updatePriority(map.get(p.getName())));
    }
}
