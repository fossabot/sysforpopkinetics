<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%-- Remove the validUser session bean, if any --%>
<c:remove var="validUser" />

<c:if test="${empty param.userName || empty param.password}">
  <c:redirect url="index.jsp" >
    <c:param name="errorMsg" value="You must enter a User Name and Password." />
  </c:redirect>
</c:if>

<%--
  See if the user name and password combination is valid. If not
  redirect back to the login page with a message.
--%>
<jsp:useBean id="digest" scope="session" class="uw.rfpk.beans.DigestPassword" >
  <c:set target="${digest}" property="password" value="${param.password}" />
</jsp:useBean>
<sql:query var="userInfo">
  SELECT * FROM user WHERE username = ? AND password = ?
  <sql:param value="${param.userName}" />
  <sql:param value="${digest.password}" />
</sql:query>

<c:if test="${userInfo.rowCount == 0}">
  <c:redirect url="index.jsp" >
    <c:param name="errorMsg" value="The User Name or Password you entered is not valid." />
  </c:redirect>
</c:if>

<%--
  Create a UserBean and save it in the session
  scope and redirect to the appropriate page
--%>
<c:set var="dbValues" value="${userInfo.rows[0]}" />
<jsp:useBean id="validUser" scope="session" class="uw.rfpk.beans.UserInfo" >
  <c:set target="${validUser}" property="userName" value="${dbValues.username}" />
  <c:set target="${validUser}" property="firstName" value="${dbValues.first_name}" />
  <c:set target="${validUser}" property="lastName" value="${dbValues.surname}" />
</jsp:useBean>
 
<c:choose>
  <c:when test="${!empty param.origURL}">
    <c:redirect url="${param.origURL}" />
  </c:when>
  <c:otherwise>
    <c:redirect url="main.jsp" />
  </c:otherwise>
</c:choose>
