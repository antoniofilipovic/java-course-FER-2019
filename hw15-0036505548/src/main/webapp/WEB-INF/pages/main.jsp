<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String id=String.valueOf(request.getSession().getAttribute("current.user.id"));%>
<%String firstname=String.valueOf(request.getSession().getAttribute("current.user.fn"));%>
<%String lastname=String.valueOf(request.getSession().getAttribute("current.user.ln"));%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
body{background:#00ccff;
			}
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		.welcome {
			text-align: right;
		}.registracija{
		font-style: italic;
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
		</style>
<title>NajBlog</title>
</head>
<body>

 <h1>DOBRODOŠLI NA NAJBOLJI BLOG IKAD </h1>
  <div class="welcome">
 
  <%if (!String.valueOf(session.getAttribute("current.user.id")).equals("null"))  { %>
				Dobrodošao, <%=firstname%> <%=lastname %> 
				<br><a href="<%=path%>/servleti/odjava" >Odjava</a>
				
			
			
	<%}%>
</div>

<div clas=registracija>
 Ovdje možete čitati blogove drugih korisnika, komentirati ih, pa čak i pokrenuti svoj blog.
</div>
<FORM action="main" method="post">
	
    <P>
    
    
    
    <div>
    <div class="greska"><c:out value="${errorMessage}"/></div>
		 <div>
		  <span class="formLabel">Nadimak</span><input type="text" name="nick" value='<c:out value="${nick}"/>' size="20">
		 </div>
		</div>
		
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="password" value='<c:out value=""/>' size="20">
		 </div>
		</div>
		

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Logiraj se">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
	
    </P>
 </FORM>
<div>Stvori novi račun. Besplatno je i uvijek će biti.
Ali ubacit ćemo uskoro hrpu reklama,iako smo rekli da nećemo.<br>
<a href="register"  >REGISTRACIJA</a>  </div>


 <ul>
 Svi naši registrirani korisnici
 <c:forEach var="e" items="${users}">
	 <li><a href="author/${e.nick}" >${e.nick}</a></li>
	</c:forEach>

 </ul>
 
 


</body>
</html>