package com.abderrezek.hotelreservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abderrezek.hotelreservations.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
