package jun.invitation.domain.contact.service;

import jun.invitation.domain.contact.dao.ContactRepository;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.invitation.domain.Invitation;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.contact.dto.ContactInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public void save(List<ContactInfoDto> contactDtos, Invitation invitation, String type) {
        if (contactDtos == null) {
            return;
        }
        contactDtos.stream()
                .map(c -> new Contact(invitation, c.getName(), c.getPhoneNumber(), c.getRelation(), type))
                .forEach(contact -> contact.register(invitation));
    }

    public Map<String, List> getSeperatedMap(List<Contact> contacts) {

        List<ContactInfoDto> groomContact = new ArrayList<>();
        List<ContactInfoDto> brideContact = new ArrayList<>();

        contacts.forEach(contact -> {
            if (contact.getWeddingSide().equals("Bride"))
                brideContact.add(new ContactInfoDto(
                        contact.getPhoneNumber(), contact.getName(), contact.getRelation())
                );
            else if (contact.getWeddingSide().equals("Groom"))
                groomContact.add(new ContactInfoDto(
                    contact.getPhoneNumber(), contact.getName(), contact.getRelation())
            );
        });

        Map<String, List> seperatedMap = new HashMap<>();
        seperatedMap.put("brideContact", brideContact);
        seperatedMap.put("groomContact", groomContact);
        return seperatedMap;

    }

    public void delete(Long productId) {
        contactRepository.deleteByProductId(productId);
    }
}
