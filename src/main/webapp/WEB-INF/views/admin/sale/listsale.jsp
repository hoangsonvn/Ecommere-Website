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
          href="<c:url value='/resource/admin/assets/vendor/bootstrap/css/bootstrap.min.css'/>">
    <link rel="stylesheet"
          href="<c:url value='/resource/admin/assets/vendor/font-awesome/css/font-awesome.min.css'/>">
    <link rel="stylesheet"
          href="<c:url value='/resource/admin/assets/vendor/linearicons/style.css'/>">
    <link rel="stylesheet"
          href="<c:url value='/resource/admin/assets/vendor/chartist/css/chartist-custom.css'/>">
    <!-- MAIN CSS -->
    <link rel="stylesheet"
          href="<c:url value='/resource/admin/assets/css/main.css'/>">
    <link rel="stylesheet"
          href="<c:url value='/resource/admin/assets/css/demo.css'/>">
    <!-- GOOGLE FONTS -->
    <link
            href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
            rel="stylesheet">

    <!-- ICONS -->
    <link rel="apple-touch-icon" sizes="76x76"
          href="<c:url value='../resource/admin/assets/img/apple-icon.png'/>">
    <link rel="icon" type="image/png" sizes="96x96"
          href="<c:url value='../resource/admin/assets/img/favicon.png'/>">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<body>
<!-- WRAPPER -->
<div id="wrapper">
    <%@include file="../common/header.jsp" %>
    <jsp:include page="../common/category.jsp"/>
    <!-- MAIN -->
    <!-- MAIN CONTENT -->
    <form action="<c:url value="/admin/deletesale"/>" method="post">
        <div class="main">
            <div class="main-content">
                <div class="container-fluid">
                    <c:if test="${messagecreate != null}">
                        <div class="alert alert-success">
                                ${messagecreate}
                        </div>
                    </c:if>
                    <c:if test="${messagedelete != null}">
                        <div class="alert alert-success">
                                ${messagedelete}
                        </div>
                    </c:if>
                    <c:if test="${messageupdate != null}">
                        <div class="alert alert-success">
                                ${messageupdate}
                        </div>
                    </c:if>
                    <c:if test="${tick != null }">
                        <div class="alert alert-warning">
                                ${tick}
                        </div>
                    </c:if>
                    <!-- OVERVIEW -->
                    <div class="panel panel-headline">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="panel">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">SALE LIST</h3>
                                            <div class="right">
                                                <button type="submit">
															<span class="label label-danger"
                                                                  style="font-size: 15px; margin-right: 15px;">Delete</span>
                                                </button>
                                                <a href="<c:url value="/admin/createsale"/>"><span
                                                        class="label label-success" style="font-size: 15px;">Create
																sale</span></a>
                                                <a href="<c:url value="/admin/listvoucher"/>">
															<span
                                                                    class="label label-success"
                                                                    style=" margin-left:10px;color:mintcream;background:#162221;font-weight:bold;font-size: 18px;">Voucher
														   </span>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="panel-body no-padding">
                                            <table class="table" style="margin: auto;">
                                                <thead>
                                                <tr>
                                                    <th><input type="checkbox" name="all" id="checkAll"
                                                               style="cursor: pointer;"/></th>
                                                    <th>title</th>
                                                    <th>salePercent</th>
                                                    <th>shortDescription</th>
                                                    <th>createDate</th>
                                                    <th>expirationDate</th>
                                                    <th>Edit</th>

                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${sales}" varStatus="loop"
                                                           var="item">
                                                    <tr>
                                                        <td style="vertical-align: middle;"><input
                                                                class="checkbox" type="checkbox" name="ids"
                                                                value="${item.saleId}" id="${loop.count}"
                                                                style="cursor: pointer;"/></td>
                                                        <td style="vertical-align: middle;">${item.title}</td>
                                                        <td style="vertical-align: middle;">${item.salePercent}</td>
                                                        <td style="vertical-align: middle;">${item.shortDescription}</td>
                                                        <td style="vertical-align: middle;">${item.createDate}</td>
                                                        <td style="vertical-align: middle;">${item.expirationDate}</td>
                                                        <td style="vertical-align: middle;">
                                                            <a href="<c:url value="/admin/updatesale/${item.saleId}"/> ">
																			<span class="label label-warning"
                                                                                  style="font-size: 15px;">Update</span></a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <nav aria-label="Page navigation example"
                                         style="margin-top: -30px;">
                                        <ul class="pagination">

                                        </ul>
                                    </nav>
                                </div>
                                <div id="headline-chart" class="ct-chart"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END OVERVIEW -->
            </div>
            <!-- END MAIN CONTENT -->
        </div>
    </form>
    <!-- END MAIN -->
    <div class="clearfix"></div>
    <footer>
        <div class="container-fluid">
            <p class="copyright">
                &copy; 2017 <a href="https://www.themeineed.com" target="_blank">Theme
                I Need</a>. All Rights Reserved.
            </p>
        </div>
    </footer>
</div>
<!-- END WRAPPER -->

<script>
    let userSelection = document.getElementsByClassName('checkbox');
    let checkAll = document.getElementById('checkAll')


    checkAll.addEventListener('click', () => {
        if (checkAll.checked == true) {
            for (let i = 1; i <= userSelection.length; i++) {
                document.getElementById(i).checked = true
            }
        } else {
            for (let i = 1; i <= userSelection.length; i++) {
                document.getElementById(i).checked = false
            }
        }

    })
</script>
</body>
</body>
</html>