package com.its.board.repository;

import com.its.board.dto.BoardDTO;
import com.its.board.dto.BoardMapperDTO;

import java.util.List;

@Mapper
public interface BoardMapperRepository {
    List<BoardMapperDTO> boardList();

    void save(BoardMapperDTO boardMapperDTO);
}
