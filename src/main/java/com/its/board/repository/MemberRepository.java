package com.its.board.repository;

import com.its.board.entity.BoardEntity;
import com.its.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Entity, Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
