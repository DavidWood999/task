<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path=request.getContextPath();
	request.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>  
	<title>主页</title>  
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
</head>  
<body>
	<a href="${path }/user/userView?uuid=ebce196a6f404aa2b59e78c059fabed1">登录，查看用户详细信息</a>
	<br>
	<br>
	<br>
	<a href="${path }/user/userLook/ebce196a6f404aa2b59e78c059fabed1">登录，查看用户详细信息</a>
	<br>
</body>
</html>
