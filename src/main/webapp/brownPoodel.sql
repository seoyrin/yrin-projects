-- ★DB에서 데이터 변경했을 경우, COMMIT 하기★
select * from board;
SELECT * FROM HEART order by 1;
select * from member;
select * from rboard;

DELETE FROM BOARD 
WHERE B_NUM = 'B0014';

COMMIT;

--●게시판 : BOARD●--
DESC BOARD;
CREATE TABLE BOARD(
	B_NUM VARCHAR2(10) PRIMARY KEY      -- 게시글 번호
   ,B_SUBJECT VARCHAR2(200) NOT NULL    -- 게시글 제목
   ,B_NAME VARCHAR2(20) NOT NULL        -- 게시글 작성자
   ,B_CONTENT VARCHAR2(1000) NOT NULL   -- 게시글 본문
   ,B_FILE VARCHAR2(100)                -- 게시글 사진
   ,B_HATE VARCHAR2(2)                  -- 누적 신고 수
   ,B_LIKE VARCHAR2(3)                  -- 좋아요 수 
   ,B_DELETEYN VARCHAR2(1)              -- 게시글 삭제 여부
   ,B_INSERTDATE DATE                   -- 게시글 작성일자
   ,B_UPDATEDATE DATE                   -- 게시글 수정일자
);

--ALTER TABLE BOARD
--MODIFY(B_NUM VARCHAR2(10), 
--       CONSTRAINT BOARD_BNUM_PK PRIMARY KEY(B_NUM));

--○게시판 : INSERT○--
INSERT INTO BOARD
					(B_NUM, B_SUBJECT, 
					 B_NAME, B_CONTENT, 
					 B_FILE, B_HATE, 
					 B_LIKE, B_DELETEYN, 
					 B_INSERTDATE, B_UPDATEDATE)
	    values
	    			('B0001', '제목1', 
	    			 '이름1', '내용1', 
	    			 '파일명1', '0',
	    			 '0', 'Y', 
	    			 SYSDATE, SYSDATE);	

--○수정, 삭제 디버깅○
UPDATE BOARD
SET B_DELETEYN='Y'
WHERE B_NUM='B0002';
COMMIT;

SELECT * FROM BOARD;

--○좋아요 : LIKE○--
DESC HEART;
CREATE TABLE HEART(
    LIKE_NUM VARCHAR2(10) PRIMARY KEY   -- 좋아요 번호 : L0001
   ,B_NUM VARCHAR(10) NOT NULL          -- 게시글 번호
   ,M_NUM VARCHAR(10) NOT NULL          -- 회원번호
   ,LIKE_DELETEYN VARCHAR(1)            -- 좋아요 취소 여부 
   ,CONSTRAINT HEART_BNUM_FK FOREIGN KEY(B_NUM) REFERENCES BOARD(B_NUM)
    ON DELETE CASCADE
   ,CONSTRAINT HEART_MNUM_FK FOREIGN KEY(M_NUM) REFERENCES MEMBER(M_NUM)
    ON DELETE CASCADE
);

ALTER TABLE HEART ADD
CONSTRAINT HEART_BNUM_FK FOREIGN KEY(B_NUM) REFERENCES BOARD(B_NUM)
ON DELETE CASCADE;

ALTER TABLE HEART ADD
CONSTRAINT HEART_MID_FK FOREIGN KEY(M_ID) REFERENCES MEMBER(M_ID)
ON DELETE CASCADE; -- MID는 PK가 아니여서 참조 불가능 

--  ,CONSTRAINT HEART_BNUM_FK FOREIGN KEY(B_NUM) REFERENCES BOARD(B_NUM)
--    ON DELETE CASCADE
--   ,CONSTRAINT HEART_MID_FK FOREIGN KEY(M_ID) REFERENCES MEMBER(M_ID)
--    ON DELETE CASCADE

SELECT * FROM HEART;

-- 좋아요 수 select
SELECT A.B_LIKE B_LIKE
FROM BOARD A
WHERE A.B_NUM = 'B0003';

-- 좋아요 눌렀는지 여부 확인
SELECT count(LIKE_DELETEYN)
FROM HEART
WHERE B_NUM='B0003' AND M_NUM='M0001';

-- BOARD 테이블의 B_LIKE +1 업데이트
UPDATE BOARD
SET B_LIKE =B_LIKE+1
WHERE B_NUM = 'B0009'
AND B_DELETEYN = 'Y';

--●댓글 : RBOARD●--
CREATE TABLE RBOARD(
	RB_NUM VARCHAR2(10) PRIMARY KEY     -- 댓글 번호 : RB0001
   ,B_NUM VARCHAR2(10) NOT NULL         -- 게시글 번호 : FK
   ,RB_NAME VARCHAR2(100) NOT NULL      -- 댓글 작성자
   ,RB_CONTENT VARCHAR2(500)            -- 댓글 본문
   ,RB_DELETEYN VARCHAR2(1)             -- 댓글 삭제 여부
   ,RB_INSERTDATE DATE                  -- 댓글 작성일자
   ,RB_UPDATEDATE DATE                  -- 댓글 수정일자 
);


--●채번●--
SELECT NVL(MAX(SUBSTR(A.B_NUM, -4)), 0) + 1  B_NUM 
FROM BOARD A;

SELECT NVL(MAX(SUBSTR(A.RB_NUM, -4)), 0) + 1  RB_NUM
FROM RBOARD A;

SELECT NVL(MAX(SUBSTR(A.LIKE_NUM, -4)), 0) + 1  LIKE_NUM
FROM HEART A;


--●회원 : MEMBER●--
DESC MEMBER;
CREATE TABLE MEMBER(
	M_NUM VARCHAR2(10) PRIMARY KEY      -- 회원번호 : M0001
   ,M_ID VARCHAR2(20) NOT NULL          -- 아이디
   ,M_PW VARCHAR2(300) NOT NULL         -- 비밀번호
   ,M_EMAIL VARCHAR2(100) NOT NULL      -- 이메일 주소
   ,M_NAME VARCHAR2(20) NOT NULL        -- 이름
   ,M_NICK VARCHAR2(20) NOT NULL        -- 닉네임
   ,M_PHONE VARCHAR2(12)                -- 휴대전화번호
   ,M_PHOTO VARCHAR2(100)               -- 프로필 사진
   ,M_ADDR VARCHAR2(50) NOT NULL        -- 주소
   ,M_SNSTYPE VARCHAR2(10)              -- 소셜 타입
   ,M_SNSID VARCHAR2(20)                -- 소셜 ID
   ,M_DELETEYN VARCHAR2(1)              -- 회원탈퇴 여부
   ,M_INSERTDATE DATE                   -- 회원가입 일자
   ,M_UPDATEDATE DATE                   -- 회원정보 수정일자
);

--ALTER TABLE MEMBER
--MODIFY(M_NUM VARCHAR2(10), 
--       CONSTRAINT MEMBER_MNUM_PK PRIMARY KEY(M_NUM));

--○회원 : INSERT○--
INSERT INTO MEMBER
					(M_NUM, M_ID, 
					 M_PW, M_EMAIL, 
					 M_NAME, M_NICK, 
					 M_ADDR)
	    values
	    			('M0001', 'syr0527', 
	    			 '1234', 'syr0527@gmail.com', 
	    			 '예린', 'yryr',
	    			 '서울특별시 금천구');	
                    
INSERT INTO MEMBER
					(M_NUM, M_ID, 
					 M_PW, M_EMAIL, 
					 M_NAME, M_NICK, 
					 M_ADDR)
	    values
	    			('M0002', 'id_a', 
	    			 'pw_a', 'main_a', 
	    			 'name_a', 'nickname_a',
	    			 'address_a');	
                     
INSERT INTO MEMBER
					(M_NUM, M_ID, 
					 M_PW, M_EMAIL, 
					 M_NAME, M_NICK, 
					 M_ADDR)
	    values
	    			('M0003', 'id_b', 
	    			 'pw_b', 'main_b', 
	    			 'name_b', 'nickname_b',
	    			 'address_b');	

commit;
select * from member;                 
drop table member;

