<%@page import="java.util.Random"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%! 
private static Map<Integer,String> colorMap=new HashMap<>();
static{
	colorMap.put(1, "red");
	colorMap.put(2, "green");
	colorMap.put(3, "blue");
	colorMap.put(4, "gray");
	colorMap.put(5, "cyan");
	
}
private void chooseColor(javax.servlet.jsp.JspWriter out) throws java.io.IOException {
	Random r=new Random();
	int number=r.nextInt()%5+1;
	out.print(colorMap.get(number));
}
%>
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
<title>Funny story</title>
</head>
<body>
<font color=<% chooseColor(out); %>><p>Real Madrid je najbolji klub na svijetu.</p></font>

</body>
</html>