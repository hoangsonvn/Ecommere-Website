<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet"
          href="<c:url value='../resource/admin/assets/vendor/bootstrap/css/bootstrap.min.css'/>">
    <link rel="stylesheet"
          href="<c:url value='../resource/admin/assets/vendor/font-awesome/css/font-awesome.min.css'/>">
    <link rel="stylesheet"
          href="<c:url value='../resource/admin/assets/vendor/linearicons/style.css'/>">
    <link rel="stylesheet"
          href="<c:url value='../resource/admin/assets/vendor/chartist/css/chartist-custom.css'/>">
    <!-- MAIN CSS -->
    <link rel="stylesheet"
          href="<c:url value='../resource/admin/assets/css/main.css'/>">
    <link rel="stylesheet"
          href="<c:url value='../resource/admin/assets/css/demo.css'/>">
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

    <jsp:include page="../common/header.jsp" />

    <jsp:include page="../common/category.jsp" />

    <!-- MAIN -->
    <!-- MAIN CONTENT -->
    <div class="main">
        <div class="main-content">
            <div class="container-fluid">
                <c:if test="${tick != null }">
                    <div class="alert alert-warning">
                            ${tick}
                    </div></c:if>
                <!-- OVERVIEW -->
                <div class="panel panel-headline">
                    <div class="panel-body">
                        <form action="<c:url value="/admin/editpermission"/> " method="post">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="panel">
                                        <div class="panel-heading">
                                            <h3 style="color: #3c3c3c;" class="panel-title">ROLE PERMISSION</h3>
                                        </div>
                                        <div class="panel-body no-padding">
                                            <table class="table table-striped" style="margin: auto;">
                                                <thead>
                                                <tr><th><input type="checkbox" name="all" id="checkAll"
                                                               style="cursor: pointer;" /> All</th>
                                                    <th style=" font-weight: bolder;color: #c0a16b;">NO</th>
                                                    <th style=" font-weight: bolder;color: #c0a16b;">Key</th>
                                                    <th style=" font-weight: bolder;color: #c0a16b;">Name</th>
                                                    <th style=" font-weight: bolder;color: #c0a16b;">Short Description</th>

                                                </tr>

                                                </thead>
                                                <tbody>
                                                        <c:forEach items="${permissions}" var="permission" varStatus="loop">
                                                         <tr>
                                                            <td style="vertical-align: middle;"><input
                                                                    class="checkbox" type="checkbox"
                                                                    <c:forEach items="${userPermission}" var="item">
                                                                        <c:if test="${item.id == permission.id}">checked="checked"</c:if>
                                                                    </c:forEach>
                                                                    name="ids"
                                                                    value="${permission.id}" id="${loop.count}"
                                                                    style="cursor: pointer;" /></td>
                                                             <td>${permission.id}</td>
                                                             <td>${permission.permissionKey}</td>
                                                             <td>${permission.permissionName}</td>
                                                             <td>${permission.description}</td>
                                                         </tr>
                                                        </c:forEach>
                                                </tbody>
                                                <button style=" margin-right:20px;color: #FFFFFF;font-weight: bold;background: #3e3e3e" type="submit" class="btn btn-info">Submit</button>

                                            </table>

                                        </div>
                                    </div>
                                <input type="hidden" name="roleId" value="${roleId}">
                                </div>
                                <div id="headline-chart" class="ct-chart"></div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- END OVERVIEW -->
        </div>
        <!-- END MAIN CONTENT -->
    </div>
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

</html>