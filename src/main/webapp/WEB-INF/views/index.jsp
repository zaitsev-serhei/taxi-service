<%@ page contentType="text/html" language="java" %>
<html>
<head>
    <style>
        body {
            font-family: "Lato", sans-serif;
        }

        .main {
            margin-left: 160px; /* Same as the width of the sidenav */
            font-size: 28px; /* Increased text to enable scrolling */
            padding: 0px 10px;
        }

    </style>
</head>
<body>

<div>
    <jsp:include page="/WEB-INF/views/sideMenu.jsp"/>
</div>

<div class="main">
    Welcome to the Taxi Application
</div>
</body>
</html>
