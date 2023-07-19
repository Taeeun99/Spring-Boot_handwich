package com.handwich.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.handwich.demo.dto.BoardDTO;
import com.handwich.demo.entity.BoardEntity;
import com.handwich.demo.repository.BoardRepository;
import com.handwich.demo.service.BoardService;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.Return;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;

	@GetMapping("/save")
	public String saveForm() {
		return "board/board_save";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
		System.out.println("boardDTO = " + boardDTO);
		boardService.save(boardDTO);
		return "user/index";
	}

	@GetMapping("/")
	public String findAll(Model model) {
		// DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
		List<BoardDTO> boardDTOList = boardService.findAll();
		model.addAttribute("boardList", boardDTOList);
		return "board/list";
	}

	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, Model model) {
		/*
		 * 해당 게시글의 조회수를 하나 올리고 게시글 데이터를 가져와서 detail.html에 출력
		 */
		boardService.updateHits(id);
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		return "board/detail";
	}

	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("boardUpdate", boardDTO);
		return "board/update";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
		BoardDTO board = boardService.update(boardDTO);
		model.addAttribute("board", board);
		return "board/detail";
//	        return "redirect:/board/" + boardDTO.getId();
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/";
	}

	// 페이징 처리
	// /board/paging?page=1 페이지라고 지정되있는 파라미터값을 받음
	@GetMapping("/paging")
	public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
		// pageable.getPageNumber();
		Page<BoardDTO> boardList = boardService.paging(pageable);
		// 현재 내가 있는 페이지 표시
		// 보여지는 페이지 갯수 3개 ( 1 2 3 ) ( 7 8 9 )
		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))) -1 ) * blockLimit + 1;
		int endPage = ((startPage + blockLimit -1) < boardList.getTotalPages()) ? startPage + blockLimit -1 : boardList.getTotalPages();
		
		model.addAttribute("boardList" , boardList); 
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage",endPage);
		return "board/paging";
		

	}
}
