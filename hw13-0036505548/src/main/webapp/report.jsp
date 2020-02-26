<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
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

</head>
<body>
<h1>OS usage</h1>
<p>Here are the results of OS
usage in survey that we completed.</p>

<img style="-webkit-user-select: none;" src="reportImage">

</body>
</html>