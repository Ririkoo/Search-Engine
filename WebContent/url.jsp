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
              <li class="active"><a>Load URL</a></li>
              <li><a href="/IR_WebProject/status.jsp">Status</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    <div class="container">
      	<form class="form-horizontal" role="form" action="loadURL.do">
    	<div class="form-group">
    		<h3>Load URL</h3>
    		<textarea class="form-control" rows="10" name="urlcollect"></textarea>
    		</br>
    		<button type="submit" class="btn btn-primary">Load Internet URL</button>
     	</div>
     	</form>
  		<hr>
		<footer>
        <p>&copy; Runzhe Zhan 2017</p>
        </footer>
    </div>
</body>
</html>