<%@page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Verify that the user is logged in --%>
<c:if test="${validUser == null}">
  <jsp:forward page="index.jsp">
    <jsp:param name="origURL" value="${pageContext.request.requestURL}" />
    <jsp:param name="errorMsg" value="Please log in first." />
  </jsp:forward>
</c:if>

<html>
<head>
  <title>Member Main Page</title>
  <link href=stylesheet.css type="text/css" rel=stylesheet>
</head>
  <body>
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
	  <td valign=top width=102 height="0" colspan="1" rowspan="1">
            <%@ include file="quicklinks.shtml" %>  
	  </td>
	  <td colspan=1 vAlign=top width=10><img alt="trans gif" height=5 src="./images/white.gif" width=10/>
	  <td colspan=1 vAlign=top>
	    <h3>Welcome ${fn:escapeXml(validUser.firstName)}  ${fn:escapeXml(validUser.lastName)}</h1>
	    <p>
               Select one of the following:
            </p>
            <p>
               Change my password<br>
               <a href="getmda.jsp">Get my Model Design Agent</a><br>
               View my job status<br>
               View my model archive<br>
               View my dataset archive<br>
               View model library<br>
               View my account information
               <c:if test="${validUser.userName == 'scientist'}">
                 <br>Manage user account
                 <br>Manage model library
               </c:if>           
            </p>
               When you are done, please <a href="logout.jsp">log out</a>.
       	  </td>
	</tr>     
      </tbody>
    </table>
  </body>
</html>