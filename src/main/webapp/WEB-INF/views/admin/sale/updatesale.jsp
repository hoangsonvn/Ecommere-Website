<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet"
          href="<c:url value="/resource/admin/assets/vendor/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet"
          href="<c:url value="/resource/admin/assets/vendor/font-awesome/css/font-awesome.min.css"/>">
    <link rel="stylesheet"
          href="<c:url value="/resource/admin/assets/vendor/linearicons/style.css"/>">
    <link rel="stylesheet"
          href="<c:url value="resource/admin/assets/vendor/chartist/css/chartist-custom.css"/>">
    <!-- MAIN CSS -->
    <link rel="stylesheet"
          href="<c:url value="/resource/admin/assets/css/main.css"/>">
    <link rel="stylesheet"
          href="<c:url value="/resource/admin/assets/css/demo.css"/>">
    <!-- GOOGLE FONTS -->
    <link
            href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
            rel="stylesheet">

    <!-- ICONS -->
    <link rel="apple-touch-icon" sizes="76x76"
          href="<c:url value="/resource/admin/assets/img/apple-icon.png"/>">
    <link rel="icon" type="image/png" sizes="96x96"
          href="<c:url value="/resource/admin/assets/img/favicon.png"/>">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">

    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resource/css/style.css"/>">
    <script src="<c:url value="/resource/ckeditor/ckeditor.js"/>"></script>

</head>
<body>
<div id="wrapper" style="max-width: 1250px; margin: auto;">
    <%@include file="../common/header.jsp" %>
    <jsp:include page="../common/category.jsp"/>
    <!-- MAIN -->
    <!-- MAIN CONTENT -->
    <div class="main">
        <div class="main-content">
            <div class="container-fluid">
                <!-- OVERVIEW -->
                <c:if test="${message != null}">
                    <div class="alert alert-danger">
                            ${message}
                    </div></c:if>
                <c:if test="${expireddate != null}">
                    <div class="alert alert-danger">
                            ${expireddate}
                    </div></c:if>
                <div class="panel panel-headline">
                    <div class="panel-heading"
                         style="display: flex; justify-content: space-between;">
                        <h3 class="panel-title">UPDATE </h3>
                        <a class="btn btn-warning" href="<c:url value="/admin/listsale"/>"
                           style="background-color: #D9534F; padding: 2px 10px; text-decoration: none; border: none; margin-right: 10px; height: 25px;">Back</a>
                    </div>
                    <form action="<c:url value="/admin/updatesale"/>" method="post">
                        <div class="row"
                             style="display: flex; justify-content: space-between;">
                            <table style="margin: auto; margin-left: 60px;" class="col-md-6">
                                <td><input type="hidden" name="saleId" value="${sale.saleId}"/></td>
                                <tr>
                                    <th>Sale Percent:</th>
                                    <td><input type="number" min="0" max="100" class="form-control"
                                               required="required" style="height: 30px;"
                                               placeholder="Enter sale percent..." name="salePercent" value="${sale.salePercent}"/></td>
                                </tr>
                                <tr>
                                    <th>title :</th>
                                    <td><input type="text" class="form-control"
                                               required="required" style="height: 30px;"
                                               placeholder="Enter title ..." name="title" value="${sale.title}"/></td>
                                </tr>
                                <tr>
                                    <th>shortDescription :</th>
                                    <td><input type="text" class="form-control"
                                               required="required" style="height: 30px;"
                                               placeholder="Enter short description ..." name="shortDescription" value="${sale.shortDescription}"/></td>
                                </tr>
                                <tr>
                                    <th>Expiration Date</th>
                                    <td><input required="required" type="date" id="expirationDate" name="expirationDate" value="${item.expirationDate}" ></td>
                                </tr>

                                <tr>
                                    <th></th>
                                    <td>
                                        <button type="submit" class="btn btn-primary"
                                                style="font-weight: bold;">UPDATE
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <th></th>
                                    <td></td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>

            </div>
            <div id="headline-chart" class="ct-chart"></div>
        </div>

        <!-- END OVERVIEW -->
    </div>
    <!-- END MAIN CONTENT -->
</div>

</body>

</html>