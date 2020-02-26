<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true" isErrorPage="true"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	body{background:#00ccff;
			}
		
		a:link, a:visited {
  			background-color: #00ccee;
  			width:inherit;
  			color: white;
  			padding: 14px 25px;
  			text-align: center;
  			text-decoration: none;
 			display: inline-block;
		}
		a:hover, a:active {
  			background-color: #99ebff;
		}
		</style>
<title>Pogreška</title>
</head>
<body>

  
<h3>Pogreška</h3>  
<p>${error}
</p>
<a href="<%=path%>">Povratak na početnu stranicu</a>
</body>
</html>