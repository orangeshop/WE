package com.akdong.we.couple.entity;

import com.akdong.we.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
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

    // 이미지 구현하면 추가
//    @JoinColumn(name = "imageId")
//    private Image image;

    @Column(name="bankbookCreated", nullable = false)
    @ColumnDefault("false")
    private boolean bankbookCreated;

    @Column(name="accountNumber")
    private String accountNumber;

}
