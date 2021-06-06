<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardInsert.jsp</title>

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		
		// OK 버튼클릭 : 게시글 등록 
		$(document).on("click", "#ok_btn", function(){
			
			alert("b_btn click >>> : ");
			
			$('#boardWrite').attr({
				'action' : '/boardInsert.bp',
				'method' : 'POST',
				'enctype' : 'multipart/form-data'
			}).submit();	
		});
	
		// LIST 버튼클릭 : 전체보기 페이지로 이동 
		$(document).on("click", "#list_btn", function(){
			// selectAll 페이지로 이동 
		});
		
	});
	
</script>

</head>
<body>
<h1>게시판 글쓰기 : boardInsertForm</h1>
<hr>

<form name="boardWrite" id="boardWrite">
<table border="1" align="center">
	<tr>
		<td colspan="2" align="center">게시판 글쓰기</td>				
	</tr> 
	
	<tr>
		<td align="center">TITLE</td>
		<td>
			<input type="text" name="b_subject" id="b_subject" size="53">
		</td>
	</tr>
	
	<tr>
		<td  align="center">WRITER</td>
		<!-- 이름은 session으로 가져와서 값 넣기 -->
		<td>
			<input type="text" name="b_name" id="b_name" size="53">
		</td>
	</tr>
	
	<tr>
		<!-- <td  align="center">내용</td> -->
		<td colspan="2">
			<textarea name="b_content" id="b_content" cols="60" rows="10" placeholder="글을 입력해주세요."></textarea>
		</td>
	</tr>
	
	<tr>
		<td  align="center">FILE</td>
		<td>
			<input type="file" name="b_file" id="b_file">
		</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">	
			<input type="button" value="LIST" id="list_btn" style="padding: 3px 100px 3px 100px">
			<input type="button" value="OK" id="ok_btn" style="padding: 3px 100px 3px 100px"> <!-- onclick="boardWriteCheck()" -->
			<!-- <input type="reset" value="다시"> -->
		</td>				
	</tr>
</table>
</form>

</body>
</html>