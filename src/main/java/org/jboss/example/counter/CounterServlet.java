/***************************************
 *                                     *
 *  JBoss: The OpenSource J2EE WebOS   *
 *                                     *
 *  Distributable under LGPL license.  *
 *  See terms of license at gnu.org.   *
 *                                     *
 ***************************************/

package org.jboss.example.counter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author  Stan Silvert
 */
public class CounterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 111431634144405770L;

	public static final Logger LOG = LogManager.getLogger(CounterServlet.class);
    
    private String titleMessage = "Counter Servlet";
    
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.titleMessage = config.getInitParameter("titleMessage");
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
        
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + this.titleMessage + "</title>");
        out.println("</head>");
        
        out.println("<body>");
        out.println("<font size='16'>");
        out.println(this.titleMessage);
        out.println("</font><br><br>");
        

        
        Counter counter = getSessionObj(session);
        counter.increment();
        
        LOG.info("*****************");
        LOG.info("Counter = " + counter.getValue());
        LOG.info("sessionID = " + request.getSession().getId());
        LOG.info("*****************");
  
        out.println("Request URL = " + request.getRequestURL());   
        out.println("<br>Request URI = " + request.getRequestURI());
        out.println("<br>Server Name = " + request.getServerName());
        out.println("<br>Local Addr = " + request.getLocalAddr());
        out.println("<br>Remote Port =  " + request.getRemotePort());
        out.println("<br>Local Port =  " + request.getLocalPort());
        out.println("<br>Server Port =  " + request.getServerPort());
        out.println("<br>sessionID = " + request.getSession().getId());

        InetAddress inetAddress = InetAddress.getLocalHost();
        out.println("<br>Inet IP Address = " + inetAddress.getHostAddress());
        out.println("<br>Inet Host Name =  " + inetAddress.getHostName());

        out.println("<br>Jboss Server Name = " + System.getProperty( "jboss.server.name" ));
        
        
        out.println("<br>Counter = " + counter.getValue());
        
        out.println("<br><br>Sample Curl <br><br>" + "curl -v " + request.getRequestURL() + " --header \"Cookie: JSESSIONID=" + request.getSession().getId() + '"');

        out.println("</body>");
        out.println("</html>");
        

        
        // do i need to write this to preserve the session?
        session.setAttribute("foo", counter);

        out.close();
    }
    
    private Counter getSessionObj(HttpSession session) {
        Counter counter = (Counter)session.getAttribute("foo");
        if (counter == null) {
            counter = new Counter();
            session.setAttribute("foo", counter);
        }
        
        return counter;
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Counter Servlet";
    }
    
}
