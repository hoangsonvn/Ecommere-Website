<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../resource/client/images/favicon.png">
    <link href="../resource/client/css/bootstrap.css" rel="stylesheet">
    <link
            href='http://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,700,500italic,100italic,100'
            rel='stylesheet' type='text/css'>
    <link href="../resource/client/css/font-awesome.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="../resource/client/css/flexslider.css"
          type="text/css" media="screen"/>
    <link href="../resource/client/css/sequence-looptheme.css"
          rel="stylesheet" media="all"/>
    <link href="../resource/client/css/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script><![endif]-->
</head>
<body id="home">
<div class="wrapper">
    <jsp:include page="common/header.jsp"/>
    <div class="container_fullwidth">
        <div class="container">
            <div class="clearfix"></div>
            <div class="container_fullwidth">
                <div class="container shopping-cart">
                    <c:if test="${reviewsuccess != null}">
                        <div style="color: #770088;" class="alert alert-success">
                                ${reviewsuccess}
                        </div>
                    </c:if>
                    <div class="row">
                        <div class="col-md-12">
                            <div style="display: flex; justify-content: space-between;">
                                <h4 class="title" style="color: #761c19;font-weight: bold;">Order Details</h4>
                            </div>
                            <div class="clearfix"></div>
                            <table class="shop-table">
                                <thead>
                                <tr>
                                    <th style="color: #4285F4;font-weight: bold;">Image</th>
                                    <th style="color: #4285F4;font-weight: bold;">Details</th>
                                    <th style="color: #4285F4;font-weight: bold;">Price</th>
                                    <th style="color: #4285F4;font-weight: bold;">Quantity</th>
                                    <th style="color: #4285F4;font-weight: bold;">Total Amount before user Voucher</th>
                                    <th style="color: #4285F4;font-weight: bold;">Vote</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${items}" var="item">
                                    <tr>
                                        <td><a
                                                href="<c:url value="/client/product-details?productId=${item.productDTO.productId}"/>"><img
                                                src="https://bucketslhs.s3.amazonaws.com/${item.productDTO.image}"
                                                alt=""></a></td>
                                        <td>
                                            <div class="shop-details">
                                                <div class="productname">${item.productDTO.productName}</div>
                                                <p>
                                                    <img alt="" src="../resource/client/images/star.png">
                                                </p>

                                                <p>${product.description}</p>
                                                <p>
                                                    Sale : <strong class="pcode">-${item.productDTO.saleDTO.salePercent}%</strong>
                                                </p>
                                                <p>
                                                    Category : <strong
                                                        class="pcode">${item.productDTO.categoryDTO.categoryName}</strong>
                                                </p>
                                            </div>
                                        </td>
                                        <td>
                                            <h5 style="color: #41B314; font-weight: bold;">
                                                $${Math.round(item.unitPrice/item.quantity)}.00</h5>
                                            <c:if test="${item.productDTO.saleDTO.salePercent > 0}">
                                                <p
                                                        style="font-size: 16px; padding-top: 7px; text-decoration: line-through;">
                                                    $${Math.round(item.unitPrice/item.quantity)}.00</p>
                                            </c:if>
                                        </td>
                                        <td><select name="quantity">
                                            <option value="${item.quantity}">${item.quantity}</option>
                                            <input type="hidden" value="${item.productDTO.productId}"
                                                   name="productId"/>
                                        </select></td>
                                        <td>
                                            <h5>
                                                <strong class="red"> $${Math.round(item.unitPrice)}.00 </strong>
                                            </h5>
                                        </td>
                                        <td>
                                            <form action="<c:url value="/client/order-details"/>" method="post">
                                                <select name="vote" onchange="this.form.submit()">
                                                    <c:forEach begin="0" end="5" var="i">
                                                        <option
                                                                <c:if test="${i == item.vote}">
                                                                    selected="selected"
                                                                </c:if> value="${i}"> ${i}
                                                            Star</option>
                                                    </c:forEach>
                                                    <input type="hidden" name="itemId" value="${item.itemId}">
                                                </select>
                                                </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="6"><a href="<c:url value="/client/my-order"/>">
                                        <button
                                                class="pull-left">Back
                                        </button>
                                    </a>
                                        <p class="pull-right" style="display: flex; border: none;">
                                            <i class="material-icons"
                                               style="margin-top: -5px; color: #00ACC1; margin-right: 1px;">&#xe0c8;</i>
                                            <i class="title"
                                               style="font-size: 17px; color: #00ACC1; font-weight: bold;">
                                                ${user.fullname} *
                                                ${user.address} * ${user.phone}</i>
                                        </p></td>
                                </tr>
                                </tfoot>
                            </table>
                            <div class="subtotal" style="margin-top: 5px;">
                                <h5>Shipping Fee:</h5>
                                <span> $5.00 </span>
                            </div>
                            <div class="grandtotal" style="margin-top: 5px;">
                                <h5 style="color: #761c19; font-weight: bold;"> TOTAL</h5>
                                <span>$${Math.round(grandTotal)}.00</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="common/footer.jsp"/>
</div>
<!-- Bootstrap core JavaScript==================================================-->
<script type="text/javascript"
        src="../resource/client/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
        src="../resource/client/js/jquery.easing.1.3.js"></script>
<script type="text/javascript"
        src="../resource/client/js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="../resource/client/js/jquery.sequence-min.js"></script>
<script type="text/javascript"
        src="../resource/client/js/jquery.carouFredSel-6.2.1-packed.js"></script>
<script defer src="../resource/client/js/jquery.flexslider.js"></script>
<script type="text/javascript"
        src="../resource/client/js/script.min.js"></script>
</body>
</html>