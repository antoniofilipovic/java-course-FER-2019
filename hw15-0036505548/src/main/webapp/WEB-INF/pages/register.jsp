<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%String firstname=String.valueOf(request.getSession().getAttribute("current.user.fn"));%>
<%String lastname=String.valueOf(request.getSession().getAttribute("current.user.ln"));%>
<% String path=request.getContextPath(); %>
<html>
	<head>
		<title>REGISTRACIJA</title>
		
		<style type="text/css">
	body{background:#00ccff;
			}
			.welcome {
			text-align: right;
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
	</head>

	<body>
		<h1>
		NAJBOLJA REGISTRACIJA IKAD
		</h1>
		
		  <div class="welcome">
 
  <%if (!String.valueOf(session.getAttribute("current.user.id")).equals("null"))  { %>
				Dobrodošao, <%=firstname%> <%=lastname %> 
				<br><a href="<%=path%>/servleti/odjava" >Odjava</a>
				
			
			
	<%}%>
</div>
		
		<form action="register" method="post">
		
		

		<div>
		 <div>
		  <span class="formLabel">Ime</span><input type="text" name="firstname" value='<c:out value="${zapis.firstName}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('firstname')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('firstname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Prezime</span><input type="text" name="lastname" value='<c:out value="${zapis.lastName}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('lastname')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('lastname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">EMail</span><input type="text" name="email" value='<c:out value="${zapis.email}"/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('email')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('email')}"/></div>
		 </c:if>
		</div>
		
		
		<div>
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="password" value='<c:out value=""/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('passwordHash')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('passwordHash')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Nadimak</span><input type="text" name="nick" value='<c:out value="${zapis.nick}"/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('nick')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('nick')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>
	<a href="main" >Vrati se na početnu stranicu.</a>
	</body>
	
	
</html>
