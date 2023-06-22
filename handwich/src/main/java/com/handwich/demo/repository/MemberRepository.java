package com.handwich.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.handwich.demo.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity,Long>{
 //이메일로 회원정보 조회  (select*from member_table where member_email=?)
	//주고받는 개체는 다 Entity
	Optional<MemberEntity> findByMemberEmail(String memberEmail);
	
	
}
