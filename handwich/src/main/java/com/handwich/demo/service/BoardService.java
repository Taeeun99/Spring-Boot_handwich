package com.handwich.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import javax.swing.ListModel;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	  public BoardDTO update(BoardDTO boardDTO) {
	        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
	        boardRepository.save(boardEntity);
	        return findById(boardDTO.getId());
	    }

	public void delete(Long id) {
		boardRepository.deleteById(id);
		
	}

	public Page<BoardDTO> paging(Pageable pageable) {
		int page = pageable.getPageNumber()-1;  //  page 위치에 있는 값은 0부터 시작 ( 사람은 1페이지를 요청 -> 프로그램은 0 페이지를 불러와야 하기때문) 
		int pageLimit = 3;
		//page : 몇페이지를 보고싶은지 , pageLimit:한페이지에 몇개씩 볼껀지 , sorting기준 : 전체를 요 기준으로 정렬을 해서 가져온다 , Sort.Direction.DESC : 내림차순 (DB 기준이 아닌 엔티티 기준)  
		// 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
		Page<BoardEntity> boardEntities =  
				boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
		return null;
	}
	


}
