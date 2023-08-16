<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    ul,li {
        padding: 0;
        list-style-type: none;
    }
    .sidenav {
        height: 100%;
        width: 160px;
        position: fixed;
        z-index: 1;
        top: 0;
        left: 0;
        background-color: #50aa82;
        overflow-x: hidden;
        padding-top: 20px;
    }

    .sidenav a {
        padding: 6px 8px 6px 16px;
        text-decoration: none;
        font-size: 14px;
        color: #555252;
        display: block;
    }

    .sidenav a:hover {
        color: #f1f1f1;
    }

    @media screen and (max-height: 450px) {
        .sidenav {padding-top: 15px;}
        .sidenav a {font-size: 18px;}
    }
</style>
<header>
    <div class="sidenav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/cars">Cars</a></li>
            <li><a href="${pageContext.request.contextPath}/drivers">Drivers</a></li>
            <li><a href="${pageContext.request.contextPath}/manufacturers">Manufacturers</a></li>
        </ul>
    </div>
</header>
</html>
