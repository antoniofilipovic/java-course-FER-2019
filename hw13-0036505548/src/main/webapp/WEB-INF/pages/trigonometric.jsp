<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
TABLE {
	border: 1px solid black
}

TD {
	border: 1px solid black
}
body {
	background-color: <%String color = (String) session.getAttribute("pickedBgCol");
			if (color == null) {
				color = "white";
			}
			
			out.write(color);%>
}
</style>
<title>Trigonometric values</title>
</head>
<body>
<%
String valueA=(String)request.getAttribute("a");
String valueB=(String)request.getAttribute("b");
int a=Integer.parseInt(valueA);
int b=Integer.parseInt(valueB);
out.print("<table>");
out.print("<tr><td></td><td>sin(x)</td><td>cos(x)</td></tr>");
for(int i=a;i<=b;i++){
	out.print("<tr><td>"+i+"</td><td>"+request.getAttribute("sin("+i+")")+"</td><td>"+request.getAttribute("cos("+i+")")+"</td></tr>");
}
out.print("</table>");
%>
</body>
</html>