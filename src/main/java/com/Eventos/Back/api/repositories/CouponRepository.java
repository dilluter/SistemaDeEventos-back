package com.Eventos.Back.api.repositories;

import com.Eventos.Back.api.domain.coupon.coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<coupon, UUID> {
}
