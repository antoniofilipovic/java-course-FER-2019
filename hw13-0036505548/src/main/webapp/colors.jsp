<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
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
<title>Colors</title>
</head>
<body>

	<a href="setcolor?color=white">WHITE</a>
	<a href="setcolor?color=red">RED</a>
	<a href="setcolor?color=green">GREEN</a>
	<a href="setcolor?color=cyan">CYAN</a>
</body>
</html>