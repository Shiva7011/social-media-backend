package com.zosh.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zosh.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

	@Query("""
			SELECT s
			FROM Story s
			JOIN FETCH s.user
			WHERE s.user.id = :userId
			AND s.createdAt > :cutoff
			ORDER BY s.createdAt ASC
			""")
	List<Story> findActiveStoriesByUserId(@Param("userId") Integer userId, @Param("cutoff") LocalDateTime cutoff);

	@Query("""
			SELECT s
			FROM Story s
			JOIN FETCH s.user
			WHERE s.user.id IN :userIds
			AND s.createdAt > :cutoff
			ORDER BY s.createdAt DESC
			""")
	List<Story> findActiveStoriesByUserIds(@Param("userIds") List<Integer> userIds,
			@Param("cutoff") LocalDateTime cutoff);
}
