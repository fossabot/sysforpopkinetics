<?xml version="1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "xhtml1-transitional.dtd">
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Verify that the user is logged in --%>
<c:if test="${validUser == null || validUser.userName != 'useradmin'}">
  <jsp:forward page="index.jsp">
    <jsp:param name="origURL" value="${pageContext.request.requestURL}" />
    <jsp:param name="errorMsg" value="Please log in first." />
  </jsp:forward>
</c:if>

<html>
  <head>
    <title>All User List</title>
  </head>
  <body bgcolor="white">

    <%-- Set number of rows to process --%>
    <c:set var="noOfRows" value="${initParam.maxNum}" />

    <sql:query var="userList"
      sql="SELECT * FROM user ORDER BY surname" 
      startRow="${param.start}" maxRows="${noOfRows}"
    />
    <table align=left border=0 width=602>
      <tbody> 
	<tr>    
	  <td colSpan=3 vAlign=top>
	    <img align=top alt="RFPK logo" height=40 src="./images/rfpklogo.gif" width=112>
	      <img align=top alt="Resource Facility for Population Kinetics" height=40 
		src="./images/rfpkfull.gif" width=474>
 	  </td> 
	</tr> 
	<tr vAlign=top> <td colSpan=3><p>&nbsp;</p></td></tr> 
	<tr>
	  <td vAlign=top width=102 height="0" colspan="1" rowspan="1">
          <%@ include file="quicklinks.shtml" %>  
	  </td>
	  <td colspan=1 vAlign=top width=10><img alt="trans gif" height=5 src="./images/white.gif" width=10/>
	  <td colspan=1 vAlign=top>
	    <h3>All User List</h3>
	    <p> 
          <c:choose>
            <c:when test="${userList.rowCount == 0}">
              No one seems to use it anymore ...
            </c:when>
            <c:otherwise>
              The following users are found:
            <p>
            <table border="1">
              <th>Usr ID</th>
              <th>User Name</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Company</th>
              <th>Country</th>
              <th>State</th>
              <th>Email</th>
              <th>Tester</th>
              <th>Developer</th>
              <c:forEach items="${userList.rows}" var="row">
              <tr>
                <td><a href=getuser.jsp?userName=${fn:escapeXml(row.username)}&password=${fn:escapeXml(row.password)}>${fn:escapeXml(row.user_id)}</a></td>
                <td>${fn:escapeXml(row.username)}</td>
                <td>${fn:escapeXml(row.first_name)}</td>
                <td>${fn:escapeXml(row.surname)}</td>
                <td>${fn:escapeXml(row.company)}</td>
                <td>${fn:escapeXml(row.country)}</td>
                <td>${fn:escapeXml(row.state)}</td>
                <td>${fn:escapeXml(row.email)}</td>
                <c:choose>
                  <c:when test="${row.test == 1}">
                    <td>Yes</td>
                  </c:when>
                  <c:otherwise>
                    <td>No</td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${row.dev == 1}">
                    <td>Yes</td>
                  </c:when>
                  <c:otherwise>
                    <td>No</td>
                  </c:otherwise>
                </c:choose>
              </tr>
              </c:forEach>
            </table>
            </c:otherwise>
          </c:choose>
          <p>
          <c:choose>
            <c:when test="${param.start > 0}">
              <a href="userlist.jsp?start=${param.start - noOfRows}">
                Previous Page</a>
            </c:when>
            <c:otherwise>
                Previous Page
          </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${userList.limitedByMaxRows}">
              <a href="userlist.jsp?start=${param.start + noOfRows}">
                Next Page</a>
            </c:when>
          <c:otherwise>
            Next Page
          </c:otherwise>
          </c:choose>
	    </p>
	    <p> 
               When you are done, please <a href="logout.jsp">log out</a>.
	    </p>
	  </td>
	</tr>
      </tbody>
    </table>
  </body>
</html>
