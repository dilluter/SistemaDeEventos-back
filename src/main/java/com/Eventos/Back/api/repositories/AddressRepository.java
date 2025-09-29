package com.Eventos.Back.api.repositories;


import com.Eventos.Back.api.domain.address.address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<address, UUID> {
}
