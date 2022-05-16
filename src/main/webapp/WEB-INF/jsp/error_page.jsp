<jsp:useBean id="cause" scope="request" type="com.blogads.exception.BlogAdsException"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 25/02/2022
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${cause.message}</title>
  <meta name='robots' content='noindex'/>
  <!-- Google font -->
  <link href="https://fonts.googleapis.com/css?family=Poppins:400,700" rel="stylesheet">
  <!-- Custom stlylesheet -->
  <link type="text/css" rel="stylesheet" href="css/style.css"/>
  <style>
      * {
          -webkit-box-sizing: border-box;
          box-sizing: border-box;
      }

      body {
          padding: 0;
          margin: 0;
      }

      #notfound {
          position: relative;
          height: 100vh;
      }

      #notfound .notfound {
          position: absolute;
          left: 50%;
          top: 50%;
          -webkit-transform: translate(-50%, -50%);
          -ms-transform: translate(-50%, -50%);
          transform: translate(-50%, -50%);
      }

      .notfound {
          max-width: 520px;
          width: 100%;
          line-height: 1.4;
      }

      .notfound .notfound-404 {
          position: absolute;
          left: 0;
          top: 0;
          height: 150px;
          width: 200px;
          z-index: -1;
      }

      .notfound .notfound-404 h1 {
          font-family: 'Poppins', sans-serif;
          font-size: 238px;
          font-weight: 700;
          margin: 0px;
          color: #e3e3e3;
          text-transform: uppercase;
          letter-spacing: 7px;
          position: absolute;
          left: 50%;
          top: 50%;
          -webkit-transform: translate(-50%, -50%);
          -ms-transform: translate(-50%, -50%);
          transform: translate(-50%, -50%);
      }

      .notfound h2 {
          font-family: 'Poppins', sans-serif;
          font-size: 28px;
          font-weight: 400;
          text-transform: uppercase;
          color: #222;
          margin-top: 12px;
          margin-bottom: 65px;
      }

      .notfound .notfound-search {
          position: relative;
          padding-right: 123px;
          width: 100%;
          margin: 30px 0px 20px;
      }

      .notfound .notfound-search input {
          font-family: 'Poppins', sans-serif;
          width: 100%;
          height: 40px;
          padding: 3px 15px;
          color: #222;
          font-size: 18px;
          font-weight: 400;
          background: #fff;
          border: 2px solid rgba(21, 23, 35, 0.2);
          -webkit-transition: 0.2s all;
          transition: 0.2s all;
      }

      .notfound .notfound-search input:focus {
          border-color: #57a3f8;
      }

      .notfound .notfound-search button {
          font-family: 'Poppins', sans-serif;
          position: absolute;
          right: 0px;
          top: 0px;
          width: 120px;
          height: 40px;
          text-align: center;
          border: none;
          background: #57a3f8;
          cursor: pointer;
          padding: 0;
          color: #fff;
          font-weight: 700;
          font-size: 18px;
      }

      .notfound a {
          font-family: 'Poppins', sans-serif;
          display: inline-block;
          font-weight: 700;
          border-radius: 15px;
          text-decoration: none;
          color: #57a3f8;
      }

      .notfound a:hover {
          color: #57a3f8;
      }

      .notfound a > .arrow {
          position: relative;
          top: -2px;
          border: solid #57a3f8;
          border-width: 0 3px 3px 0;
          display: inline-block;
          padding: 3px;
          -webkit-transform: rotate(135deg);
          -ms-transform: rotate(135deg);
          transform: rotate(135deg);
      }

      @media only screen and (max-width: 767px) {
          .notfound .notfound-404 {
              height: 110px;
              line-height: 110px;
          }

          .notfound .notfound-404 h1 {
              font-size: 170px;
          }

          .notfound h2 {
              font-size: 24px;
          }

          .notfound .notfound-search {
              margin: 10px 0px 20px;
          }
      }

      @media only screen and (max-width: 480px) {
          .notfound .notfound-404 {
              left: 40px;
          }

          .notfound .notfound-404 h1 {
              font-size: 120px;
          }

          .notfound h2 {
              font-size: 18px;
          }
      }

  </style>
</head>

<body>
<div id="notfound">
  <div class="notfound">
    <div class="notfound-404">
      <h1>${cause.status}</h1>
    </div>
    <h2>${cause.message}</h2>
    <a href="<c:url value='/' />"><span class="arrow"></span>Trang chủ</a>
  </div>
</div>
</body>
</html>
