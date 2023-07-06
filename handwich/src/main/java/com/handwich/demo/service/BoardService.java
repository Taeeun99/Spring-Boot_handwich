package com.handwich.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import javax.swing.ListModel;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.handwich.demo.dto.BoardDTO;
import com.handwich.demo.entity.BoardEntity;
import com.handwich.demo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public void save(BoardDTO boardDTO) {
		BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
		boardRepository.save(boardEntity);
	}
	
	public List<BoardDTO> findAll(){
		List<BoardEntity> boardEntityList = boardRepository.findAll();
		List<BoardDTO> boardDTOList = new ArrayList<>();
		for(BoardEntity boardEntity: boardEntityList) {
			boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
		}
		return boardDTOList;
	}

	
	
	// id 따라서 게시물 열리게 하는 부분
	@Transactional
	public void updateHits(Long id) {
		boardRepository.updateHits(id);
	}

	public BoardDTO findById(Long id) {
		Optional<BoardEntity> optionalBordEntity = boardRepository.findById(id);
		if (optionalBordEntity.isPresent()) {
			BoardEntity boardEntity = optionalBordEntity.get();
			BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
			return boardDTO;
		}else {
			return null;
		}
	}

}
