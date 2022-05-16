<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 24/02/2022
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="shortcut icon" href="<c:url value="/assets/user/img/favicon.ico"/>" type="image/x-icon"/>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
        integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous"/>
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/assets/admin/css/bootstrap.min.css'/>"/>
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/assets/admin/css/style.css'/>"/>
  <title>Page - admin</title>
</head>

<body>
<section class="all-content">
  <tiles:insertAttribute name="header"/>
  <!--SEARCH-->
  <tiles:insertAttribute name="page-search"/>
  <!--END SEARCH-->
  <tiles:insertAttribute name="body"/>
</section>
<!--FOOTER-->
<tiles:insertAttribute name="footer"/>
<!--END FOOTER-->
</body>

</html>
<script src="<c:url value='/assets/admin/js/jquery-3.3.1.min.js'/>"></script>
<script src="<c:url value='/assets/admin/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/assets/admin/js/js.js'/>"></script>
