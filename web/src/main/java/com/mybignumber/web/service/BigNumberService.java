package com.mybignumber.web.service;

import com.mybignumber.MyBigNumber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý tính toán cộng 2 số lớn,
 * tái sử dụng lại lớp lõi MyBigNumber từ module core (Task 1).
 */
@Service
public class BigNumberService {

    // Khởi tạo đối tượng MyBigNumber từ module core
    private final MyBigNumber myBigNumber = new MyBigNumber();

    /**
     * DTO chứa kết quả và danh sách tiến trình các bước thực hiện
     */
    public static class CalculationResult {
        private final String stn1;
        private final String stn2;
        private final String result;
        private final List<String> steps;

        public CalculationResult(String stn1, String stn2, String result, List<String> steps) {
            this.stn1 = stn1;
            this.stn2 = stn2;
            this.result = result;
            this.steps = steps;
        }

        public String getStn1() {
            return stn1;
        }

        public String getStn2() {
            return stn2;
        }

        public String getResult() {
            return result;
        }

        public List<String> getSteps() {
            return steps;
        }
    }

    /**
     * Thực hiện phép cộng và thu thập toàn bộ các bước diễn giải
     *
     * @param stn1 số thứ nhất
     * @param stn2 số thứ hai
     * @return đối tượng chứa kết quả và các bước tiến trình
     */
    public CalculationResult calculate(String stn1, String stn2) {
        List<String> steps = new ArrayList<>();

        // Sử dụng callback listener đã thiết kế từ Task 1 để thu thập các bước
        String sum = myBigNumber.sum(stn1, stn2, steps::add);

        return new CalculationResult(stn1, stn2, sum, steps);
    }
}
