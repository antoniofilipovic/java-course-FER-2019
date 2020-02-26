<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
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
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.naslov {
		   display: inline-block;
		   width: 100px;
           font-weight: bold;
		   text-align: right;
           padding-right: 10px;
           height: 10px
		}
		
		.naslov1 {
		   display: inline-block;
		   width: 100px;
           font-weight: bold;
		   text-align: right;
           padding-right: 10px;
           height: 50px
           
           
		}
		.formControls {
		  margin-top: 10px;
		}
		.welcome {
			text-align: right;
		}
		.textAreaForm{
		width:600px;
		height:400px;
		padding: 12px 20px;
		text-align: left;
		}
		
			a:link, a:visited {
  			background-color: #00ccee;
  			width:auto;
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
		<c:choose>
		<c:when test="${id.isEmpty()}">
		NAJBOLJE STVARANJE NOVOG UNOSA U BLOGU
		</c:when>
		
		<c:otherwise>
		NAJBOLJE UREĐIVANJE UNOSA U BLOGU
		</c:otherwise>
		</c:choose>
		</h1>
		
		<h2>Pozdrav ${author}</h2>
		
		  <div class="welcome">
 
  <%if (!String.valueOf(session.getAttribute("current.user.id")).equals("null"))  { %>
				Dobrodošao, <%=firstname%> <%=lastname %> 
				<br><a href="<%=path%>/servleti/odjava" >Odjava</a>
				
			
			
	<%}%>
</div>
		
		<form action="${action}" method="post">
		<div>
		 <div>
		  <span class="naslov">Naslov</span><input type="text" name="title" value='<c:out value="${unos.title}"/>'  >
		 </div>
		 <c:if test="${unos.imaPogresku('title')}">
		 <div class="greska"><c:out value="${unos.dohvatiPogresku('title')}"/></div>
		 </c:if>
		</div>

		<div>
			<span class="naslov1">BLOG</span><textarea name="text" class="textAreaForm"  >${unos.text}</textarea>
		
		 <c:if test="${unos.imaPogresku('text')}">
		 <div class="greska"><c:out value="${unos.dohvatiPogresku('text')}"/></div>
		 </c:if>
		</div>

		

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>
		
		
		<div class="welcome">
		 <a href="<%=path %>" >Vrati se na početnu stranicu.</a>
		 </div>
		
	</body>
</html>