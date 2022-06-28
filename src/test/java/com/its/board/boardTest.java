package com.its.board;

import com.its.board.common.PagingConst;
import com.its.board.dto.BoardDTO;
import com.its.board.dto.CommentDTO;
import com.its.board.dto.MemberDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.entity.CommentEntity;
import com.its.board.entity.MemberEntity;
import com.its.board.repository.BoardMapperRepository;
import com.its.board.repository.BoardRepository;
import com.its.board.repository.CommentRepository;
import com.its.board.repository.MemberRepository;
import com.its.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class boardTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardMapperRepository boardMapperRepository;
    public BoardDTO newBoard(int i){
        BoardDTO board =
                new BoardDTO("email"+i, "password"+i,"name"+i,"mobile"+i);
        return board;
    }
    @Test
    @Transactional
    @Rollback(value = false)
    public  void newMember(){
         MemberDTO memberDTO = new MemberDTO("email1", "pw1", "name1")
        memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 테스트")
    public void commentSaveTest(){
        BoardDTO boardDTO= newBoard(99); // 저장할 게시글 객체
        // 회원 엔티티 객체를 같이 줘야 하니까 위에서 저장한 이메일 값으로 회원엔티티 조회
        MemberEntity memberEntity = memberRepository.findByMemberEmail("email").get();
        // 게시글 객체와 회원 엔티티 boardEntity 객체 생성
        BoardEntity boardEntity = BoardEntity.tosaveEntity(boardDTO, memberEntity);
        // 저장 수행
        Long saveBoardId = boardRepository.save(boardEntity).getId();
        // 댓글 저장을 위해 회원엔티티, 게시글엔티티 준비
        BoardEntity boardEntity = boardRepository.findById(savedBoardId).get();
    }
   @Test
   @Transactional
   @Rollback(value = false)
   @DisplayName("회원 게시글 연관관계 테스트")
   public void memberBoardFindByIdTest(){
        //위에서 저장한 테이블 조회
        // select*from board_table where board_id=1
       Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(4L);
       if(optionalBoardEntity.isPresent()){
           BoardEntity boardEntity = optionalBoardEntity.get();
           System.out.println("boardEntity = " + boardEntity.getId());
           System.out.println("boardEntity = " + boardEntity.getBoardTitle());
           //게시글 작성자의 이름을 보고싶다.
           //select m.member_name from member_table m,board_table b where m.member_id = b.member_id where b.member_id=1;
           //where m.member_id = b.member_id b.member_id =1;
           //객체 그래프 탐색
           System.out.println("boardEntity.getMemberEntity().getMemberName() = " + boardEntity.getMemberEntity().getMemberName());
       }
   }
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 삭제 테스트")
    public void deleteTest(){
        //게시글 삭제
//        boardRepository.deleteById(1L);
        // 게시글을 작성한 회원 삭제
        memberRepository.deleteById(1L);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void saveTest(){
        IntStream.rangeClosed(1,20)
    }

//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void newBoard(){
//        IntStream.rangeClosed(1,20).forEach(i ->{
//            boardService.save(newBoard(i))
//        });


    @Test
    @Transactional
    @DisplayName("페이징 테스트")
    public  void pagingTest(){
        int page = 2;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                ));

        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부 이전페이지가있는지 true / false 를 준다.
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부

    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("댓글작성 테스트")
    public void commentSaveTest() {
        BoardDTO boardDTO = newBoard(99); // 저장할 게시글 객체
        // 회원 엔티티 객체를 같이 줘야 하니까 위에서 장한 이메일 값으로 회원 엔티티 조회
        MemberEntity memberEntity = memberRepository.findByMemberEmail("email").get();
        // 게시글 객체와 회원 엔티티로 bo
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
        Long savedBoardId = boardRepository.save(boardEntity).getId();
        BoardEntity findBoardEntity = boardRepository.findById(savedBoardId).get();
        //댓글 저장용 엔티티 객체 생성
        CommentDTO commentDTO = new CommentDTO("email", "댓글내용두번째");
        CommentEntity commentEntity = CommentEntity

    }
        @Test
        @Transactional
        @Rollback(value = false)
        @DisplayName("댓글 목록 출력 테스트")
        public  void commentListTest(){
        // 댓글이 들어있는 게시글 엔티티 조회
            Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(5L);
            // 게시글 엔티티의 댓글 목록 조회
            if(optionalBoardEntity.isPresent()){
                BoardEntity boardEntity = optionalBoardEntity.get();
                List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
                for(CommentEntity commentEntity: commentEntityList){
                }

            }
    }
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("검색 테스트")
    public void searchTest(){
        List<BoardDTO>boardDTOList = boardService.search("9");
        System.out.println("boardDTOList = " + boardDTOList);
    }
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("mapper 테스트")
    public void mapperTest() {
        List<BoardDTO> boardDTOList = boardMapperRepository.boardList();

    }
}
