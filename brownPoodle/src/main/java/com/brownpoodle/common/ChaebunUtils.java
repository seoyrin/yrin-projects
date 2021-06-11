package com.brownpoodle.common;

/*
 * 채번 로직
 */
public abstract class ChaebunUtils {

	public static final String BIZ_GUBUN_M = "M";	// 회원가입
	public static final String BIZ_GUBUN_P = "P";	// 반려동물
	
	public static final String BIZ_GUBUN_B = "B";	// 게시판
	public static final String BIZ_GUBUN_RB = "RB";	// 게시판 댓글
	public static final String BIZ_GUBUN_L = "L";	// 좋아요 게시글
	
	public static final String BIZ_GUBUN_ID = "ID";	// ID 찾기
	public static final String BIZ_GUBUN_PW = "PW";	// PW 찾기 
	
	public static final String BIZ_GUBUN_W = "W";	// 산책 기록
	
	public static final String BIZ_GUBUN_IN = "IN";	// 업체 정보 (information)
	
	public static final String BIZ_GUBUN_C = "C";	// 다이어리 캘린더
	
	// 0001 형식 만들기
	public static String numPad(String s) {
		int sLen = s.length();
		
		for (int i = sLen; i < 4; i++) {
			s = "0" + s;
		}
		
//		String ymd = DateFormatUtils.ymdFormats(type);
		
		return s;
	}
	
	// 회원가입 번호 채번 - M0001
	public static String getMemberChaebun(String commNO) {
		return BIZ_GUBUN_M.concat(ChaebunUtils.numPad(commNO));
	}
	
	// 반려동물 번호 채번 - P0001
	public static String getPetChaebun(String commNO) {
		return BIZ_GUBUN_P.concat(ChaebunUtils.numPad(commNO));
	}
	
	// 게시글 번호 채번 - B0001
	public static String getBoardChaebun(String commNO) {
		return BIZ_GUBUN_B.concat(ChaebunUtils.numPad(commNO));
	}
	
	// 게시글 댓글 번호 채번 - RB0001
	public static String getRboardChaebun(String commNO) {
		return BIZ_GUBUN_RB.concat(ChaebunUtils.numPad(commNO));
	}
	
	// 좋아요 게시글 번호 채번 - L0001
	public static String getLikeChaebun(String commNO) {
		return BIZ_GUBUN_L.concat(ChaebunUtils.numPad(commNO));
	}
	
	// ID 찾기 번호 채번 - ID0001
	public static String getIDChaebun(String commNO) {
		return BIZ_GUBUN_ID.concat(ChaebunUtils.numPad(commNO));
	}
	
	// PW 찾기 번호 채번 - PW0001
	public static String getPWChaebun(String commNO) {
		return BIZ_GUBUN_PW.concat(ChaebunUtils.numPad(commNO));
	}
	
	// 산책 기록 번호 채번 - W0001
	public static String getWalkChaebun(String commNO) {
		return BIZ_GUBUN_W.concat(ChaebunUtils.numPad(commNO));
	}
	
	// 업체 정보 번호 채번 - IN0001
	public static String getInfoChaebun(String commNO) {
		return BIZ_GUBUN_IN.concat(ChaebunUtils.numPad(commNO));
	}
	// 다이어리 캘린더 번호 채번 - C0001
	public static String getCalChaebun(String commNO) {
		return BIZ_GUBUN_C.concat(ChaebunUtils.numPad(commNO));
	}
	
	public static void main(String[] args) {
//		String testd = ChaebunUtils.getBoardChaebun("1");
//		String t = ChaebunUtils.getRboardChaebun("1");
		String t = ChaebunUtils.getLikeChaebun("1");
		String tt = ChaebunUtils.getMemberChaebun("1");
		
		System.out.println(t);
		System.out.println(tt);
	}
}
