package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Vector;

public final class datasetlibrary_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.Vector _jspx_dependants;

  static {
    _jspx_dependants = new java.util.Vector(1);
    _jspx_dependants.add("/quicklinks.shtml");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_if_test;

  public java.util.List getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_if_test.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!---------------------------------------------------------------------\n");
      out.write("From:   Resource Facility for Population Kinetics                    \n");
      out.write("        Department of Bioengineering Box 352255                      \n");
      out.write("        University of Washington                                     \n");
      out.write("        Seattle, WA 98195-2255                                       \n");
      out.write("\n");
      out.write("This file is part of the System for Population Kinetics (SPK), which\n");
      out.write("was developed with support from NIH grants RR-12609 and P41-\n");
      out.write("EB001975. Please cite these grants in any publication for which this\n");
      out.write("software is used and send a notification to the address given above.\n");
      out.write("\n");
      out.write("SPK is Copyright (C) 1998-2003, by the University of Washington,\n");
      out.write("Resource Facility for Population Kinetics, and is made available as\n");
      out.write("free open source software under the terms of the University of\n");
      out.write("Washington Free-Fork License as a public service.  A copy of the\n");
      out.write("License can be found in the COPYING file in the root directory of this\n");
      out.write("distribution.\n");
      out.write("---------------------------------------------------------------------->\n");
      out.write("<!--\n");
      out.write("author: Jiaji Du\n");
      out.write("-->\n");
      out.write("<?xml version=\"1.0\"?>\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"xhtml1-transitional.dtd\">\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write('\n');
      if (_jspx_meth_c_if_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("  <head>\n");
      out.write("    <title>Library Dataset List</title>\n");
      out.write("  </head>\n");
      out.write("  <body bgcolor=\"white\">\n");
      out.write("\n");
      out.write("    <table align=left border=0 width=602>\n");
      out.write("      <tbody> \n");
      out.write("\t<tr>    \n");
      out.write("\t  <td colSpan=3 vAlign=top>\n");
      out.write("\t    <img align=top alt=\"RFPK logo\" height=40 src=\"./images/rfpklogo.gif\" width=112>\n");
      out.write("\t      <img align=top alt=\"Resource Facility for Population Kinetics\" height=40 \n");
      out.write("\t\tsrc=\"./images/rfpkfull.gif\" width=474>\n");
      out.write(" \t  </td> \n");
      out.write("\t</tr> \n");
      out.write("\t<tr vAlign=top> <td colSpan=3><p>&nbsp;</p></td></tr> \n");
      out.write("\t<tr>\n");
      out.write("\t  <td vAlign=top width=150 height=\"0\" colspan=\"1\" rowspan=\"1\">\n");
      out.write("          ");
      out.write("<!---------------------------------------------------------------------\n");
      out.write("From:   Resource Facility for Population Kinetics                    \n");
      out.write("        Department of Bioengineering Box 352255                      \n");
      out.write("        University of Washington                                     \n");
      out.write("        Seattle, WA 98195-2255                                       \n");
      out.write("\n");
      out.write("This file is part of the System for Population Kinetics (SPK), which\n");
      out.write("was developed with support from NIH grants RR-12609 and P41-\n");
      out.write("EB001975. Please cite these grants in any publication for which this\n");
      out.write("software is used and send a notification to the address given above.\n");
      out.write("\n");
      out.write("SPK is Copyright (C) 1998-2003, by the University of Washington,\n");
      out.write("Resource Facility for Population Kinetics, and is made available as\n");
      out.write("free open source software under the terms of the University of\n");
      out.write("Washington Free-Fork License as a public service.  A copy of the\n");
      out.write("License can be found in the COPYING file in the root directory of this\n");
      out.write("distribution.\n");
      out.write("---------------------------------------------------------------------->\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
      out.write("\n");
      out.write("<font size=2>\n");
      out.write("<p class=quick>Quick Links</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"http://depts.washington.edu/rfpk/\">RFPK Home</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"/info/index.jsp\">SPK Home</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"index.jsp\">MySPK</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"RFPK_SPK_TERMS_OF_SERVICE.html\" target=\"_blank\">Terms of Service</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"usermain.jsp\">Member</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"chpassword.jsp\">Password</a>\n");
      out.write("</p> \n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"getmda.jsp\">Download</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"WebHelp1/SPKStart.htm\" target=\"_blank\">Getting Started</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"SPK_User_Manual.pdf\">User Manual</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"userjobs.jsp\">My Jobs</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"usermodels.jsp?start=0&counter=0\">My Models</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"userdatasets.jsp?start=0&counter=0\">My Datasets</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"examplejobs.jsp?start=0&counter=0\">Job Examples</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"modellibrary.jsp?start=0&counter=0\">Model Library</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"datasetlibrary.jsp?start=0&counter=0\">Dataset Library</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"userinfo.jsp\">My Account</a>\n");
      out.write("</p>\n");
      out.write("<p>\n");
      out.write("    <a class=quick href=\"http://bugzilla.rfpk.washington.edu\">Bug Report</a>\n");
      out.write("</p>\n");
      out.write("</font>\n");
      out.write("  \n");
      out.write("\t  </td>\n");
      out.write("\t  <td colspan=1 vAlign=top width=10><img alt=\"trans gif\" height=5 src=\"./images/white.gif\" width=10/>\n");
      out.write("\t  <td colspan=1 vAlign=top>\n");
      out.write("\t    <h3>Library Dataset List</h3>\n");
      out.write("\t    <p> \n");
      out.write("              ");
      uw.rfpk.beans.DatasetList library = null;
      synchronized (request) {
        library = (uw.rfpk.beans.DatasetList) _jspx_page_context.getAttribute("library", PageContext.REQUEST_SCOPE);
        if (library == null){
          library = new uw.rfpk.beans.DatasetList();
          _jspx_page_context.setAttribute("library", library, PageContext.REQUEST_SCOPE);
        }
      }
      out.write("\n");
      out.write("              ");
 int maxNum = Integer.parseInt(getServletContext().getInitParameter("maxNum")); 
                 library.setUsername("librarian");
                 library.setDbHost(getServletContext().getInitParameter("database_host"));
                 library.setDbName(getServletContext().getInitParameter("database_name"));
                 library.setDbUser(getServletContext().getInitParameter("database_username"));
                 library.setDbPass(getServletContext().getInitParameter("database_password"));
                 Vector datasetList = library.getDatasetList(maxNum + 1, Long.parseLong(request.getParameter("start")));
                 Vector startList = null;
                 int counter = Integer.parseInt(request.getParameter("counter"));
                 int size = datasetList.size();
                 boolean isMore = false; 
                 if(size == 0)
                 { 
      out.write("\n");
      out.write("                     No dataset seems to be in there anymore ...\n");
      out.write("              ");
 }
                 else
                 {
                     if(counter == 0)
                     {
                         startList = new Vector();
                         startList.add(((String[])datasetList.get(0))[0]);
                         session.setAttribute("START", startList); 
                     }
                     else
                     {
                         startList = (Vector)session.getAttribute("START");
                         if(counter >= startList.size())
                         {
                             startList.add(((String[])datasetList.get(0))[0]);
                             session.setAttribute("START", startList); 
                         }
                     } 
      out.write("\n");
      out.write("                     The following library datasets are found:\n");
      out.write("                 <p>\n");
      out.write("                 <table border=\"1\">\n");
      out.write("                 <th>Dataset Name</th>\n");
      out.write("                 <th>No. of Versions</th>\n");
      out.write("                 <th>Last Revised Time</th>\n");
      out.write("                 <th>Description</th>\n");
      out.write("                  ");
 
                     if(size > maxNum)
                     {
                         size = maxNum;
                         isMore = true;
                     }
                     for(int i = 0; i < size; i++)
                     { 
                         String[] dataset = (String[])datasetList.get(i);
                         String link = "<a href=versions.jsp?id=" + dataset[0] + "&type=data>" + dataset[1] + "</a>";  
                         String description = dataset[4].startsWith("http://") || dataset[4].startsWith("https://") ? 
                                              "<a href=" + dataset[4] + ">" + dataset[4] + "</a>" : dataset[4]; 
      out.write("         \n");
      out.write("                  <tr>\n");
      out.write("                    <td>");
      out.print(link);
      out.write("</td>\n");
      out.write("                    <td align=\"center\">");
      out.print(dataset[2]);
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(dataset[3]);
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(description);
      out.write("</td>\n");
      out.write("                  </tr>\n");
      out.write("                  ");
 } 
      out.write("\n");
      out.write("                 </table>\n");
      out.write("            <p>\n");
      out.write("              ");
 if(counter > 0)
                 {
                     long start = Long.parseLong((String)startList.get(counter - 1)) + 1;
                     int count = counter - 1;
                     String pageLink = "<a href=datasetlibrary.jsp?start=" + String.valueOf(start) + 
                                       "&counter=" + String.valueOf(count) + ">Previous Page</a>"; 
      out.write("\n");
      out.write("                     ");
      out.print(pageLink);
      out.write("\n");
      out.write("              ");
 }
                 else
                 { 
      out.write("\n");
      out.write("                     Previous Page\n");
      out.write("              ");
 }
                 if(isMore)
                 {
                     int count = counter + 1;
                     String pageLink = "<a href=datasetlibrary.jsp?start=" + ((String[])datasetList.get(size - 1))[0] + 
                                       "&counter=" + String.valueOf(count) + ">Next Page</a>"; 
      out.write("\n");
      out.write("                     ");
      out.print(pageLink);
      out.write("\n");
      out.write("              ");
 }
                 else 
                 { 
      out.write("\n");
      out.write("                     Next Page\n");
      out.write("              ");
 }} 
      out.write("\n");
      out.write("\t    </p>\n");
      out.write("\t    <p> \n");
      out.write("               You may click the dataset name to see the version list of the dataset.\n");
      out.write("               When you are done, please <a href=\"logout.jsp\">log out</a>.\n");
      out.write("\t    </p>\n");
      out.write("\t  </td>\n");
      out.write("\t</tr>\n");
      out.write("\t</tr>\n");
      out.write("      </tbody>\n");
      out.write("    </table>\n");
      out.write("  </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_if_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_if_0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _jspx_tagPool_c_if_test.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_if_0.setPageContext(_jspx_page_context);
    _jspx_th_c_if_0.setParent(null);
    _jspx_th_c_if_0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${validUser == null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_if_0 = _jspx_th_c_if_0.doStartTag();
    if (_jspx_eval_c_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('\n');
        out.write(' ');
        out.write(' ');
        if (true) {
          _jspx_page_context.forward("index.jsp" + (("index.jsp").indexOf('?')>0? '&': '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("origURL", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.requestURL}", java.lang.String.class, (PageContext)_jspx_page_context, null, false), request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("errorMsg", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("Please log in first.", request.getCharacterEncoding()));
          return true;
        }
        out.write('\n');
        int evalDoAfterBody = _jspx_th_c_if_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_if_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE)
      return true;
    _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
    return false;
  }
}
