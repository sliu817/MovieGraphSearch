<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator"%>
<%@ page import="bean.*, db.*;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<link href="./css/newstyle.css"  rel="stylesheet" type="text/css" />
	<title>search Movie Result</title>
	<% String title = request.getParameter("title"); 

	ArrayList<Movie> list = CypherRecommender.getMovies(title);
	ArrayList<Genre> genres = CypherRecommender.getGenresByTitle(title);
	ArrayList<Actor> actors = CypherRecommender.getActorsByTitle(title);
	ArrayList<Director> directors = CypherRecommender.getDirectorsByTitle(title);
	ArrayList<ArrayList<User>> users =CypherRecommender.getUsersByTitle(title);
	ArrayList<Movie> similars_CF = CypherRecommender.getSimilarByCF(title);
	String star = CypherRecommender.getStarByTitle(title);
	String ratedCount = CypherRecommender.getRatedCount(title);
	%>
</head>
<body style="background-image:url('./images/bg_image.jpg'); background-repeat:repeat-x;">

<div id="movieContainer">
	
	<div id="movieFrame">
	<%
		if (list != null) {
             for(Movie movie : list) {  
               
    %>	
		
		<img border="0" src=<%=movie.getImdbPictureURL()%> alt="Pulpit rock" width="200" height="300">
        <div id="movieFact">
           	<P style="font-size:2.2em; margin-bottom:0.3em; font-family:'Abril Fatface', Georgia, serif; font-weight:200; color:black; text-shadow: 1px 1px 1px rgba(0,0,0,0.4)">  <%=movie.getTitle()%> </P>
            <%=movie.getYear()%> <br/><br/>
            <%=movie.getCountry()%><br/><br/>
  	<%
			}
		}
       	else {
           out.println("<center><font color=brown>Sorry, No movieList results found!</font></center>");
        }
	%>
       
        
        
    <%
    	if (star != null) {
                
    %>
		 <% out.println(String.format("%.2f",Float.valueOf(star)));%>
		by <%=ratedCount%> users
           
    <%
        } 
        else {
            out.println("<center><font color=brown>Sorry, No star results found!</font></center>");
        }
	%>

		<br/><br/>
       
       	<table>
       	<tr>
        
        <%
            if (genres != null) {
                for(Genre genre : genres){
     	%>    	
            <td>
          
             	<form action= searchMovieByGenreResult.jsp method=get >
             	<input type="hidden" name="mId" value="<%=genre.getMovieId()%>" />
  		 		<input type="hidden" name="genre" value="<%=genre.getGenre()%>" />
  		 
  		 		<%
                        String type = genre.getGenre();
                        String hue="";
                        if(type.equals("Action")||type.equals("Documentary")||type.equals("Film-Noir"))
                            hue = "#6699FF";
                        else if(type.equals("Adventure")||type.equals("Romance")||type.equals("Sci-Fi"))
                             hue = "#FFFF33";
                        else if(type.equals("Animation")||type.equals("Drama")||type.equals("Musical"))
                             hue = "#FF6666";
                        else if(type.equals("Children's")||type.equals("Crime")||type.equals("Mystery")||type.equals("War"))
                             hue = "#66FF66";
                        else 
                             hue = "#F8F888";
                %>
  		 
          		<input type="submit" value="<%=genre.getGenre()%>" style="background-color:<%=hue%>;width:70px;height:30px" />     
     
            	</form> 
          </td>
 		</tr>
            <%
               } 
             }
            else {
                out.println("<center><font color=brown>Sorry, No genreList results found!</font></center>");
            }
        %>      
       </table>
  	</div>     
   </div>
         
         
	<div id="movieCast">
	Directors : 
        <%
            if (directors != null) {
            	for(Director direc : directors){
		 %>
           
   			<form action= searchMovieByDirectorResult.jsp method=get>
   				<input type="hidden" name="dId" value="<%=direc.getDirectorId()%>" />
         		<input type="submit" value="<%=direc.getDirectorName()%>" style="background-color:#6699FF;"/>   
          
       		</form> 
		<%
            	 } 
              }
             else {
                 out.println("<center><font color=brown>Sorry, No directorList results found!</font></center>");
            }
         
        %>
        
        
      Actors:  
         <%
            if (actors != null) {
            	for(Actor actor : actors){
         %>
            
             <form action= searchMovieByActorResult.jsp method=get>
   				<input type="hidden" name="aId" value="<%=actor.getActorId()%>" />
         		<input type="submit" value=" <%=actor.getActorName()%>" style="background-color:#F8F888;" />   
          	</form> 
       	<%
               	} 
           	}
            else {
               out.println("<center><font color=brown>Sorry, No actorList results found!</font></center>");
           	}       
        %>
        
            
       <table>         
        <%
        	int i = 0;
        
            if (users != null) {
                for(ArrayList<User> ulist : users){
       %>  
           	
                	<tr>
                	<td>
       <%
                	if(i ==0)
                		out.println("<font color=brown>5 stars rated by User </font>");
                	else if(i == 1)
                		out.println("<font color=brown>4 stars rated by User </font>");
                	else if(i == 2)
                		out.println("<font color=brown>1 stars rated by User </font>");
                	i++;
                	for(User u: ulist){
        %>
        			</td>
        			<td>
              	 
             		<form action= searchUserResult.jsp method=get >
   						<input type="hidden" name="uId" value=<%= u.getUserID()%> />
  						<input type="hidden" name="age" value=<%= u.getAge()%> />
    					<input type="hidden" name="occupation" value="<%= u.getOccupation()%>" />
         				<input type="submit" value=<%= u.getUserID()%> style="background-color:#FF6666;" />   
          
       				</form> 
       				</td>
		<%
                	}
        %>  
                   	
                	</tr>
      	<%	
                }    	
            }else {
                out.println("<center><font color=brown>Sorry, No userList results found!</font></center>");
            }     
                
        %>
        </table>
        
   </div>
   
     
   <div id="movieSimilar">
      Users Who Like This Movie Also Like   
        <hr/>
        <table cellpadding="13">
          <tr>
        <%
        
            if (similars_CF != null) {
            	for(Movie m : similars_CF){
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
            }else {
                out.println("<center><font color=brown>Sorry, No similarList results found!</font></center>");
            }  
                
        %>
          </tr>
        </table>
          
 </div>
        
</div>
            
</body>
</html>