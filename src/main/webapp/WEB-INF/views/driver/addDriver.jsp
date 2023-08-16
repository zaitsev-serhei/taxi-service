<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Car Page</title>
    <style>
        button {
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
        <form method="post" action="${pageContext.request.contextPath}/drivers/add">
            <div class="form-group">
                <label class="col-form-label" for="name">Name:</label><br/>
                <input class="form-control" id="name" name="name" type="text" required="required">
            </div>
            <div class="form-group">
                <label class="col-form-label" for="licenseNumber">License Number:</label><br/>
                <input class="form-control" id="licenseNumber" name="licenseNumber" type="text" required="required">
            </div>
            <br/>
            <button class="btn btn-primary" type="submit">Add driver</button>
        </form>
    </div>
</div>
</body>
</html>
