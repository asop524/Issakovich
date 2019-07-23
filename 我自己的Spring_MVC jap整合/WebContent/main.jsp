<%@page import="org.iss.domain.Book"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<body>
<br /><br />
		<div class="container">
			<div class="panel panel-primary">
			<!-- .panel-heading 面板头信息。 -->		
			<div class="panel-heading">
				<!-- .panel-title 面板标题。 -->		
		  	  <h3 class="panel-title">图书信息列表</h3>
		  	</div>
		  	<div class="panel-body">
		  		<div class="container">
		  			<div class="row">
					<%
						// 获取上个页面传递过来的request.setAttribute("books", books);
						List<Book> books = (List<Book>)request.getAttribute("books");
						// 遍历显示在页面上
						for(Book book : books){
							%>
								<div class="col-md-4 col-sm-6">
									<a href="book.jsp">
										<img src="images/<%=book.getImage() %>" alt="">
									</a>
									<div class="caption">
										<h4><%=book.getName() %></h4>
										<p><%=book.getAuthor() %></p>
										<p><%=book.getPrice() %></p>
									</div>
								</div>
							
							<%
						}
					%>
					</div>
				</div>
		  	</div>
		  </div>
		  </div>
</body>
</html>