<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<%@ page import="org.springframework.security.web.authentication.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
 
<html>
<body>
<h1>Login</h1>
 
	<form name="f" action="<c:url value="/loginProcess" />" method="post">
		<table>
			<tr>
				<td>Username:</td>
				<td><input type="text" 
						 name="j_username" 
						 id="j_username" <c:if test="${not empty param.login_error}">value="<%= session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>"</c:if> /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="j_password" id="j_password" /></td>
			</tr>
			<tr><td colspan="2" align="center"><input name="submit" id="submit" type="submit" value="Login" /></td></tr>
		</table>
	</form>
	<c:if test="${not empty param.login_error}">
		<font color="red">
			Login failure!<br />
			Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
		</font>
	</c:if>
 
<sec:authorize access="isAuthenticated()">
   You are currently logged in!
</sec:authorize>
 
</body>
</html>