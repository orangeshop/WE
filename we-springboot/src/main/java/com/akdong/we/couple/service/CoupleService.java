package com.akdong.we.couple.service;

import com.akdong.we.couple.repository.CoupleRepository;
import com.akdong.we.couple.response.CreateCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service("coupleService")
@RequiredArgsConstructor
@Slf4j
public class CoupleService {
    private final CoupleRepository coupleRepository;

    private final RedisTemplate<String, String> redisTemplate;
    private static final long CODE_EXPIRATION = 5 * 60; // 5분 만료

    public String createCode(Long memberId) {
        String code = generateRandomCode();
        redisTemplate.opsForValue().set("couple:code:" + memberId, code, CODE_EXPIRATION, TimeUnit.SECONDS);
        return code;
    }

    private String generateRandomCode() {
        return String.format("%06d", new Random().nextInt(1000000)); // 6자리 랜덤 숫자
    }


}
