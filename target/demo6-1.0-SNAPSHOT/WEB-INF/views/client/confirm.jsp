<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.demo6.shop.utils.SecurityUtils" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../resource/client/images/favicon.png">
    <link href="../resource/client/css/bootstrap.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
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
                    <div class="row">
                        <c:if test="${limit != null}">
                            <div class="alert alert-danger" style="size: 15px;" role="alert">
                                    ${limit}
                            </div>
                        </c:if>
                        <c:if test="${noteffectivedate != null}">
                            <div class="alert alert-danger" style="size: 15px;" role="alert">
                                    ${noteffectivedate}
                            </div>
                        </c:if>
                        <c:if test="${notvoucher != null}">
                            <div class="alert alert-danger" style="size: 15px;" role="alert">
                                    ${notvoucher}
                            </div>
                        </c:if>
                        <c:if test="${used != null}">
                            <div class="alert alert-success" style="size: 15px;color: #761c19;" role="alert">
                                    ${used}
                            </div>
                        </c:if>
                        <div class="col-md-12">
                            <h3 class="title">Shopping Cart</h3>
                            <div class="clearfix"></div>
                            <table class="shop-table">
                                <thead>
                                <tr>
                                    <th style="color: #761c19;font-weight: bold;">Image</th>
                                    <th style="color: #761c19;font-weight: bold;">Details</th>
                                    <th style="color: #761c19;font-weight: bold;">Price</th>
                                    <th style="color: #761c19;font-weight: bold;">Quantity</th>
                                    <th style="color: #761c19;font-weight: bold;">Total Amount </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${cart}">
                                    <tr>
                                        <td>
                                            <a href="<c:url value="/client/product-details?productId=${item.value.productDTO.productId}"/>"><img
                                                    src="https://bucketslhs.s3.amazonaws.com/${item.value.productDTO.image}"
                                                    alt=""></a></td>
                                        <td>
                                            <div class="shop-details">
                                                <div class="productname">${item.value.productDTO.productName}</div>
                                                   <p>
                                                       <img alt="" src="../resource/client/images/star.png">
                                                   </p>
                                                   <p>
                                                       Sale : <strong class="pcode">-${item.value.productDTO.saleDTO.salePercent}%</strong>
                                                   </p>
                                                   <p>
                                                       Category : <strong class="pcode">${item.value.productDTO.categoryDTO.categoryName}</strong>
                                                   </p>
                                            </div>
                                        </td>
                                        <td>
                                            <h5 style="color: #41B314; font-weight: bold;">
                                                $${Math.round(item.value.productDTO.price-item.value.productDTO.saleDTO.salePercent*item.value.productDTO.price/100)}.00</h5>
                                            <c:if
                                                    test="${item.value.productDTO.saleDTO.salePercent > 0}">
                                                <p style="font-size: 16px; padding-top: 7px; text-decoration: line-through;">
                                                    $${item.value.productDTO.price}.00</p>
                                            </c:if>
                                        </td>
                                        <td>
                                            <p style="color: #ea4c89;font-weight: bold;">  ${item.value.quantity}</p>

                                                    <input type="hidden" value="${item.value.productDTO.productId}" name="productId"/>
                                        </td>
                                        <td>
                                            <h5>
                                                <strong class="red" style="font-weight: bold;">
                                                    $${Math.round(item.value.totalPriceSale)}.00 </strong>
                                            </h5>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>


                                <tfoot>
                                <tr>
                                    <td colspan="6"><a href="<c:url value="/client/home"/>">
                                        <button
                                                class="pull-right">Continue Shopping
                                        </button>
                                    </a>
                                </tr>
                                </tfoot>
                            </table>
                            <div class="clearfix"></div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6">
                                    <div class="shippingbox">
                                        <h5>Estimate Shipping And Tax</h5>
                                        <label> FullName *:  </label> <input type="text" name="" value="${user.fullname}" style="color: red;" readonly="readonly"/>
                                        <label> Address *:  </label> <input type="text" name="" value="${user.address}" style="color: red;" readonly="readonly"/>
                                        <label> Phone number *:  </label> <input type="text" name="" value="${user.phone}" style="color: red;" readonly="readonly"/>
                                        <label> Email *:  </label> <input type="text" name="" value="${user.email}" style="color: red;" readonly="readonly"/>
                                        <label>Change of shipping address * : </label>
                                        <c:if test="${user != null}">
                                            <a href="<c:url value="/client/profile"/> ">
                                                <button>Go To Profile</button>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-md-4 col-sm-6">
                                    <div class="shippingbox">
                                        <h5>Discount Codes</h5>

                                        <form action="<c:url value="/client/voucherCode"/> " method="post" >
                                            <label> Enter your coupon code if you have one </label>
                                            <label style="color: #ee3423;"> ${voucherdescription} </label>
                                            <input type="text" name="vouchercode" required="required" value="${voucherCodeClient.code}" style="color: red;" />
                                            <div></div>
                                            <button style="float: left;">Get Coupon </button>
                                        </form>
                                        <c:if test="${not empty voucherCodeClient}">
                                        <form style="float: right;" action="<c:url value="/client/vocherCodeRemove"/> " method="post" >
                                            <button>Cancel </button>
                                        </form></c:if>
                                    </div>
                                </div>
                                <div class="col-md-4 col-sm-6">
                                    <div class="shippingbox">
                                        <div class="subtotal">
                                            <h5>Sub Total:</h5>
                                            <span>$${Math.round(TotalPriceCartSale)}.00</span>
                                        </div>
                                        <div class="subtotal">
                                            <h5>Shipping Fee:</h5>
                                            <span> $5.00 </span>
                                        </div>
                                        <div class="grandtotal">
                                            <h5 style="color: #151515; font-weight: bold;" >
                                                Temporary Price</h5>
                                            <span>$${Math.round(TotalPriceCartSale+5)}.00</span>
                                        </div>
                                        <c:if test="${voucherPrice != 0}">
                                            <div class="subtotal">
                                                <h5 style="color: #00aa00;" >
                                                    - </h5>
                                                <span>$${Math.round(voucherPrice)}.00</span>
                                            </div>
                                        </c:if>
                                        <div class="grandtotal">
                                            <h5 style="color: #761c19; font-weight: bold;" > TOTAL</h5>
                                            <span>$${Math.round(finalTotal)}.00</span>
                                        </div>

                                        <sec:authorize access="isAuthenticated()">

                                            <c:if
                                                    test="${user.address == null && user.phone == null }">
                                                <p style="color: #ff1e2c;font-weight: bold;">Please update your information</p>
                                                <a href="<c:url value="/client/profile"/> " type="button" class="btn btn-light">Profile</a>

                                            </c:if></sec:authorize>
                                        <c:if
                                                test="${user.address != null && user.phone != null }">
                                            <form action="<c:url value="/client/checkout"/>" method="get">
                                                <button type="submit" style="background: #d5d5d5; color: #1a8cff;font-weight: bold; float: right;">Paypal</button>
                                                <input type="hidden" name="paypal" value="paypal">
                                            </form>
                                            <form action="<c:url value="/client/checkout"/>" method="get">
                                                <button style="color: #593a93;font-weight: bold;" type="submit">Payment on delivery</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${user == null}">
                                                <a href="<c:url value="/login"/> ">
                                                       <button style="width:80px;height:34px;ackground: #d5d5d5; color: #1a8cff;font-weight: bold;float: right;">Paypal
                                                       </button>
                                                </a>
                                            <a href="<c:url value="/login"/>">
                                                <button>Process To Checkout</button>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
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