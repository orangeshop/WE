package com.akdong.we.couple.entity;

import com.akdong.we.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member1Id")
    private Member member1;

    @OneToOne
    @JoinColumn(name = "member2Id")
    private Member member2;

    @Column(name="bankbookCreated", nullable = false)
    private boolean bankbookCreated;

    @Column(name="accountNumber")
    private String accountNumber;

}
