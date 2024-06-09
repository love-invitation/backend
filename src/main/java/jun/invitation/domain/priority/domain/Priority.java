package jun.invitation.domain.priority.domain;

import jakarta.persistence.*;
import jun.invitation.domain.priority.dto.PriorityDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity @NoArgsConstructor
@AllArgsConstructor
@Getter @Slf4j
public class Priority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id")
    private Long id;

    private Integer article;
    private Integer weddingDate;
    private Integer weddingPlace;
    private Integer transport;
    private Integer gallery;
    private Integer contact;
    private Integer thumbnail;
    private Integer guestbook;
    private Integer account;

    @Builder
    public Priority(Integer article, Integer weddingDate, Integer weddingPlace, Integer gallery,
                    Integer transport, Integer contact, Integer thumbnail, Integer guestbook, Integer account) {
        this.article = article;
        this.weddingDate = weddingDate;
        this.weddingPlace = weddingPlace;
        this.transport = transport;
        this.gallery = gallery;
        this.contact = contact;
        this.thumbnail = thumbnail;
        this.guestbook = guestbook;
        this.account = account;
    }

    public void update(PriorityDto priorityDto) {

        if (priorityDto == null) {
            return;
        }
        this.article = priorityDto.getArticle();
        this.weddingDate = priorityDto.getWeddingDate();
        this.weddingPlace = priorityDto.getWeddingPlace();
        this.transport = priorityDto.getTransport();
        this.gallery = priorityDto.getGallery();
        this.contact = priorityDto.getContact();
        this.thumbnail = priorityDto.getThumbnail();
        this.guestbook = priorityDto.getGuestbook();
        this.account = priorityDto.getAccount();
    }

    public List<String> getSortedPriorityList() {

        List<String> fieldNames = new ArrayList<>();

        Field[] fields = Priority.class.getDeclaredFields();

        List<Field> sortedFields = new ArrayList<>();

        for (Field field : fields) {
            if (field.getType().equals(Integer.class)) {
                sortedFields.add(field);
            }
        }
        log.info("[sortedFields] {}", sortedFields);
        sortedFields.sort(Comparator.comparingInt(field -> {
            try {
                // 각 필드의 우선순위 값 반환
                return (Integer) field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return 0;
            }
        }));


        for (Field field : sortedFields) {
            fieldNames.add(field.getName());
        }
        log.info("[fieldNames] {}", fieldNames);
        return fieldNames;

    }
}
