<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 24/02/2022
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="vi">

<head>
  <!-- Basic -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <!-- Mobile Metas -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name='robots' content='index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1'/>
  <meta name="author" content="laptrinhcs">
  <meta name="geo.region" content="VN"/>
  <meta name="geo.placename" content="Huế"/>
  <meta name="geo.position" content="16.463932;107.586339"/>
  <meta name="ICBM" content="16.463932, 107.586339"/>
  <!-- Site Metas -->

  <c:choose>
    <c:when test="${post != null && post.postId > 0}">
      <meta name="keywords" content="${post.title}">
      <meta name="description" content="${post.title}">

      <c:set var="req" value="${pageContext.request}"/>
      <c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}"/>
      <c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
      <c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
      <c:set var="pageUrl" value="${ baseURL }${ requestPath }${ not empty params?'?'+=params:'' }"/>
      <meta property="og:url" content="${pageUrl}"/>
      <meta property="og:type" content="article"/>
      <meta property="og:title" content="${post.title}"/>
      <meta property="og:description" content="${post.description}"/>
      <meta property="og:image" content="${post.image}"/>
      <meta property="og:image:width" content="640"/>
      <meta property="og:image:height" content="360"/>
    </c:when>
    <c:otherwise>
      <meta name="keywords" content="Forest time - blog chia sẻ kiến thức">
      <meta name="description" content="Forest time - blog chia sẻ kiến thức">
    </c:otherwise>
  </c:choose>

  <!-- Site Icons -->
  <link rel="shortcut icon" href="<c:url value="/assets/user/img/favicon.ico"/>" type="image/x-icon"/>
  <link rel="apple-touch-icon" href="<c:url value='/assets/user/img/apple-touch-icon.png'/>">

  <!-- Design fonts -->
  <link href="https://fonts.googleapis.com/css?family=Droid+Sans:400,700" rel="stylesheet">

  <!-- Bootstrap core CSS -->
  <link href="<c:url value='/assets/user/css/bootstrap.css'/>" rel="stylesheet">

  <!-- FontAwesome Icons core CSS -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <!-- Custom styles for this template -->
  <link href="<c:url value="/assets/user/css/style.css"/>" rel="stylesheet">

  <!-- Responsive styles for this template -->
  <link href="<c:url value='/assets/user/css/responsive.css'/>" rel="stylesheet">

  <!-- Colors for this template -->
  <link href="<c:url value="/assets/user/css/colors.css"/>" rel="stylesheet">

  <!-- Version Garden CSS for this template -->
  <link href="<c:url value='/assets/user/css/version/garden.css'/>" rel="stylesheet">

<%--  <!-- Global site tag (gtag.js) - Google Analytics -->--%>
<%--  <script async src="https://www.googletagmanager.com/gtag/js?id=G-31DLD5Y9D5"></script>--%>
<%--  <script>--%>
<%--      window.dataLayer = window.dataLayer || [];--%>

<%--      function gtag() {--%>
<%--          dataLayer.push(arguments);--%>
<%--      }--%>

<%--      gtag('js', new Date());--%>

<%--      gtag('config', 'G-31DLD5Y9D5');--%>
<%--  </script>--%>
<%--  &lt;%&ndash; ads &ndash;%&gt;--%>
<%--  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-1526378979750451"--%>
<%--          crossorigin="anonymous"></script>--%>
</head>

<body>

<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="body"/>
<tiles:insertAttribute name="footer"/>

<!-- Core JavaScript
    ================================================== -->
<script src="<c:url value='/assets/user/js/jquery.min.js'/>"></script>
<script src="<c:url value='/assets/user/js/tether.min.js'/>"></script>
<script src="<c:url value='/assets/user/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/assets/user/js/custom.js'/>"></script>
</body>
</html>
