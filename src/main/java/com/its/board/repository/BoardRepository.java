package com.its.board.repository;

import com.its.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;


// spring jpa data 를 사용한다.
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
   Optional<BoardEntity> findByBoardPassword(String boardPassword);


    //  native sql :update board_table set board_hits=board_hits+1 where board_id=?
    // 실제 DB에서 사용할때는 위에 적은것처럼 사용해야한다.
    //jpql( java persistense query language)
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = : id")
    void boardHits(@Param("id") Long id);
}
