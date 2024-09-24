package com.akdong.we.notification.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long token_id;

    @Column
    private long user_id;

    @Column
    private String token;

    public TokenDto asDto()
    {
        return TokenDto
                .builder()
                .token(token)
                .user_id(user_id)
                .build();
    }
}
