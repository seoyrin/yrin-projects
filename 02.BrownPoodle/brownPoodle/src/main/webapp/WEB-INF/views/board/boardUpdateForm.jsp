<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		// MODIFY : 수정하기
		$(document).on("click", "#modify_btn", function(){
			alert("수정하기 버튼 >>> : ");
			$("#boardUpdate").attr({
				"method" : "POST",
				"action" : "boardUpdate.bp"
			}).submit();
		});
	
		// LIST : selectAll페이지로 이동 
		$(document).on("click", "#list_btn", function(){
			// selectAll 페이지로 이동 	
		});
	});
</script>

</head>
<body>
<h1>게시글 수정하기 페이지</h1>
<hr>

<form name="boardUpdate" id="boardUpdate">
<table border="1" align="center">
	<tr>
		<td colspan="2" align="center">
			게시판 글 수정하기
			<input type="hidden" name="b_num" id="b_num" value="B0008" /> <!-- 글번호 -->
		</td>				
	</tr> 
	
	<tr>
		<td align="center">TITLE</td>
		<td>
			<input type="text" name="b_subject" id="b_subject" value="제목123" size="53"> <!-- 글제목 -->
		</td>
	</tr>
	
	<tr>
		<td  align="center">WRITER</td>
		<!-- 이름은 session으로 가져와서 값 넣기 -->
		<td>
			<input type="text" name="b_name" id="b_name" value="yerin" size="53"> <!-- 글쓴이 -->
		</td>
	</tr>
	
	<tr>
		<!-- <td  align="center">내용</td> -->
		<td colspan="2">
			<img src="/resources/img/boardimg/heart_n.png"
			     name="b_file" id="b_file" 
			     border="1" width="40" height="50" alt="image"><br> <!-- 사진 -->
			<textarea name="b_content" id="b_content" value="후기입니다." cols="60" rows="10"></textarea> <!-- 내용 -->
		</td>
	</tr>
	
	<tr>
		<%-- <td  align="center">FILE</td>
		<td>
			<input type="text" name="b_file" id="b_file" value="파일이름">
			<img src="/springBoard/imgupload/sm_<%= bvo.getSbfile() %>" border="1" width="40" height="50" alt="image">
		</td> --%>
	</tr>
	
	<tr>
		<td colspan="2" align="center">	
			<input type="button" value="LIST" id="list_btn" style="padding: 3px 100px 3px 100px">
			<input type="button" value="MODIFY" id="modify_btn" style="padding: 3px 100px 3px 100px"> <!-- onclick="boardWriteCheck()" -->
			<!-- <input type="reset" value="다시"> -->
		</td>				
	</tr>
</table>
</form>

</body>
</html>