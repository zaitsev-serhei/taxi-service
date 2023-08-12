<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Driver Page</title>
    <style>
        .title {
            color: grey;
            font-size: 18px;
            width: 10%;
            text-align: center;
            padding: 14px;
        }

        .button     {
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
    </style>
</head>
<body>
<div class="main">
    <div>
        <jsp:include page="/WEB-INF/views/sideMenu.jsp"/>
    </div>
    <H1>Drivers</H1>
    <div>
        <jsp:include page="/WEB-INF/views/topMenu.jsp"/>
    </div>
    <div>
        <table border="1">
            <tr class="title">
                <th>ID</th>
                <th>NAME</th>
                <th>LICENSE NUMBER</th>
                <th>ACTIONS</th>
            </tr>
            <c:forEach var="car" items="${drivers}">
                <tr>
                    <th>
                        <h1 class="title"><c:out value="${car.id}"/></h1>
                    </th>
                    <th>
                        <h1 class="title"><c:out value="${car.name}"/></h1>
                    </th>
                    <th>
                        <h2 class="title"> <c:out value="${car.licenseNumber}"/></h2>
                    </th>
                    <th>
                        <div>
                            <a href="${pageContext.request.contextPath}/drivers/delete?id=${car.id}" class="button">Delete</a>
                        </div>
                    </th>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
