package com.token.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.token.entity.Desk;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {

	Optional<Desk> findByDeskNo(String deskNo);

	Optional<Desk> findByStatus(boolean b);

	@Query("SELECT d FROM Desk d WHERE d.status = true AND d.deskNo NOT IN "
			+ "(SELECT t.deskNo FROM Token t WHERE DATE(t.timestamp) = :date GROUP BY t.deskNo HAVING COUNT(t) >= 10) "
			+ "ORDER BY (SELECT COUNT(t) FROM Token t WHERE t.deskNo = d.deskNo AND DATE(t.timestamp) = :date) ASC")
	Desk findDeskWithLowestTokenCount(@Param("date") LocalDateTime localDateTime);

}
