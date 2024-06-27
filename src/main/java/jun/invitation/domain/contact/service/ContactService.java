package jun.invitation.domain.contact.service;

import jun.invitation.domain.contact.dao.ContactRepository;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.invitation.domain.Invitation;

import jun.invitation.domain.contact.dto.ContactInfoDto;
import jun.invitation.domain.invitation.domain.embedded.WeddingSide;
import jun.invitation.domain.priority.PriorityName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.GROOM;
import static jun.invitation.domain.priority.PriorityName.*;

@Service
@Transactional
@RequiredArgsConstructor
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

    public Map<String, List> getSeperatedMap(List<Contact> contacts) {

        List<ContactInfoDto> groomContact = new ArrayList<>();
        List<ContactInfoDto> brideContact = new ArrayList<>();

        contacts.forEach(contact -> {
            if (contact.getWeddingSide().equals(BRIDE))
                brideContact.add(new ContactInfoDto(
                        contact.getPhoneNumber(), contact.getName(), contact.getRelation())
                );
            else if (contact.getWeddingSide().equals(GROOM))
                groomContact.add(new ContactInfoDto(
                    contact.getPhoneNumber(), contact.getName(), contact.getRelation())
            );
        });

        Map<String, List> seperatedMap = new HashMap<>();
        seperatedMap.put(BRIDE.getSide(), brideContact);
        seperatedMap.put(GROOM.getSide(), groomContact);
        return seperatedMap;

    }

    public void delete(Long productId) {
        contactRepository.deleteByProductId(productId);
    }

    public void update(ContactReqDto newContacts, List<Contact> currentContacts, Invitation invitation) {

        if (!currentContacts.isEmpty() || currentContacts != null) {
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
                        .collect(Collectors.toList());
            }

            if (groomContactInfo != null) {
                groomContactInfo.stream()
                        .map(gc -> {
                            Contact contact = new Contact(gc.getName(), gc.getPhoneNumber(), gc.getRelation(), GROOM);
                            contact.register(invitation);
                            return contact;
                        })
                        .collect(Collectors.toList());
            }

        }
    }
}
