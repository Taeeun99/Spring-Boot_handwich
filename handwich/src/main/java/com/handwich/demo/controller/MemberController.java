package com.handwich.demo.controller;

import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.handwich.demo.dto.MemberDTO;
import com.handwich.demo.service.MemberService;

@Controller
public class MemberController {
    private final MemberService memberService;
    
    // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/index")
    public String indexForm() {
        return "user/index";
    }
    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "user/save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return "user/login";
    }
    
    @GetMapping("/member/login")
    public String loginForm() {
    	return "user/login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO , HttpSession session) {
    	MemberDTO loginResult = memberService.login(memberDTO);
    	if(loginResult != null) {
    		//login 성공
    		session.setAttribute("loginEmail", loginResult.getMemberEmail());
    		return "home";
    	}else {
    		//login 실패
    		
    		return "user/login";
    	}
    }
    // 회원 목록 불러오기
    @GetMapping("/member/")
    public String findAll(Model model) {
    	List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
    	model.addAttribute("memberList",memberDTOList);
    	return "user/list";
    }
    
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
    	MemberDTO memberDTO = memberService.findById(id);
    	model.addAttribute("member",memberDTO);
    	return "user/detail";
    }
    
    
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
    	String myEmail = (String) session.getAttribute("loginEmail");
    	MemberDTO memberDTO = memberService.updateForm(myEmail);
    	model.addAttribute("updateMember" , memberDTO);
    	return "user/update";
    } 
    
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
    	memberService.update(memberDTO);
    	return "redirect:/member/" + memberDTO.getId();
    }
    
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
    	memberService.deleteById(id);
    	return "redirect:/member/";
    }
    
    
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "user/index";
    }
 
}
