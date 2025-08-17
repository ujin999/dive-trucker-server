package com.trucker.common.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    private Long id;

    private String authority;

    @Builder
    public Role(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
