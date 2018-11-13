<%-- 
    Document   : login
    Created on : Oct 30, 2018, 6:52:27 PM
    Author     : thomas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% String error = (String) request.getAttribute("error");
        if(error != null)    
            out.print("<h1 style=\"color:red;\">"+error+"</h1>");
        %>
        <form action="FrontControl" method="POST">
            Username: <input type="text" name="username" value="" /><br/>
            Password: <input type="text" name="password" value="" /><br/>
            <input type="hidden" name="origin" value="login"/>
            <input type="submit" name="submit" value="Login" />
        </form>
        
    </body>
</html>
