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
    <H1>Add Driver To Car</H1>
    <div class="topnav">
        <a href="${pageContext.request.contextPath}/cars">View all</a>
        <a href="${pageContext.request.contextPath}/cars/add">Add new</a>
        <a class="active" href="${pageContext.request.contextPath}/cars/drivers/add">Add Driver to Car</a>
    </div>
    <div>
        <form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
            <table border="2" style="width: 100%">
                <tr>
                    <th>Car</th>
                    <th>Driver</th>
                </tr>
                <tr>
                    <c:choose>
                        <c:when test="${car != null}">
                            <th style="width: 50%">
                                <div class="form-group">
                                    <label class="col-form-label" for="carId">Model:</label><br/>
                                    <input class="form-control" id="carId" name="carId" type="text"
                                           value="${car.id}" required="required" readonly>
                                </div>
                                <div class="form-group">
                                    <label class="col-form-label" for="model">Model:</label><br/>
                                    <input class="form-control" id="model" name="model" type="text"
                                           value="${car.model}" required="required" readonly>
                                </div>
                                <div class="form-group">
                                    <label class="col-form-label" for="name">Manufacturer:</label><br/>
                                    <input class="form-control" id="name" name="name" type="text"
                                           value="${car.manufacturer.name}" required="required" readonly>
                                </div>
                                <div class="form-group">
                                    <label class="col-form-label" for="country">Manufacturer:</label><br/>
                                    <input class="form-control" id="country" name="country" type="text"
                                           value="${car.manufacturer.country}" required="required" readonly>
                                </div>
                            </th>
                        </c:when>
                    </c:choose>
                    <div></div>

                    <th style="width: 50%">
                        <select name="option_driver" style="width: 100%">
                            <option>Select Driver</option>
                            <c:forEach var="op_driver" items="${driverList}">
                                <option value="${op_driver.id}"><c:out value="${op_driver.id}"/> -- <c:out
                                        value="${op_driver.name}"/></option>
                            </c:forEach>
                        </select><br>
                    </th>
                </tr>
                <tr>
                    <th colspan="2">
                        <button class="button" type="submit">Add driver</button>
                    </th>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
