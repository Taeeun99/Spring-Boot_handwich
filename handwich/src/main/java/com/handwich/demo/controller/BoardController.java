package com.handwich.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.handwich.demo.dto.BoardDTO;
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
	            해당 게시글의 조회수를 하나 올리고
	            게시글 데이터를 가져와서 detail.html에 출력
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
	
	
}
