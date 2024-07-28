package jun.invitation.domain.contact.service;

import jun.invitation.domain.contact.dao.ContactRepository;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.contact.dto.ContactInfoDto;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.WeddingSide;
import jun.invitation.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.GROOM;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;

    public void save(List<ContactInfoDto> contactDtos, Product product, WeddingSide type) {
        if (ObjectUtils.isEmpty(contactDtos)) {
            return;
        }
        contactDtos.forEach(contactInfoDto -> {
            Contact contact = new Contact(contactInfoDto.getName(), contactInfoDto.getPhoneNumber(), contactInfoDto.getRelation(), type);
            contact.register(product);
        });
    }

    public Map<String, List<ContactInfoDto>> classifyByWeddingSide(List<Contact> contacts) {
        return contacts.stream()
                .collect(groupingBy(
                        contact -> contact.getWeddingSide().getSide(),
                        mapping(ContactInfoDto::new,toList()))
                );
    }

    public void delete(Long productId) {
        contactRepository.deleteByProductId(productId);
    }

    public void update(ContactReqDto newContacts, List<Contact> currentContacts, Invitation invitation) {

        if (!ObjectUtils.isEmpty(currentContacts))
            contactRepository.deleteByProductId(invitation.getId());


        if (newContacts != null) {
            createAndRegister(newContacts.getBride(),invitation, BRIDE);
            createAndRegister(newContacts.getGroom(),invitation, GROOM);
        }
    }

    private void createAndRegister(List<ContactInfoDto> contactInfoDto, Product product, WeddingSide weddingSide) {

        if (ObjectUtils.isEmpty(contactInfoDto))
            return;

        contactInfoDto.forEach(bc -> {
            Contact contact = new Contact(bc.getName(), bc.getPhoneNumber(), bc.getRelation(), weddingSide);
            contact.register(product);
        });
    }
}
