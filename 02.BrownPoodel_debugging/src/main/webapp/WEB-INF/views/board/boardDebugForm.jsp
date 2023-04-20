<%@page import="com.brownpoodle.board.vo.BoardVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update, Delete, Like</title>

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		 
		// ==========================★수정, 삭제 디버깅★============================
		// 수정하기
		$(document).on("click", "#update", function(){
			$("#boardDebugForm").attr({
				"method" : "POST",
				"action" : "boardUpdate.bp"
			}).submit();
		});
		
		// 삭제하기 : 삭제 -> 전체보기 페이지로 이동 
		$(document).on("click", "#delete", function(){
		
			let deleteURL = "boardDelete.bp";
			let reqType = "POST";
			let dataParam = {
					b_num : $("#b_num").val()
			};
			
			alert("dataParam >>> : " + dataParam);
			
			$.ajax({
				url : deleteURL,
				type : reqType,
				data : dataParam,
				success : whenSuccess,
				error : whenError
			});
			
			function whenSuccess(resData) {
				if ("DELETE_OK" == resData) {
					alert("삭제가 완료되었습니다.");
					location.href="boardInsertForm.bp";
				}else if ("DELETE_FAIL" == resData) {
					alert("삭제가 실패하였습니다.");
					location.href="boardInsertForm.bp";
				};
			}
			function whenError(e) {
				alert("error e >>> : " + e.responseText);
			}
		});
		
		
		// ==========================좋아요 디버깅1============================
		// 동기를 하면 X -> ajax 동기 awake : https://blueshw.github.io/2018/02/27/async-await/
		// 좋아요 버튼 눌렀을 때, ajax 처리
		$(document).on("click", "#like_img", function(){
			
			let likeclickURL = "boardLikeCheck.bp";
			let reqType ="POST";
			let dataParam = {
					m_num : $("#m_num").val(),
					b_num : $("#b_num").val()
			};
			
			// alert("dataParam >>> : " + dataParam); // => dataParam >>> : [object Object]
			
			$.ajax({
				url : likeclickURL,
				type : reqType,
				data : dataParam,
				async : false,
				success : whenSuccess,
				error : whenError
			});
			
			function whenSuccess(resData) {
				if (resData.resultCheck == 1) {
					//$("#like_img").attr("src", "/resources/img/boardimg/heart_y.png"); //=======> 이부분은 필요한가??????????? ■
					$("#b_like").empty();
					// $("#b_like").append(resData.b_like);
					$("#b_like").attr("value", resData.b_like);
					
				}else if (resData.resultCheck == 0) {
					//$("#like_img").attr("src", "/resources/img/boardimg/heart_n.png"); //■ 
					$("#b_like").empty();
					// $("#b_like").append(resData.b_like);
					$("#b_like").attr("value", resData.b_like);
					
				}else {
					alert("resData에러!!");
				};
				// location.href="boardLikeSelect.bp";
				// location.reload(); // 새로고침 : 안해도됨 
			}
			function whenError(e) {
				alert("error e >>> : " + e.responseText);
			}
		}); 
		
		
		
		// ==========================좋아요 디버깅2============================
		/* $(document).on("click", "#like_img", function(){
			 
			$("#likeForm")
            .attr({"action":"boardLikeCheck.bp",
                    "method":"POST",
                    "enctype":"application/x-www-from-urlencoded"
                 })
            .submit();
			
			//const resultCheck = asyncLike();
			const data = asyncLike();
			
			alert('data.resultCheck >>> : ' + data.resultCheck);
			alert('data.b_like >>> : ' + data.b_like);
			
			if (data.resultCheck == 1) {
				// $("#like_img").attr("src", "/resources/img/boardimg/heart_y.png"); //=======> 이부분은 필요한가??????????? ■
				$("#b_like").empty();
				// $("#b_like").append(resData.b_like);
				$("#b_like").attr("value", data.b_like);
				
			}else if (data.resultCheck == 0) {
				// $("#like_img").attr("src", "/resources/img/boardimg/heart_n.png"); //■ 
				$("#b_like").empty();
				// $("#b_like").append(resData.b_like);
				$("#b_like").attr("value", data.b_like);
				
			}else {
				alert("resData에러!!");
			};
		});
			
		async function asyncLike() {
			alert('asyncLike 함수 진입 시작 >>> : ');
			const res = await fetch('boardLikeCheck.bp');
			alert('asyncLike 함수 중간 >>> : ');
			const data = await res.json();
			alert('data >>> : ' + data);
			const resultCheck = await data.resultCheck;
			const b_like = await data.b_like;
			alert('asyncLike 함수 끝 >>> : ');
			
			location.href="boardLikeSelect.bp";
			return data;
		}  */
		
		
		
		// ==========================좋아요 디버깅3============================
			/* $(document).on("click", "#like_img", function(){
			
			let likeclickURL = "boardLikeCheck.bp";
			let reqType ="POST";
			let dataParam = {
					m_num : $("#m_num").val(),
					b_num : $("#b_num").val()
			};
			
			asyncLike();
			
			// alert("dataParam >>> : " + dataParam); // => dataParam >>> : [object Object]
			
			function getData() {
				//alert('getData() 함수 진입 >>> : ');
				return  $.ajax({
						url : likeclickURL,
						type : reqType,
						data : dataParam
						//async:false
						//success : whenSuccess,
						//error : whenError
					    });
			}
			
			async function asyncLike() {
				//alert('asyncLike() 함수 진입 >>> : ');
				const res = await getData();
				//await getData();
				
				if (res.resultCheck == 1) {
					$("#like_img").attr("src", "/resources/img/boardimg/heart_y.png"); //=======> 이부분은 필요한가??????????? ■
					$("#b_like").empty();
					// $("#b_like").append(resData.b_like);
					$("#b_like").attr("value", res.b_like);
					
				}else if (res.resultCheck == 0) {
					$("#like_img").attr("src", "/resources/img/boardimg/heart_n.png"); //■ 
					$("#b_like").empty();
					// $("#b_like").append(resData.b_like);
					$("#b_like").attr("value", res.b_like);
					
				}else {
					alert("resData에러!!");
				}; 
				//location.reload(); // 새로고침 : 안해도됨 
				//history.go(0);	
			}
			//location.reload(true);
			
			function whenSuccess(resData) {
				if (resData.resultCheck == 1) {
					//$("#like_img").attr("src", "/resources/img/boardimg/heart_y.png"); //=======> 이부분은 필요한가??????????? ■
					$("#b_like").empty();
					// $("#b_like").append(resData.b_like);
					$("#b_like").attr("value", resData.b_like);
					
				}else if (resData.resultCheck == 0) {
					//$("#like_img").attr("src", "/resources/img/boardimg/heart_n.png"); //■ 
					$("#b_like").empty();
					// $("#b_like").append(resData.b_like);
					$("#b_like").attr("value", resData.b_like);
					
				}else {
					alert("resData에러!!");
				};
				// location.href="boardLikeSelect.bp";
				//location.reload(); // 새로고침 : 안해도됨 
			}
			function whenError(e) {
				alert("error e >>> : " + e.responseText);
			} 
		});   */
		
		
		// ==========================좋아요 디버깅4============================
		/* const getAjax = function(url) {
			return new Promise()
		}
		 */
		
	});
</script>

</head>
<body>
<% request.setCharacterEncoding("EUC-KR");%> 

<h1>Update, Delete 디버깅</h1>

<!-- UPDATE, DELETE : 제목, 내용, 글번호 -->
<!--  
<div>
	<form name="boardDebugForm" id="boardDebugForm">
		글번호 : <input type="text" name="b_num" id="b_num"><br>
		제목 : <input type="text" name="b_subject" id="b_subject"><br>
		내용 : <input type="text" name="b_content" id="b_content"><br>
	
		<button type="button" id="update">수정하기</button>
		<button type="button" id="delete">삭제하기</button>
 	</form>
</div>
<hr><p>
-->
<hr>

<!-- 좋아요 
		로그인한 회원번호 : m_num
		게시글 번호 : b_num
-->
<h1>좋아요♡->♥ 디버깅</h1>
<div>
	<form name="likeForm" id="likeForm">

<%
	// likeSelect
	Object obj1 = request.getAttribute("likeSelect");
	List<BoardVO> list1 = (List)obj1;
	BoardVO bvo = null;
	if (list1.size() == 1) {
		bvo = list1.get(0);
	};
	
	// likeCheck
	String src = "";
	Object obj2 = request.getAttribute("likeCheck");
	//int nCnt = Integer.valueOf(obj2);
	int nCnt = (int)obj2;
	System.out.println("boardDebugForm nCnt >>> : " + nCnt);
	if (nCnt == 0) {
		src="/resources/img/boardimg/heart_n.png";
	}else {
		src="/resources/img/boardimg/heart_y.png";
	}
%>
		<!-- 회원번호와 게시글번호 임의로 지정해서 넘겨주기★ -->
		<input type="text" name="m_num" id="m_num" value="M0002">
		<input type="text" name="b_num" id="b_num" value="B0010">
 		
 		<!-- 좋아요 ♡ 버튼 -->
		<%-- <button type="button" name="like_img" id="like_img" value="<%= nCnt %>"
		        style="width:40px; height:40px">
			<img src="<%= src %>" alt="좋아요이미지"
			     style="width:30px; height:30px">
		</button> --%>
		
		<input type="image" name="like_img" id="like_img" 
			       src="<%= src %>" alt="좋아요이미지"
			       style="width:40px; height:40px">
	
		<br>
		<!-- 좋아요 수 보여주기 -->
		<input type="text" name="b_like" id="b_like" value="<%= bvo.getB_like() %>"> <!-- 좋아요 수 -->
	</form>
</div>

</body>
</html>