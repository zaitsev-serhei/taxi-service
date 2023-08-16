<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manufacturer Page</title>
    <style>
        .title {
            color: grey;
            font-size: 18px;
            width: 10%;
            text-align: center;
            padding: 14px;
        }
        .button {
            border: none;
            outline: 0;
            display: inline-block;
            padding: 8px;
            color: white;
            background-color: #50aa82;
            text-align: center;
            cursor: pointer;
            width: 100%;
            font-size: 18px;
            margin-top: 8px;
            margin-bottom: 8px;
        }

        a {
            text-decoration: none;
            font-size: 22px;
            color: black;
        }

        button:hover, a:hover {
            opacity: 0.7;
        }

        .main {
            margin-left: 160px; /* Same as the width of the sidenav */
            font-size: 28px; /* Increased text to enable scrolling */
            padding: 0px 10px;
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
        }

        .topnav {
            overflow: hidden;
            background-color: #e9e9e9;
        }

        .topnav a {
            float: left;
            display: block;
            color: black;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav a.active {
            background-color: #2196F3;
            color: white;
        }

        @media screen and (max-width: 600px) {
            .topnav a {
                float: none;
                display: block;
                text-align: left;
                width: 100%;
                margin: 0;
                padding: 14px;
            }
        }
    </style>
</head>
<body>
<div class="main">
    <div>
        <jsp:include page="/WEB-INF/views/sideMenu.jsp"/>
    </div>
    <H1>Manufacturers</H1>
    <div class="topnav">
        <a class="active" href="${pageContext.request.contextPath}/manufacturers">View all</a>
        <a href="${pageContext.request.contextPath}/manufacturers/add">Add new</a>
    </div>
    <div>
        <table border="1">
            <tr class="title">
                <th>ID</th>
                <th>NAME</th>
                <th>COUNTRY</th>
                <th>ACTIONS</th>
            </tr>
            <c:forEach var="manufacturer" items="${manufacturers}">
                <tr>
                    <th>
                        <h1 class="title"><c:out value="${manufacturer.id}"/></h1>
                    </th>
                    <th>
                        <h1 class="title"><c:out value="${manufacturer.name}"/></h1>
                    </th>
                    <th>
                        <h1 class="title"><c:out value="${manufacturer.country}"/></h1>
                    </th>
                    <th>
                        <div>
                            <a href="${pageContext.request.contextPath}/manufacturers/delete?id=${manufacturer.id}" class="button">Delete</a>
                        </div>
                    </th>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
