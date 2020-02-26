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
<title>Color chooser</title>
</head>
<body>
	<a href="colors.jsp">Background color chooser</a><br>
	
	
</body>
<form action="trigonometric" method="GET">
 Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>


<p>Here is a funny story.<br>
<a href="stories/funny.jsp">Funny story</a></p>

<p> Create your power table<br>
<a href="powers?a=1&b=100&n=3">Classic values</a><br>

<form action="powers" method="GET">
		Starting number:<br>
		<input type="number" name="a"  step="1" value="0"><br>
		Ending number:<br>
		<input type="number" name="b"  step="1" value="0"><br>
		Number of pages:<br>
		<input type="number" name="n"  step="1" value="1"><br>
		<input type="submit" value="Create"><input type="reset" value="Reset">
	</form>


<p> Link to application info
<br>
<a href="appinfo.jsp">Info</a><br>
</p>
</html>