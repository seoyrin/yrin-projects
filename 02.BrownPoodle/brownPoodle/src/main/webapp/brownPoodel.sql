-- ��DB���� ������ �������� ���, COMMIT �ϱ��
select * from board;
SELECT * FROM HEART order by 1;
select * from member;
select * from rboard;

DELETE FROM BOARD 
WHERE B_NUM = 'B0014';

COMMIT;

--�ܰԽ��� : BOARD��--
DESC BOARD;
CREATE TABLE BOARD(
	B_NUM VARCHAR2(10) PRIMARY KEY      -- �Խñ� ��ȣ
   ,B_SUBJECT VARCHAR2(200) NOT NULL    -- �Խñ� ����
   ,B_NAME VARCHAR2(20) NOT NULL        -- �Խñ� �ۼ���
   ,B_CONTENT VARCHAR2(1000) NOT NULL   -- �Խñ� ����
   ,B_FILE VARCHAR2(100)                -- �Խñ� ����
   ,B_HATE VARCHAR2(2)                  -- ���� �Ű� ��
   ,B_LIKE VARCHAR2(3)                  -- ���ƿ� �� 
   ,B_DELETEYN VARCHAR2(1)              -- �Խñ� ���� ����
   ,B_INSERTDATE DATE                   -- �Խñ� �ۼ�����
   ,B_UPDATEDATE DATE                   -- �Խñ� ��������
);

--ALTER TABLE BOARD
--MODIFY(B_NUM VARCHAR2(10), 
--       CONSTRAINT BOARD_BNUM_PK PRIMARY KEY(B_NUM));

--�۰Խ��� : INSERT��--
INSERT INTO BOARD
					(B_NUM, B_SUBJECT, 
					 B_NAME, B_CONTENT, 
					 B_FILE, B_HATE, 
					 B_LIKE, B_DELETEYN, 
					 B_INSERTDATE, B_UPDATEDATE)
	    values
	    			('B0001', '����1', 
	    			 '�̸�1', '����1', 
	    			 '���ϸ�1', '0',
	    			 '0', 'Y', 
	    			 SYSDATE, SYSDATE);	

--�ۼ���, ���� ������
UPDATE BOARD
SET B_DELETEYN='Y'
WHERE B_NUM='B0002';
COMMIT;

SELECT * FROM BOARD;

--�����ƿ� : LIKE��--
DESC HEART;
CREATE TABLE HEART(
    LIKE_NUM VARCHAR2(10) PRIMARY KEY   -- ���ƿ� ��ȣ : L0001
   ,B_NUM VARCHAR(10) NOT NULL          -- �Խñ� ��ȣ
   ,M_NUM VARCHAR(10) NOT NULL          -- ȸ����ȣ
   ,LIKE_DELETEYN VARCHAR(1)            -- ���ƿ� ��� ���� 
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
ON DELETE CASCADE; -- MID�� PK�� �ƴϿ��� ���� �Ұ��� 

--  ,CONSTRAINT HEART_BNUM_FK FOREIGN KEY(B_NUM) REFERENCES BOARD(B_NUM)
--    ON DELETE CASCADE
--   ,CONSTRAINT HEART_MID_FK FOREIGN KEY(M_ID) REFERENCES MEMBER(M_ID)
--    ON DELETE CASCADE

SELECT * FROM HEART;

-- ���ƿ� �� select
SELECT A.B_LIKE B_LIKE
FROM BOARD A
WHERE A.B_NUM = 'B0003';

-- ���ƿ� �������� ���� Ȯ��
SELECT count(LIKE_DELETEYN)
FROM HEART
WHERE B_NUM='B0003' AND M_NUM='M0001';

-- BOARD ���̺��� B_LIKE +1 ������Ʈ
UPDATE BOARD
SET B_LIKE =B_LIKE+1
WHERE B_NUM = 'B0009'
AND B_DELETEYN = 'Y';

--�ܴ�� : RBOARD��--
CREATE TABLE RBOARD(
	RB_NUM VARCHAR2(10) PRIMARY KEY     -- ��� ��ȣ : RB0001
   ,B_NUM VARCHAR2(10) NOT NULL         -- �Խñ� ��ȣ : FK
   ,RB_NAME VARCHAR2(100) NOT NULL      -- ��� �ۼ���
   ,RB_CONTENT VARCHAR2(500)            -- ��� ����
   ,RB_DELETEYN VARCHAR2(1)             -- ��� ���� ����
   ,RB_INSERTDATE DATE                  -- ��� �ۼ�����
   ,RB_UPDATEDATE DATE                  -- ��� �������� 
);


--��ä����--
SELECT NVL(MAX(SUBSTR(A.B_NUM, -4)), 0) + 1  B_NUM 
FROM BOARD A;

SELECT NVL(MAX(SUBSTR(A.RB_NUM, -4)), 0) + 1  RB_NUM
FROM RBOARD A;

SELECT NVL(MAX(SUBSTR(A.LIKE_NUM, -4)), 0) + 1  LIKE_NUM
FROM HEART A;


--��ȸ�� : MEMBER��--
DESC MEMBER;
CREATE TABLE MEMBER(
	M_NUM VARCHAR2(10) PRIMARY KEY      -- ȸ����ȣ : M0001
   ,M_ID VARCHAR2(20) NOT NULL          -- ���̵�
   ,M_PW VARCHAR2(300) NOT NULL         -- ��й�ȣ
   ,M_EMAIL VARCHAR2(100) NOT NULL      -- �̸��� �ּ�
   ,M_NAME VARCHAR2(20) NOT NULL        -- �̸�
   ,M_NICK VARCHAR2(20) NOT NULL        -- �г���
   ,M_PHONE VARCHAR2(12)                -- �޴���ȭ��ȣ
   ,M_PHOTO VARCHAR2(100)               -- ������ ����
   ,M_ADDR VARCHAR2(50) NOT NULL        -- �ּ�
   ,M_SNSTYPE VARCHAR2(10)              -- �Ҽ� Ÿ��
   ,M_SNSID VARCHAR2(20)                -- �Ҽ� ID
   ,M_DELETEYN VARCHAR2(1)              -- ȸ��Ż�� ����
   ,M_INSERTDATE DATE                   -- ȸ������ ����
   ,M_UPDATEDATE DATE                   -- ȸ������ ��������
);

--ALTER TABLE MEMBER
--MODIFY(M_NUM VARCHAR2(10), 
--       CONSTRAINT MEMBER_MNUM_PK PRIMARY KEY(M_NUM));

--��ȸ�� : INSERT��--
INSERT INTO MEMBER
					(M_NUM, M_ID, 
					 M_PW, M_EMAIL, 
					 M_NAME, M_NICK, 
					 M_ADDR)
	    values
	    			('M0001', 'syr0527', 
	    			 '1234', 'syr0527@gmail.com', 
	    			 '����', 'yryr',
	    			 '����Ư���� ��õ��');	
                    
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

