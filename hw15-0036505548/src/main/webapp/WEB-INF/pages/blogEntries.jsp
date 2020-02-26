<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path=request.getContextPath(); %>

<%String firstname=String.valueOf(request.getSession().getAttribute("current.user.fn"));%>
<%String lastname=String.valueOf(request.getSession().getAttribute("current.user.ln"));%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
			body{background:#00ccff;
			}
		ul{
		position:left;
		width:450px;
		margin:25px;
		padding:10px;
		box-sizing: border-box;
		}
		ul li{
		display:flex;
		background:rgba(255,255,255,.1);
		width:inherit;
		color:#fff;
		margin:10px, 0;
		transition:.5s;
		
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
		.welcome {
			text-align: right;
		}
		</style>
		
		
<title>Svi blogovi korisnika ${author}</title>
</head>
<body>
	<h2>Blogovi</h2>
  <div class="welcome">
 
  <%if (!String.valueOf(session.getAttribute("current.user.id")).equals("null"))  { %>
				Dobrodošao, <%=firstname%> <%=lastname %> 
				<br><a href="<%=path%>/servleti/odjava" >Odjava</a>
				
			
			
	<%}%>
</div>


 <ul>

 

 <c:forEach var="e" items="${blogEntries}">
	 <li><a href="${author}/${e.id}" >${e.title}</a></li>
	</c:forEach>

 </ul>
 
 


<% if( (String.valueOf(request.getAttribute("author"))).equals(request.getSession().getAttribute("current.user.nick"))) {%>
 <a href="${author}/new" >Dodaj novi blog</a>
 <%} %>
 
 <div class="welcome">
		 <a href="<%=path %>" >Vrati se na početnu stranicu.</a>
		 </div>
		
</body>
</html> 