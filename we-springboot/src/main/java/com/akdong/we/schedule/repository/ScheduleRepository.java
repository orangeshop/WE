package com.akdong.we.schedule.repository;

import com.akdong.we.schedule.domain.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {

    @Query("SELECT c FROM ScheduleEntity c WHERE YEAR(c.scheduled_time) = :year AND MONTH(c.scheduled_time) = :month")
    List<ScheduleEntity> getScheduleByDate(@Param("year") int year,@Param("month") int month);
}
