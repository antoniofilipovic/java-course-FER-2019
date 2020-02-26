<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <body>
 <h1>Glasanje za omiljeni bend:</h1>


<ol>
    <c:forEach var="e" items="${polls}">
	<li> <a href="glasanje?pollID=${e.id}" title="${e.title}">${e.message}</a> </li>
	</c:forEach>
</ol>
 </body>
</html>