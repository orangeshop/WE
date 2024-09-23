package com.akdong.we.schedule.service;

import com.akdong.we.schedule.domain.ScheduleDto;
import com.akdong.we.schedule.domain.ScheduleEntity;
import com.akdong.we.schedule.domain.ScheduleList;
import com.akdong.we.schedule.domain.ScheduleRequest;
import com.akdong.we.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleDto saveSchedule(ScheduleRequest schedule)
    {
        return scheduleRepository
                .save(schedule.asEntity())
                .asDto();
    }

    public ScheduleList getScheduleByDate(int year,int month)
    {
        return ScheduleList
                .builder()
                .year(year)
                .month(month)
                .scheduleList(scheduleRepository
                        .getScheduleByDate(year,month)
                        .stream()
                        .map(ScheduleEntity::asDto)
                        .toList()
                )
                .build();
    }

    public ResponseEntity<ScheduleDto> updateSchedule(long scheduleId,ScheduleRequest scheduleRequest)
    {
        return scheduleRepository
                .findById(scheduleId)
                .map(schedule -> new ResponseEntity<>(scheduleRepository
                        .save(scheduleRequest.asEntity(scheduleId))
                        .asDto(), HttpStatus.OK))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "일정 " + scheduleId + "이 없습니다."));
    }

    public ResponseEntity<ScheduleDto> toggleSchedule(long scheduleId)
    {
        return scheduleRepository
                .findById(scheduleId)
                .map(schedule -> {
                    schedule.setDone(!schedule.isDone());
                    return new ResponseEntity<>(schedule.asDto(), HttpStatus.OK);
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "일정 " + scheduleId + "이 없습니다."));
    }
}
