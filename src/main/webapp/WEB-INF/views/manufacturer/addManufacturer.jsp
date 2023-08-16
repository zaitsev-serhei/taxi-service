<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manufacturer Page</title>
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

            .topnav input[type=text] {
                border: 1px solid #ccc;
            }
        }
    </style>
</head>
<body>
<div class="main">
    <div>
        <jsp:include page="/WEB-INF/views/sideMenu.jsp"/>
    </div>
    <H1>Drivers</H1>

    <div class="topnav">
        <a href="${pageContext.request.contextPath}/manufacturers">View all</a>
        <a class="active" href="${pageContext.request.contextPath}/manufacturers/add">Add new</a>
    </div>
        <div>
            <form method="post" action="${pageContext.request.contextPath}/manufacturers/add">
                <div class="form-group">
                    <label class="col-form-label" for="name">Name :</label><br/>
                    <input class="form-control" id="name" name="name" type="text" required="required">
                </div>
                <div class="form-group">
                    <label class="col-form-label" for="country">Country :</label><br/>
                    <input class="form-control" id="country" name="country" type="text" required="required">
                </div>
                <br/>
                <button type="submit">Add manufacturer</button>
            </form>
        </div>
    </div>
</body>
</html>
