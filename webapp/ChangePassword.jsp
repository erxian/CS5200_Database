<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="musicraze.model.Users"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Change Password</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="mt-2 container">
		<% response.setHeader("Cache-Control","no-cache"); %>
	    <% response.setHeader("Cache-Control","no-store"); %>
	    <% response.setHeader("Pragma","no-cache"); %>
	    <% response.setDateHeader ("Expires", 0); %>
		<% Users user = (Users) (session.getAttribute("user")); %>
		<% request.setAttribute("user", user); %>
		<% if (user == null) { %>
			<script>
			    function() {
					window.location.href = "/UserLogin.jsp"
			    }
		    </script>
		<% } else { %>
		<form action="ChangePassword" method="post">
			<div class="form-group">
				<label for="oldPassword" class="row">Old Password: </label>
				<input id="oldPassword" type="password" class="row col-6" name="oldPassword" value="" />
				<p id="alertOldPassword" style="color: red">${alerts.oldPassword}</p>
			</div>
			<div class="form-group">
				<label for="newPassword" class="row">New Password: </label>
				<input id="newPassword" type="password" class="row col-6" name="newPassword" value="" />
				<p id="alertNewPassword" style="color: red">${alerts.newPassword}</p>
			</div>
			<div class="form-group">
				<label for="confirmPassword" class="row">Confirm Password: </label>
				<input id="confirmPassword" type="password" class="row col-6" name="confirmPassword" value="" />
				<p id="alertConfirmPassword" style="color: red">${alerts.confirmPassword}</p>
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary row col-6">Save</button>
			</div>
		</form>
		<% } %>
	</div>
</body>
</html>