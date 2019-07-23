<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<body>
<!-- .container 类用于固定宽度并支持响应式布局的容器。 -->
		<div class="container">
			<!-- 栅格系统用于通过一系列的行（row）与列（column）的组合来创建页面布局 -->
			<div class="row">
				<!-- 页面标题 -->
				<div class="page-header">
		  	   	     <h2>用户登录</h2>
		  	   	     <!-- 
		  	   	     .form-control:所有设置了 .form-control 类的 <input>、<textarea> 和 <select> 元素都将被默认设置宽度属性为 width: 100%; 
		  	   	     .form-horizontal:表单添加 .form-horizontal 类，并联合使用 Bootstrap 预置的栅格类，可以将 label 标签和控件组水平并排布局。
		  	   	     .form-inline:多个控件可以排列在同一行,水平
		  	   	     	-->
		  	   	     	<%
		  	   	     		// 获取后台传递的message
		  	   	     		String message = (String)request.getAttribute("message");
		  	   	     		if(message != null && !message.equals("")){
		  	   	     			%>
		  	   	     				<div class="input-group col-md-4">
		  	   	     					<div class="alert alert-danger" role="alert"><%=message %></div>
		  	   	     				</div>
		  	   	     			<%
		  	   	     		}
		  	   	     	%>
		  	   	     <form class="form-horizontal" action="login" method="post">
						<div class="form-group">
							<!-- input-group用于将图片和控件放在一组  -->
							<div class="input-group col-md-4">
								<!-- 额外的内容(图片)放在 input-group-addon中-->
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input class="form-control" placeholder="用户名/邮箱" type="text" name="loginname" value="Jack@163.com"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group col-md-4">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input class="form-control" placeholder="密码" type="password" name="password"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-4">
								<!--btn-group-justified 能够让你的按钮组根据父容器尺寸来设定各自相同的尺寸，采用响应式布局技术从而有利于移动端的用户体验。-->
								<div class="btn-group btn-group-justified" >
									  <div class="btn-group" >
									    <button type="submit" class="btn btn-success">
									    	<span class="glyphicon glyphicon-log-in"></span>&nbsp;登录</button>
									  </div>
									  <div class="btn-group" >
									    <button type="button" class="btn btn-danger">
									    	<span class="glyphicon glyphicon-edit"></span>注册</button>
									  </div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
</body>
</html>