package com.demo6.shop.controller.admin;

import com.demo6.shop.common.ICommon;
import com.demo6.shop.entity.Voucher;
import com.demo6.shop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class VoucherController {
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private ICommon iCommon;
    @PreAuthorize("hasAuthority('VOUCHER_READ')")
    @GetMapping("/admin/listvoucher")
    public String listVoucher(HttpServletRequest request) {
        request.setAttribute("vouchers", voucherService.findAll());
        iCommon.notificate(request);
        return "admin/voucher/listvoucher";
    }

    @PreAuthorize("hasAuthority('VOUCHER_CREATE')")
    @GetMapping("/admin/createvoucher")
    public String GCreate(HttpServletRequest request) {
        iCommon.notificate(request);
        return "/admin/voucher/createvoucher";
    }

    @PreAuthorize("hasAuthority('VOUCHER_CREATE')")
    @PostMapping("/admin/createvoucher")
    public String pCreateVoucher(@RequestParam(value = "code") String code, @RequestParam(value = "voucherPercent") int voucherPercent, @RequestParam(value = "title") String title,
                                 @RequestParam(value = "shortDescription") String shortDescription, @RequestParam(name = "expirationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expirationDate, @RequestParam("maxVoucher") Long maxVoucher,
                                 @RequestParam(name = "effectiveDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date effectiveDate) {
        if (voucherService.findOneByCode(code) != null) {
            return "redirect:/admin/createvoucher?message=duplicateCodeVoucher";
        }
        if (new Date(new Date().getTime()).compareTo(expirationDate) > 0 || new Date(new Date().getTime()).compareTo(effectiveDate) >0) {
            return "redirect:/admin/createvoucher?expireddate=invalidDate";
        }
        if (expirationDate.compareTo(effectiveDate) < 0) {
            return "redirect:/admin/createvoucher?effectivedate=effectiveDate";
        }
        Voucher voucher = Voucher.builder().shortDescription(shortDescription).voucherPercent(voucherPercent)
                .createDate(new Date(new Date().getTime())).expirationDate(expirationDate).effectiveDate(effectiveDate).title(title).code(code).maxVoucher(maxVoucher).build();
        voucherService.persist(voucher);
        return "redirect:/admin/listvoucher?messagecreate=voucherCreate";

    }

    @PreAuthorize("hasAuthority('VOUCHER_UPDATE')")
    @GetMapping("/admin/updatevoucher/{voucherId}")
    public String gUpdateVoucher(HttpServletRequest request, @PathVariable(value = "voucherId") String voucherId) {
        iCommon.notificate(request);
        request.setAttribute("voucher", voucherService.findOneById(voucherId));
        return "admin/voucher/updatevoucher";
    }

    @PreAuthorize("hasAuthority('VOUCHER_UPDATE')")
    @PostMapping("/admin/updatevoucher")
    public String pUpdate(@RequestParam(value = "voucherId") String voucherId, @RequestParam(value = "code") String code, @RequestParam(value = "voucherPercent") int voucherPercent, @RequestParam("title") String title,
                          @RequestParam(value = "shortDescription") String shortDescription, @RequestParam(name = "expirationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expirationDate, @RequestParam("maxVoucher") Long maxVoucher,
                          @RequestParam(name = "effectiveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date effectiveDate) {
        if (!voucherService.findOneById(voucherId).getCode().equals(code) && voucherService.findOneByCode(code) != null) {
            return "redirect:/admin/updatevoucher/" + voucherId + "?message=duplicateCodeVoucher";
        }
        if (new Date(new Date().getTime()).compareTo(expirationDate) > 0 || new Date(new Date().getTime()).compareTo(effectiveDate) > 0) {
            return "redirect:/admin/updatevoucher/" + voucherId + "?expireddate=invalidDate";
        }
        if (expirationDate.compareTo(effectiveDate) < 1) {
            return "redirect:/admin/updatevoucher/" + voucherId + "?effectivedate=effectiveDate";
        }
        Voucher voucher = Voucher.builder().voucherId(voucherId).shortDescription(shortDescription).voucherPercent(voucherPercent)
                .expirationDate(expirationDate).effectiveDate(effectiveDate).title(title).code(code).maxVoucher(maxVoucher).build();
        voucherService.merge(voucher);
        return "redirect:/admin/listvoucher?messageupdate=voucherUpdate";
    }

    @PreAuthorize("hasAuthority('VOUCHER_DELETE')")
    @PostMapping("/admin/deletevoucher")
    public String dVoucher(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids");
        if (ids == null) {
            return "redirect:/admin/listvoucher?tick=tick";
        }
        for (String id : ids) {
            voucherService.delete(id);
        }
        return "redirect:/admin/listvoucher?messagedelete=voucherDelete";
    }


}


