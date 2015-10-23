<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="/mysite/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	$(function() {
		$("#write-form").submit(function() {
			//이름 체크
			if ($("input[name='name']").val() == "") {
				alert("이름이 비어 있습니다.");
				$("input[name='name']").focus();
				return false;
			}
			//비밀번호 체크
			if ($("input[name='pass']").val() == "") {
				alert("비밀번호가 비어 있습니다.");
				$("input[name='pass']").focus();
				return false;
			}
			//내용 체크
			if ($("#textarea-content").val() == "") {
				alert("내용이 비어 있습니다.");
				$("#textarea-content").focus();
				return false;
			}
			return true;
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="guestbook">
				<form id="write-form" action="/mysite/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="textarea-content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li><c:set var='count' value='${fn:length(list) }'></c:set> <c:forEach
							items='${list}' var='vo' varStatus='status'>
							<table>
								<tr>
									<td>${count-status.index }</td>
									<td>${vo.name }</td>
									<td>${vo.regDate }</td>
									<td>
										<!-- <a href="">삭제</a>-->
										<form action="/mysite/guestbook" method="post">
											<input type="hidden" name="a" value="deleteform"> <input
												type="hidden" name="no" value="${vo.no}"> <input
												type="submit" value="삭제">
										</form>
									</td>
								</tr>
								<tr>
									<td colspan=4>${fn:replace(vo.message,newLine,'<br>') }</td>
								</tr>
							</table>
						</c:forEach> <br></li>
				</ul>
			</div>
		</div>
		<c:import url="/views/include/navigation.jsp"></c:import>
		<c:import url="/views/include/footer.jsp"></c:import>
	</div>
</body>
</html>