package com.brownpoodle.chabun.service;

import com.brownpoodle.board.vo.BoardVO;
import com.brownpoodle.rboard.vo.RboardVO;

public interface ChabunService {

	// 필요한 채번 클래스 생성 
	public BoardVO getBoardChabun();	// 게시판
	public RboardVO getRboardChabun();	// 댓글
	public BoardVO getLikeChabun();		// 게시판 좋아요 
}
