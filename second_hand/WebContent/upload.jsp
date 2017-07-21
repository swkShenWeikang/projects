<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title>
</head>
<body>
	<form 
  		action="${pageContext.request.contextPath}/upload" 
  		method="POST"
  		enctype="multipart/form-data">
  		<table border="1" align="center">
  			<caption>文件上传</caption>
  			<!-- <tr>
  				<th>上传用户</th>
  				<td><input type="text" name="username"/></td>
  			</tr> -->
  			<tr>
  				<th>上传文件</th>
  				<td><input type="file" name="upfile"/></td>
  			</tr>
  			<tr>
  				<td colspan="2" align="center">
  					<input type="submit" value="上传"/>
  				</td>
  			</tr>
  		</table>
  	</form>
</body>
</html>