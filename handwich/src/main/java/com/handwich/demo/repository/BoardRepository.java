package com.handwich.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.handwich.demo.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	
	
	//조회수 증가 : db를 기준으로 
	//update board_table set board_hits = board_hits+1 where id=?
	@Modifying
	
	@Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
	void updateHits(@Param("id") Long id);
	
	
}
