<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="controller" method="post">
		
		<input type="hidden" name="command" value="add_News" />
		              						
		<label> News Title:<br />
		<input type="text" name="title" value="" size=15 maxlength=150 required="required"/><br /> </label>
        
        <label>News Date:<br />
		<input type="text" name="data" value="" size=15 maxlength=150 required="required"/><br /> </label>
						
		<label> News Content:<br />
		<input type="text" name="content" value="" size=500 maxlength=150 required="required"/><br /> </label>
						
		<input type="submit" value="Enter" /><br />      
        
 </form>
</body>
</html> 
