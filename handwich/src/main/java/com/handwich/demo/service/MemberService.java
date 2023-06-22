package com.handwich.demo.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.handwich.demo.dto.MemberDTO;
import com.handwich.demo.entity.MemberEntity;
import com.handwich.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public void save(MemberDTO memberDTO) {
		// TODO Auto-generated method stub3
		// 1. dto -> entity 변환
		// 2. repository의 save 메서드 호출
		MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
		memberRepository.save(memberEntity);
// repository의 save메서드 호출 (조건. entity  객체를 넘겨줘야 함)
	}

	public MemberDTO login(MemberDTO memberDTO) {
		/*
		 * 1. 회원이 엽력한 이메일로 DB 에서 조회를 함 2. DB에서 조회한 비밀번화와 사용자가 입력한 비밀번호가 일치하는지 판단
		 */
		Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
		if (byMemberEmail.isPresent()) {
			// 조회 결과가 있다 ( 해당 이메일을 가진 회원 정보가 있다
			MemberEntity memberEntity = byMemberEmail.get();

			// memberEntity에 있는 Password 랑 memberDTO에 있는 Password 랑 같은지 비교
			if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
				// 비밀번호가 일치
				// entity -> dto 변환 후 리턴
				MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
				return dto;
			} else {
				// 비밀번호가 불일치 (로그인 실패)
				return null;
			}

		} else {
			// 조회결과가 없다 ( 해당 이메일을 가진 회원이 없다)
			return null;
		}
	}

	public List<MemberDTO> findAll() {

		List<MemberEntity> memberEntityList = memberRepository.findAll();
		List<MemberDTO> memberDTOList = new ArrayList<>();
		for (MemberEntity memberEntity : memberEntityList) {
			memberDTOList.add(MemberDTO.toMemberDTO(memberEntity)); // memberEntity 객체를 DTO로 변환
//			MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity); // 변환된 DTO 를 List에 담음
//			memberDTOList.add(memberDTO);
		}
		return memberDTOList;
	}

	public MemberDTO findById(Long id) {

		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

		if (optionalMemberEntity.isPresent()) {
//			MemberEntity memberEntity = optionalMemberEntity.get();
//			MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//			return memberDTO;
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
			// 1. optionalMemberEntity 객체로 감싸져있는것의 껍데기를 벗겨낼때는 get() 메서드 사용
			// 2. toMemberDTO 매서드로 DTO로 변환을 한결과를 컨트롤러로 리턴
		} else {
			return null;
		}
	}

	public MemberDTO updateForm(String myEmail) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
		if (optionalMemberEntity.isPresent()) {
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		} else {
			return null;
		}
	}

	public void update(MemberDTO memberDTO) {
		memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
	}

	public void deleteById(Long id) {
		memberRepository.deleteById(id);
	}

}
