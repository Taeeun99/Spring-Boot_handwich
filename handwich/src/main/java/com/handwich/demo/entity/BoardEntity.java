package com.handwich.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.type.TrueFalseType;

import com.handwich.demo.dto.BoardDTO;
import com.handwich.demo.repository.BoardRepository;

import lombok.Getter;
import lombok.Setter;

//DB의 테이블 역할을 하는 클래슽  라이 

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 
	private Long id;
	
	@Column(length = 20, nullable = false ) // 크기 20, not null
	private String boardWriter;
	
	
	@Column  // 크기 255, null 가능
	private String boardPass;
	
	@Column
	private String boardTitle;
	 
	@Column(length = 500)
	private String boardContents;
	
	@Column
	private int boardHits;
	
	
	public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setId(boardDTO.getId());  //업데이트 수 를 전달
		boardEntity.setBoardWriter(boardDTO.getBoardWriter());
		boardEntity.setBoardPass(boardDTO.getBoardPass());
		boardEntity.setBoardTitle(boardDTO.getBoardTitle());
		boardEntity.setBoardContents(boardDTO.getBoardContents());
		boardEntity.setBoardHits(0);
		return boardEntity;
		
	
	}
	

	  public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
	        BoardEntity boardEntity = new BoardEntity();
	        boardEntity.setId(boardDTO.getId());
	        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
	        boardEntity.setBoardPass(boardDTO.getBoardPass());
	        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
	        boardEntity.setBoardContents(boardDTO.getBoardContents());
	        boardEntity.setBoardHits(boardDTO.getBoardHits());
	        return boardEntity;
	    }



	
	
}
