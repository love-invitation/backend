package jun.invitation.domain.contact.service;

import jun.invitation.domain.contact.dao.ContactRepository;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.contact.dto.ContactInfoDto;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.WeddingSide;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void save(List<ContactInfoDto> contactDtos, Invitation invitation, WeddingSide type) {
        if (contactDtos == null) {
            return;
        }
        contactDtos.stream()
                .map(c -> new Contact(c.getName(), c.getPhoneNumber(), c.getRelation(), type))
                .forEach(contact -> contact.register(invitation));
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

        if (currentContacts != null || !currentContacts.isEmpty()) {
            contactRepository.deleteByProductId(invitation.getId());
        }

        if (newContacts != null) {
            List<ContactInfoDto> brideContactInfo = newContacts.getBride();
            List<ContactInfoDto> groomContactInfo = newContacts.getGroom();

            if (brideContactInfo != null) {
                brideContactInfo.stream()
                        .map(bc -> {
                            Contact contact = new Contact(bc.getName(), bc.getPhoneNumber(), bc.getRelation(), BRIDE);
                            contact.register(invitation);
                            return contact;
                        })
                        .collect(toList());
            }

            if (groomContactInfo != null) {
                groomContactInfo.stream()
                        .map(gc -> {
                            Contact contact = new Contact(gc.getName(), gc.getPhoneNumber(), gc.getRelation(), GROOM);
                            contact.register(invitation);
                            return contact;
                        })
                        .collect(toList());
            }

        }
    }
}
