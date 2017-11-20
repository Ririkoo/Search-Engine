<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Information Retrieval</title>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
 <style>
 	body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
   	}
</style>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">Information Retrieval Project</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Content Search<b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="/IR_WebProject/irindex.jsp">Normal Search</a></li>
                    <li><a href="/IR_WebProject/biword.jsp">Biword Search</a></li>
                    <li><a href="/IR_WebProject/posindex.jsp">Position Search</a></li>
                  </ul>
              </li>              
              <li><a href="/IR_WebProject/url.jsp">Load URL</a></li>
              <li class="active"><a>Status</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    <div class="container">
       <div class="row-fluid marketing">
        <div class="span6">
          <h2>Dictionary</h2>
          <p>  	
          <ul>
          	<c:forEach items="${Dictionary }" var="term">
			<li>${term.key} : ${term.value}</li>
			</c:forEach>
		  </ul>
		  </p>
        </div>
        <div class="span6">
          <h2>DocFile:</h2>
          <p>  	
          <ul>
			<c:forEach items="${docFile }" var="item3">
			<li>docid:${item3.key} </br> <a href="file://${item3.value}">${item3.value}</a></li>
			</c:forEach>
		  </ul>
		  </p>
       </div>
      </div> 
      <hr>
      <footer>
        <p>&copy; Runzhe Zhan 2017</p>
      </footer>  
    </div> 
</body>
</html>