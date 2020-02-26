<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true" isErrorPage="true"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
body {
	background-color: <%String color = (String) session.getAttribute("pickedBgCol");
			if (color == null) {
				color = "white";
			}
			
			out.write(color);%>
}
</style>
<title>Error</title>
</head>
<body>

  
<h3>Sorry an exception occured!</h3>  
<p><%= session.getAttribute("exception")%>
</p>
<a href="index.jsp">BACK</a>
</body>
</html>