package com.its.board.dto;

import com.its.board.entity.BoardEntity;
import com.its.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardPassword;
    private String boardContents;
    private int boardHit;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    private MultipartFile boardFile; // 실제파일
    private String boardFileName; //파일 이름
    public BoardDTO(String boardTitle, String boardWriter, String boardPassword,String boardContents){
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardPassword = boardPassword;
        this.boardContents = boardContents;

    }

    public BoardDTO(Long id, String boardTitle, String boardWriter, int boardHit, LocalDateTime createdTime){
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHit = boardHit;
        this.createdTime = createdTime;

    }
    public  static  BoardDTO toBoardDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setId(boardEntity.getId());
        boardDTO.setId(boardEntity.getId());
        boardDTO.setId(boardEntity.getId());
        return boardDTO;
    }


}
