<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String firstname=String.valueOf(request.getSession().getAttribute("current.user.fn"));%>
<%String lastname=String.valueOf(request.getSession().getAttribute("current.user.ln"));%>
<% String path=request.getContextPath(); %>

<% String nick=String.valueOf(request.getSession().getAttribute("current.user.nick")); %>
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
		.formControls {
		  margin-top: 10px;
		 padding-left: 110px;
		}.textAreaForm{
		width:600px;
		height:100px;
		padding: 12px 20px;
		text-align: left;
		}
		.naslov1 {
		   display: inline-block;
		   width: 100px;
           font-weight: bold;
		   text-align: right;
           padding-right: 10px;
           height: 50px;
           
           
		}
		.uredi{
		
		position:left;
		width:auto;
		margin:15px;
		padding:10px;
		box-sizing: border-box;
		}
		.komentar{
		position:left;
		width:auto;
		margin:15px;
		padding:10px;
		background-color: #4ddbff;
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
		.blog{
		text-align: left;
		background-color: #00ccee;
		
		}
		.welcome {
			text-align: right;
		}
		.right{
    float:right;
}

.left{
    float:left;
}
		</style>
	</head>

	<body>
		<h1>
		UNOS U BLOGU
		</h1>
		<h2>${author}</h2>
		   <div class="welcome">
 
  <%if (!String.valueOf(session.getAttribute("current.user.id")).equals("null"))  { %>
				Dobrodošao, <%=firstname%> <%=lastname %> 
				<br><a href="<%=path%>/servleti/odjava" >Odjava</a>
				
			
			
	<%}%>
</div>
		 
		  <c:choose>
    <c:when test="${potpuniUnos==null}">
      Nema bloga!
    </c:when>
    <c:otherwise>
    <div class="blog">
      <h3><c:out value="${potpuniUnos.title}"/></h3>
     
      <p><c:out value="${potpuniUnos.text}"/></p><br>
      <p>Stvoreno:<c:out value="${potpuniUnos.createdAt}"/></p>
       </div>
      
      <c:if test="${!potpuniUnos.comments.isEmpty()}">
      	<ul>
      		<c:forEach var="e" items="${potpuniUnos.comments}">
       			 <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      		</c:forEach>
      	</ul>
      
      </c:if>
    </c:otherwise>
  </c:choose>
		 
		  <% if( (String.valueOf(request.getAttribute("author"))).equals(String.valueOf(request.getSession().getAttribute("current.user.nick")))) { %>
 				<div class="uredi"><a href="edit?EID=${potpuniUnos.id}" >Uredi ovaj unos bloga</a></div>
 			
 			
 		<% } %>
		
 		
		
		<form action="${eid}" method="post">
		<div class="komentar">
		<% if(  nick.equals("null") || nick.isEmpty() ){ %>
		
 				<div>
		 		<div>
		 		 <span class="naslov">Email</span><input type="text" name="email" value='<c:out value="${komentar.email}"/>' >
				 </div>
				 <c:if test="${komentar.imaPogresku('email')}">
		 			<div class="greska"><c:out value="${komentar.dohvatiPogresku('email')}"/></div>
				 </c:if>
				</div>
 			
 			
 		<% } %>
		
		

		<div>
		 <div>
		  <span class="naslov1" >KOMENTAR</span><textarea name="message" class="textAreaForm"  >${komentar.message}</textarea>
		 </div>
		 <c:if test="${komentar.imaPogresku('message')}">
		 <div class="greska"><c:out value="${komentar.dohvatiPogresku('message')}"/></div>
		 </c:if>
		</div>

		

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		</div>
		</form>
	<div class="welcome"> <a href="<%=path %>" >Vrati se na početnu stranicu.</a>  </div>
	</body>
</html>