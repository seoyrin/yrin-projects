package com.brownpoodle.common;

/*
 * 이미지 업로드, 페이징 처리관련 상수
 */
public abstract class CommonUtil {

	// 회원가입 이미지 업로드
	public static final String M_IMG_UPLOAD_PATH = "memberimg";
	public static final int M_IMG_FILE_SIZE = 10 * 1024 * 1024;
	public static final String M_EN_CODE = "UTF-8";

	// 반려동물 이미지 업로드
	public static final String P_IMG_UPLOAD_PATH = "petimg";
	public static final int P_IMG_FILE_SIZE = 10 * 1024 * 1024;	// 10MB
	public static final String P_EN_CODE = "UTF-8";

	// 게시판 이미지 업로드
	public static final String B_IMG_UPLOAD_PATH = "boardimg";
	public static final int B_IMG_FILE_SIZE = 10 * 1024 * 1024;	// 10MB
	public static final String B_EN_CODE = "UTF-8";


	
	
	// 게시판 페이징 사이즈 초기화
//	public static final String B_PAGE_SIZE = "3";	// 한 페이지당 보여줄 게시글 수
//	public static final String B_GROUP_SIZE = "5";	// 한 그룹당 페이지 수
//	public static final String B_CUR_PAGE = "1";		// 현재 페이지
//	public static final String B_TOTAL_COUNT = "";	// 전체 게시글수
}
