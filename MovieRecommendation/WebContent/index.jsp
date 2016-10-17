<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">window.NREUM||(NREUM={});NREUM.info={"beacon":"beacon-3.newrelic.com","errorBeacon":"jserror.newrelic.com","licenseKey":"9311aec657","applicationID":"2587164","transactionName":"dF4KQEMJXFlWEEtkWFlQEEZQSWR4dyBLcHRjEQ==","queueTime":0,"applicationTime":21,"ttGuid":"","agentToken":null,"agent":"js-agent.newrelic.com/nr-333.min.js","extra":""}</script>
<script type="text/javascript">window.NREUM||(NREUM={}),__nr_require=function a(b,c,d){function e(f){if(!c[f]){var g=c[f]={exports:{}};b[f][0].call(g.exports,function(a){var c=b[f][1][a];return e(c?c:a)},g,g.exports,a,b,c,d)}return c[f].exports}for(var f=0;f<d.length;f++)e(d[f]);return e}({"4O2Y62":[function(a,b){function c(a,b){var c=d[a];return c?c.apply(this,b):(e[a]||(e[a]=[]),void e[a].push(b))}var d={},e={};b.exports=c,c.queues=e,c.handlers=d},{}],handle:[function(a,b){b.exports=a("4O2Y62")},{}],YLUGVp:[function(a,b){function c(){var a=m.info=NREUM.info;if(a&&a.agent&&a.licenseKey&&a.applicationID){m.proto="https"===l.split(":")[0]||a.sslForHttp?"https://":"http://",g("mark",["onload",f()]);var b=i.createElement("script");b.src=m.proto+a.agent,i.body.appendChild(b)}}function d(){"complete"===i.readyState&&e()}function e(){g("mark",["domContent",f()])}function f(){return(new Date).getTime()}var g=a("handle"),h=window,i=h.document,j="addEventListener",k="attachEvent",l=(""+location).split("?")[0],m=b.exports={offset:f(),origin:l};i[j]?(i[j]("DOMContentLoaded",e,!1),h[j]("load",c,!1)):(i[k]("onreadystatechange",d),h[k]("onload",c)),g("mark",["firstbyte",f()])},{handle:"4O2Y62"}],loader:[function(a,b){b.exports=a("YLUGVp")},{}]},{},["YLUGVp"]);</script>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>The Movie Database</title>
  <meta name="description" content="TMDb is a community maintained movie and TV database.">
  <meta name="keywords" content="movies,films,movie database,movie api,actors,actresses,posters,fanart,backdrops,high quality,directors,hollywood,stars,quotes">
  <link href="http://d3a8mw37cqal2z.cloudfront.net/assets/0be5e087c86596c/stylesheets/ext-all-notheme.css" media="screen" rel="stylesheet" type="text/css" />
  <link href="http://d3a8mw37cqal2z.cloudfront.net/assets/c1f9d690f94d5b5/stylesheets/kendo/kendo.common.min.css" media="screen" rel="stylesheet" type="text/css" />
  <link href="http://d3a8mw37cqal2z.cloudfront.net/assets/28f08665db1ead6/stylesheets/kendo/kendo.uniform.min.css" media="screen" rel="stylesheet" type="text/css" />
  <link href="http://d3a8mw37cqal2z.cloudfront.net/assets/fbd59e2114718cf/stylesheets/screen.css" media="screen" rel="stylesheet" type="text/css" />
  <link href="http://d3a8mw37cqal2z.cloudfront.net/assets/9cd335d6d64a544/stylesheets/common_new.css" media="screen" rel="stylesheet" type="text/css" />
   <link href="css/newstyle.css"  rel="stylesheet" type="text/css" />
  <script src="http://d3a8mw37cqal2z.cloudfront.net/assets/e1288116312e472/javascripts/jquery-1.8.3.min.js" type="text/javascript"></script>
  <link rel="search" type="application/opensearchdescription+xml" title="TMDb Search" href="/opensearch.xml">

</head>
<body id="index" style="background-image:url('./images/bg_image.jpg'); background-repeat:repeat-x;">
  <div id = "container">
  <div id="main" >
    <div id="searchBox">
      <form action= searchMovieByTitle.jsp method=get target="_blank" >
        <input type="text" class="big-search" name="title" value="Find movie" onfocus="this.value='';" onblur="this.value = (this.value=='') ? 'Find movie' : this.value;" />
      </form>
    </div>
  </div>

  <div id="below">
  	<div id="welcome" style="color:blue;">
   		WELCOME TO THE MOVIE RECOMMENDER WITH NEO4J   
      	<hr/> 
	</div>

  	<div id="footer">
		This project is developed by using Neo4j embedded in Java applications. All movie data is provided by <a href="http://grouplens.org/datasets/hetrec-2011/" target="_blank">HetRec 2011( MovieLens + IMDb/Rotten Tomatoes)</a>.<br/>
    	<br/>
    	<a href="http://neo4j.org"><img src="./images/logo/neo4j.png"/></a>
    	
    	<a href="http://www.neotechnology.com/"><img src="./images/logo/neotechnology_small.png"/></a>
    	<a href="http://www.neo4j.org/develop/spring"><img src="./images/logo/sdn.png" /></a>
    	<a href="http://www.neo4j.org/develop/ruby"><img src="./images/logo/ruby.png"/></a>
    	<a href="http://www.neo4j.org/develop/java"><img src="./images/logo/java.jpg"/></a>
    	<a href="http://www.neo4j.org/develop/php"><img src="./images/logo/php.png"/></a>
    	<a href="http://www.neo4j.org/develop/python"><img src="./images/logo/python.png"/></a>
    
    	
	</div>
	
	<div id="info">
    	<p >
      		 Neo4j is a highly scalable, robust (fully ACID) native graph database. Neo4j is used in mission-critical apps by thousands of leading startups, enterprises, and governments around the world.
      		 
    	</p>
    </div>
  </div>
  </div>
   
</body>
</html>