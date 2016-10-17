<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator"%>
<%@ page import="bean.*, db.*;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<link href="css/newstyle.css"  rel="stylesheet" type="text/css" />
	<title>Movie by Director</title>
	<% 
		String directorId = request.getParameter("dId"); 
		ArrayList<Movie> list =CypherRecommender.getMoviesByDirector(directorId);
	%>
</head>

<body style="background-image:url('./images/bg_image.jpg'); background-repeat:repeat-x;">
<div id="movieContainer">
	
	<div id="movieListFrame">
 		<table cellpadding="13">
 			<tr>
		<%
            if (list != null) {
               	for(Movie m : list) {  
        %>
            <td>
            	<form action= searchMovieResult.jsp method=get>
             		<input type="hidden" name="title" value="<%= m.getTitle()%>"	 />
            		<input type="image" src=<%=m.getImdbPictureURL()%> alt="Pulpit rock" width="100" height="150">	
             	</form>
             	<p style="font-size: 12px;"><%= m.getTitle()%></p>	
           	</td>	 
 
        <%
               } 
           }
            else {
                out.println("<center><font color=brown>Sorry, No results found!</font></center>");
           }
          
        %>
        	</tr>
        </table>
    </div>
</div>
           
</body>
</html>