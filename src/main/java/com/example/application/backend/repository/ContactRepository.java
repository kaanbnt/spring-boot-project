package com.example.application.backend.repository;

import com.example.application.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("    Select contact " +
            "   from Contact contact " +
            "   where lower(contact.firstName) like (concat('%', :firstName, '%'))" +
            "   or lower(contact.lastName) like (concat('%', :firstName, '%'))")
    List<Contact> search(@Param("firstName") String firstName);
}
