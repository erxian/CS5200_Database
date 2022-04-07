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
<title>Edit Profile</title>
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
		<form action="EditProfile" method="post">
			<div class="form-group">
				<label for="firstName" class="row">First Name: </label>
				<input id="firstName" class="row col-6" name="firstName"
					value="${user.getFirstName()}" />
				<p id="alertFirstName" style="color: red">${alerts.firstName}</p>
			</div>
			<div class="form-group">
				<label for="lastName" class="row">Last Name: </label>
				<input id="lastName" class="row col-6" name="lastName"
					value="${user.getLastName()}" />
				<p id="alertLastName" style="color: red">${alerts.lastName}</p>
			</div>
			<div class="form-group">
				<label for="email" class="row">Email: </label>
				<input id="email" class="row col-6" name="email"
					value="${user.getEmail()}" />
				<p id="alertEmail" style="color: red">${alerts.email}</p>
			</div>
			<div class="form-group">
				<label for="avatar" class="row">Avatar: </label>
				<input id="avatar" class="row col-6" name="avatar"
					value="${user.getAvatar()}" />
				<p id="alertAvatar" style="color: red">${alerts.avatar}</p>
			</div>
			<div class="form-group">
				<label for="bio" class="row">Bio: </label>
				<input id="bio" class="row col-6" name="bio"
					value="${user.getBio()}" />
				<p id="alertBio" style="color: red">${alerts.bio}</p>
			</div>
			<div class="form-group">
				<label for="bornDate" class="row">Born Date: </label>
				<input id="bornDate" class="row col-6" name="bornDate"
					value="${user.getBornDateStr()}" />
				<p id="alertBornDate" style="color: red">${alerts.bornDate}</p>
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary row col-6">Save</button>
			</div>
		</form>
		<% } %>
	</div>
</body>
</html>