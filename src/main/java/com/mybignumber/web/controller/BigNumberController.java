package com.mybignumber.web.controller;

import com.mybignumber.web.service.BigNumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller điều hướng giao diện Web và xử lý yêu cầu tính toán cộng 2 số lớn
 */
@Controller
public class BigNumberController {

    private final BigNumberService bigNumberService;

    public BigNumberController(BigNumberService bigNumberService) {
        this.bigNumberService = bigNumberService;
    }

    /**
     * Hiển thị trang chủ ứng dụng Web
     */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    /**
     * Xử lý khi người dùng nhấn nút 'Cộng hai số' trên Form Thymeleaf
     */
    @PostMapping("/calculate")
    public String calculate(@RequestParam(value = "stn1", required = false) String stn1,
                            @RequestParam(value = "stn2", required = false) String stn2,
                            Model model) {
        model.addAttribute("stn1", stn1);
        model.addAttribute("stn2", stn2);

        try {
            BigNumberService.CalculationResult calculationResult = bigNumberService.calculate(stn1, stn2);
            model.addAttribute("result", calculationResult.getResult());
            model.addAttribute("steps", calculationResult.getSteps());
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi không mong muốn: " + e.getMessage());
        }

        return "index";
    }

    /**
     * API REST hỗ trợ gọi bằng AJAX / Postman (trả về dữ liệu JSON)
     */
    @PostMapping("/api/calculate")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> calculateApi(
            @RequestParam(value = "stn1", required = false) String stn1,
            @RequestParam(value = "stn2", required = false) String stn2) {
        Map<String, Object> response = new HashMap<>();
        try {
            BigNumberService.CalculationResult calculationResult = bigNumberService.calculate(stn1, stn2);
            response.put("success", true);
            response.put("stn1", calculationResult.getStn1());
            response.put("stn2", calculationResult.getStn2());
            response.put("result", calculationResult.getResult());
            response.put("steps", calculationResult.getSteps());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
