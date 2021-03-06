<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Тимур
  Date: 25.10.2016
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<% if (request.getAttribute("ok") == null) {%>
<form class="addfilmform" action="/addrole" method="post">
    <p>
    <div id = "same">
    <h2>Add role</h2>
    </div>
    <p>
        <input type="text" name="name" class="input-block-level" id="addfilmfields" placeholder="Name">
        <br>
        <button class="btn btn-large btn-primary" type="submit">Add</button>
            <% if (request.getAttribute("no")!=null) {%>
    <p><%=request.getAttribute("no")%>
    </p>
    <%}%>
</form>
<%} else {%>
<br>
<div style="left: 15%" class="filmisadded">
    <p>
    <H2><%=request.getAttribute("ok")%>
    </H2></p>
    <button><a href="/addrole">Add another role.</a></button>
    <button><a href="/home">Go home!</a></button>
</div>
<%}%>
<% if (request.getAttribute("genres")!=null) {%>
<div class="container">
    <p>Roles</p>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>id</th>
            <th>Role</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <%if (request.getAttribute("genres")!=null) {%>
        <% for (Role genre : (List<Role>)request.getAttribute("genres")) {%>
        <tr>
            <td><%=genre.getId()%></td>
            <td><%=genre.getName()%></td>
            <td><a class="btn btn-sm btn-danger btn-block" style="width:100px;"
                   href="/delete?role_id=<%=genre.getId()%>" role="button">Delete</a></td>
        </tr>
        <%}%>
        <%}%>
        </tbody>
    </table>
</div>
<%}%>
</body>
</html>
