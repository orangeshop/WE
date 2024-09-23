package com.akdong.we.ledger;

import com.akdong.we.common.exception.BusinessException;
import com.akdong.we.common.qr.QRCodeGenerator;
import com.akdong.we.couple.entity.Couple;
import com.akdong.we.couple.repository.CoupleRepository;
import com.akdong.we.file.service.FileService;
import com.akdong.we.ledger.entity.Ledger;
import com.akdong.we.ledger.repository.LedgerRepository;
import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("ledgerService")
@RequiredArgsConstructor
@Slf4j
public class LedgerService {
    private final LedgerRepository ledgerRepository;
    private final CoupleRepository coupleRepository;
    private final FileService fileService;

    @Transactional
    public Ledger createLedger(Couple couple){
        // 이미 Ledger가 존재하는지 확인
        if (couple.isLedgerCreated()) {
            throw new BusinessException(LedgerErrorCode.COUPLE_ALREADY_CREATED_ERROR);
        }

        Ledger ledger = Ledger.builder()
                .couple(couple)
                .build();

        // Ledger 저장
        Ledger savedLedger = ledgerRepository.save(ledger);

        // QR 코드 생성 및 S3 업로드
        String ledgerAccessUrl = "we://transfer/" + savedLedger.getId();
        try {

            byte[] qrCodeBytes = QRCodeGenerator.generateQRCodeImage(ledgerAccessUrl, 350, 350);

            // 바이트 배열을 MultipartFile로 변환
            MultipartFile qrCodeMultipartFile = new MockMultipartFile(
                    "ledger_" + savedLedger.getId() + ".png", // 파일 이름
                    "ledger_" + savedLedger.getId() + ".png", // 원본 파일 이름
                    "image/png",                              // 파일 타입
                    qrCodeBytes                               // 파일 데이터
            );

            // S3에 업로드
            String qrCodeUrl = fileService.upload(qrCodeMultipartFile, "qr-codes");  // 디렉토리 이름 설정

            // QR 코드 URL을 저장
            log.info("QR code URL: {}", qrCodeUrl);
            savedLedger.setQrCodeUrl(qrCodeUrl);

        } catch (WriterException | IOException e) {
            throw new BusinessException(LedgerErrorCode.QR_GENERATE_FAILED_ERROR);
        }
        couple.setLedgerCreated(true);

        return savedLedger;

    }

}
