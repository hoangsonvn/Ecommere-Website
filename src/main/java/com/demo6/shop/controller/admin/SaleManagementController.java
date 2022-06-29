package com.demo6.shop.controller.admin;

import com.demo6.shop.common.ICommon;
import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ResourceBundle;

@Controller
public class SaleManagementController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private ICommon iCommon;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    @PreAuthorize("hasAuthority('SALE_READ')")
    @GetMapping("/admin/listsale")
    public String findAll(HttpServletRequest request) {
        iCommon.notificate(request);
        request.setAttribute("sales", saleService.findAll());
        return "/admin/sale/listsale";
    }
    @PreAuthorize("hasAuthority('SALE_CREATE')")
    @GetMapping("/admin/createsale")
    public String cSale(HttpServletRequest request) {
        String messsagedate = request.getParameter("expireddate");
        String message = request.getParameter("message");
        if (messsagedate != null) {
            request.setAttribute("expireddate", resourceBundle.getString(messsagedate));
        }
        if (message != null) {
            request.setAttribute("message", resourceBundle.getString(message));
        }
        return "/admin/sale/createsale";
    }

    @PreAuthorize("hasAuthority('SALE_CREATE')")
    @PostMapping("/admin/createsale")
    public String pSale(@RequestParam(value = "salePercent") int salePercent, @RequestParam(value = "shortDescription") String shortDescription,
                        @RequestParam(value = "title") String title, @RequestParam(name = "expirationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expirationDate) {
        if (saleService.findBySalePercent(salePercent)!= null) {
            return "redirect:/admin/createsale?message=duplicate";
        }
        if (new Date(new Date().getTime()).compareTo(expirationDate) > 0) {
            return "redirect:/admin/createsale?expireddate=dateExpiration";
        }
        SaleDTO saleDTO = SaleDTO.builder().salePercent(salePercent)
                .title(title).shortDescription(shortDescription)
                .expirationDate(expirationDate)
                .build();
        saleService.persist(saleDTO);
        return "redirect:/admin/listsale?messagecreate=saleCreate";
    }

    @PreAuthorize("hasAuthority('SALE_UPDATE')")
    @GetMapping("/admin/updatesale/{id}")
    public String uSale(HttpServletRequest request, @PathVariable(value = "id") String id) {
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("message", resourceBundle.getString(message));
        }
        String messsagedate = request.getParameter("expireddate");
        if (messsagedate != null) {
            request.setAttribute("expireddate", resourceBundle.getString(messsagedate));
        }
        request.setAttribute("sale", saleService.findOne(id));
        return "/admin/sale/updatesale";
    }

    @PreAuthorize("hasAuthority('SALE_UPDATE')")
    @PostMapping("/admin/updatesale")
    public String puSale(@RequestParam(value = "saleId") String saleId, @RequestParam(value = "salePercent") int salePercent, @RequestParam(value = "shortDescription") String shortDescription,
                         @RequestParam(value = "title") String title, @RequestParam(name = "expirationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expirationDate) {
        SaleDTO saleDTO = saleService.findOne(saleId);
        Integer salePercentDB = saleService.findBySalePercent(salePercent);
        if (saleDTO.getSalePercent() != salePercent && salePercent == salePercentDB) {
            return "redirect:/admin/updatesale/"+ saleId + "?message=duplicate";
        }
        if (new Date(new Date().getTime()).compareTo(expirationDate) > 0) {
            return "redirect:/admin/updatesale/" + saleId + "?expireddate=dateExpiration";
        }
        SaleDTO newSaleDTO = SaleDTO.builder().saleId(saleId)
                .salePercent(salePercent).title(title)
                .shortDescription(shortDescription)
                .expirationDate(expirationDate)
                .createDate(saleDTO.getCreateDate())
                .build();
        saleService.update(newSaleDTO);
        return "redirect:/admin/listsale?messageupdate=saleUpdate";
    }

    @PreAuthorize("hasAuthority('SALE_DELETE')")
    @PostMapping("/admin/deletesale")
    public String dSale(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids");
        if (ids == null) {
            return "redirect:/admin/listsale?tick=tick";
        }
        for (String id : ids) {
            saleService.delete(id);
        }
        return "redirect:/admin/listsale?messagedelete=saleDelete";

    }
}
