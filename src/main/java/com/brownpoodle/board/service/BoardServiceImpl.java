package com.brownpoodle.board.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brownpoodle.board.dao.BoardDAO;
import com.brownpoodle.board.vo.BoardVO;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	private Logger logger = Logger.getLogger(BoardServiceImpl.class);

	// service에서 dao 연결
	private BoardDAO boardDAO;
	
	BoardServiceImpl() {}
	
	// 생성자에 @Autowired로 DI : 의존성 주입
	@Autowired(required = false)
	public BoardServiceImpl(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	
	// ●게시판 상세보기
	@Override
	public List<BoardVO> boardSelect(BoardVO bvo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// ●게시판 전체보기
	@Override
	public List<BoardVO> boardSelectAll(BoardVO bvo) {
		// TODO Auto-generated method stub
		return null;
	}

	// ●게시판 글쓰기
	@Override
	public int boardInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardInsert 함수 진입 >>> : ");
		return boardDAO.boardInsert(bvo);
	}

	// ●게시판 글수정
	@Override
	public int boardUpdate(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardUpdate 함수 진입 >>> :");
		return boardDAO.boardUpdate(bvo);
	}

	// ●게시판 글삭제 
	@Override
	public int boardDelete(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardDelete 함수 진입 >>> :");
		return boardDAO.boardDelete(bvo);
	}

	//===========================좋아요============================
	// 좋아요 수 출력 : selectAll과 합치기
	@Override
	public List<BoardVO> boardLikeSelect(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikeSelect 함수 진입 >>> :");
		return boardDAO.boardLikeSelect(bvo);
	}

	// 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
	@Override
	public List<BoardVO> boardLikeCheck(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikeCheck 함수 진입 >>> :");
		return boardDAO.boardLikeCheck(bvo);
	}

	// 처음으로 좋아요 눌렀을 때, 좋아요 테이블 인서트
	@Override
	public int boardLikeInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikeInsert 함수 진입 >>> :");
		return boardDAO.boardLikeInsert(bvo);
	}

	// BOARD 테이블의 B_LIKE +1 업데이트
	@Override
	public int boardLikePlus(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikePlus 함수 진입 >>> :");
		return boardDAO.boardLikePlus(bvo);
	}

	// 취소했다가 다시 좋아요 눌렀을 때, 좋아요 테이블 업데이트
	// 좋아요 취소하면, 좋아요 테이블 업데이트
	@Override
	public int boardLikeUpdateY(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikeUpdateY 함수 진입 >>> :");
		return boardDAO.boardLikeUpdateY(bvo);
	}
	
	@Override
	public int boardLikeUpdateN(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikeUpdateN 함수 진입 >>> :");
		return boardDAO.boardLikeUpdateN(bvo);
	}

	// BOARD 테이블의 B_LIKE -1업데이트
	@Override
	public int boardLikeMinus(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl boardLikeMinus 함수 진입 >>> :");
		return boardDAO.boardLikeMinus(bvo);
	}
	//===========================좋아요============================

}
