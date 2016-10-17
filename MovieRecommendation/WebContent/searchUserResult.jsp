<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator"%>
<%@ page import="bean.*, db.*;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<link href="css/newstyle.css"  rel="stylesheet" type="text/css" />
	<title>Insert title here</title>
	<% User user = null;
		ArrayList<Movie> ratedList = null;
		ArrayList<Movie> recommendList = null;
		ArrayList<Movie> sameAgeList = null;
		ArrayList<Movie> sameOccupationList = null;
		String userId = request.getParameter("uId"); 
		String age = request.getParameter("age");
		String occ = request.getParameter("occupation");
		user=CypherRecommender.getUserByuId(userId);
		ratedList = CypherRecommender.getRatedMoviesByUser(userId);
		recommendList = CypherRecommender.getRecommendMoviesByUser(userId);
		sameAgeList = CypherRecommender.getSameAgeMoviesByUser(userId, age);
		sameOccupationList = CypherRecommender.getSameOccupationMoviesByUser(userId, occ);
		String userRatedCount = CypherRecommender.getUserRatedCount(userId);

	%>
</head>
<body style="background-image:url('./images/bg_image.jpg'); background-repeat:repeat-x;">

<div id="movieContainer">
	
	<div id="movieFrame">
	<img border="0" src="./images/user.png" alt="user icon"  />
	<%
            if (user != null) {
	%>
		<div id="userFact">
            <%=user.getUserID()%><br/><br/>
            <%=user.getGender()%><br/><br/>
            <% 
            switch(Integer.valueOf(age)){
            	case 1: out.println("Under 18"); break;
            	case 18: out.println("18-24"); break;
           	 	case 25: out.println("25-34"); break;
            	case 35: out.println("35-44"); break;
            	case 45: out.println("45-49"); break;
            	case 50: out.println("50-55"); break;
            	case 56: out.println("56+"); break;
            	default: out.println("");
            }
            
           %><br/><br/>
            <%=user.getOccupation()%><br/><br/>
		<%
             } 
             else {
                out.println("<center><font color=brown>Sorry, No user results found!</font></center>");
             }
		 %>
        </div>
   </div>
        
        
   <div id="movieRecom">
   HE/SHE RATED MOVIES  <%=userRatedCount%>
        <table cellpadding="13">
        <tr>
  	<%
        if (ratedList != null) {
        	for(Movie m : ratedList){
    %>
            <td>
             	<form action= searchMovieResult.jsp method=get>
                	 <input type="hidden" name="title" value="<%= m.getTitle()%>"	 />
            		  <input type="image" src=<%=m.getImdbPictureURL()%> alt="Pulpit rock" width="80" height="120">	
             	</form>
             	<p style="font-size: 12px;"><%= m.getTitle()%></p>	
             	</td>
    <%
            }
         } 
         else {
             out.println("<center><font color=brown>Sorry, No ratedList results found!</font></center>");
        }
    %>
        </tr>
        </table>
        
    HIS/HER RECOMMENDATIONS  
        <table cellpadding="13">
        <tr>
        <%
            if ( recommendList != null) {	
            	for(Movie m : recommendList){
        %>
            <td>
             	<form action= searchMovieResult.jsp method=get>
               		<input type="hidden" name="title" value="<%= m.getTitle()%>"	 />
            		<input type="image" src=<%=m.getImdbPictureURL()%> alt="Pulpit rock" width="80" height="120">	
          		</form>
             	<p style="font-size: 12px;"><%= m.getTitle()%></p>	
			</td>
           
     	<%
                }
          	} 
         	else {
               	out.println("<center><font color=brown>Sorry, No recommendList results found!</font></center>");
           	}             
        %>
        </tr>
       </table>
    </div>  
    
    
        
   <div id= "movieSimilar">  
    Users With the Same Age Also Like   
        <hr/> 
        <table cellpadding="13">
        <tr>
        <%
            if (sameAgeList!= null) {	
            	for(Movie m : sameAgeList){
        %>
            <td>
             	<form action= searchMovieResult.jsp method=get>
               		<input type="hidden" name="title" value="<%= m.getTitle()%>"	 />
            		<input type="image" src=<%=m.getImdbPictureURL()%> alt="Pulpit rock" width="80" height="120">	
          		</form>
             	<p style="font-size: 12px;"><%= m.getTitle()%></p>	
			</td>
           
     	<%
                }
          	} 
         	else {
               	out.println("<center><font color=brown>Sorry, No sameAgelist results found!</font></center>");
           	}             
        %>
        </tr>
       </table>
        Users With the Same Occupation Also Like   
        <hr/> 
          <table cellpadding="13">
        <tr>
  	<%
        if (sameOccupationList != null) {
        	for(Movie m : sameOccupationList){
    %>
            <td>
             	<form action= searchMovieResult.jsp method=get>
                	 <input type="hidden" name="title" value="<%= m.getTitle()%>"	 />
            		  <input type="image" src=<%=m.getImdbPictureURL()%> alt="Pulpit rock" width="80" height="120">	
             	</form>
             	<p style="font-size: 12px;"><%= m.getTitle()%></p>	
             	</td>
    <%
            }
         } 
         else {
             out.println("<center><font color=brown>Sorry, No sameOccupationList results found!</font></center>");
        }
    %>
        </tr>
        </table>
        </div>
        
       </div>        

            
</body>
</html>