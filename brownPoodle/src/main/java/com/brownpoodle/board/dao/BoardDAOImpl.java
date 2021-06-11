package com.brownpoodle.board.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.brownpoodle.board.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	private Logger logger = Logger.getLogger(BoardDAOImpl.class);
	
	// 의존성 주입
	@Autowired(required = false)
	private SqlSessionTemplate sqlSession;
	
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
		logger.info("BoardDAOImpl boardInsert 함수 진입 >>> : ");	
		return (Integer)sqlSession.insert("boardInsert", bvo);
	}

	// ●게시판 글수정
	@Override
	public int boardUpdate(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("위에");
		logger.info("BoardDAOImpl boardUpdate 함수 진입 >>> :");
		System.out.println("아래");
		return (Integer)sqlSession.update("boardUpdate", bvo);
	}

	// ●게시판 글삭제 
	@Override
	public int boardDelete(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardDelete 함수 진입 >>> :");
		return (Integer)sqlSession.update("boardDelete", bvo);
	}

	//===========================좋아요============================
	// 좋아요 수 출력 : selectAll과 합치기
	@Override
	public List<BoardVO> boardLikeSelect(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikeSelect 함수 진입 >>> :");
		return sqlSession.selectList("boardLikeSelect", bvo);
	}

	// 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
	@Override
	public List<BoardVO> boardLikeCheck(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikeCheck 함수 진입 >>> :");
		return sqlSession.selectList("boardLikeCheck", bvo);
	}

	// 처음으로 좋아요 눌렀을 때, 좋아요 테이블 인서트
	@Override
	public int boardLikeInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikeInsert 함수 진입 >>> : ");	
		return (Integer)sqlSession.insert("boardLikeInsert", bvo);
	}

	// BOARD 테이블의 B_LIKE +1 업데이트
	@Override
	public int boardLikePlus(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikePlus 함수 진입 >>> :");
		return (Integer)sqlSession.update("boardLikePlus", bvo);
	}

	// 취소했다가 다시 좋아요 눌렀을 때, 좋아요 테이블 업데이트
	// 좋아요 취소하면, 좋아요 테이블 업데이트
	@Override
	public int boardLikeUpdateY(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikeUpdateY 함수 진입 >>> :");
		return (Integer)sqlSession.update("boardLikeUpdateY", bvo);
	}

	@Override
	public int boardLikeUpdateN(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikeUpdateN 함수 진입 >>> :");
		return (Integer)sqlSession.update("boardLikeUpdateN", bvo);
	}
	
	// BOARD 테이블의 B_LIKE -1업데이트
	@Override
	public int boardLikeMinus(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl boardLikeMinus 함수 진입 >>> :");
		return (Integer)sqlSession.update("boardLikeMinus", bvo);
	}
	//===========================좋아요============================

}
