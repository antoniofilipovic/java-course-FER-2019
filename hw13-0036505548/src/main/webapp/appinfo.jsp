<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%! 

private void determineDate(javax.servlet.jsp.JspWriter out,HttpSession session) throws java.io.IOException {
	ServletContext ctx = getServletContext();
	
	
	long timeBegin=0;
	
	timeBegin=(long)ctx.getAttribute("time");
	
	
	long timeCurrent=System.currentTimeMillis();
	
	long diff =timeCurrent - timeBegin;

	long diffSeconds = diff / 1000 % 60;
	long diffMinutes = diff / (60 * 1000) % 60;
	long diffHours = diff / (60 * 60 * 1000) % 24;
	long diffDays = diff / (24 * 60 * 60 * 1000);

	out.write(diffDays + " days, ");
	out.write(diffHours + " hours, ");
	out.write(diffMinutes + " minutes, ");
	out.write(diffSeconds + " seconds.");
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
<title>App info</title>
</head>
<body>
<% determineDate(out,session); %>
</body>
</html>