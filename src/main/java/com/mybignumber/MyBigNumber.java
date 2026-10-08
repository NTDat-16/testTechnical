package com.mybignumber;

import java.util.logging.Logger;

public class MyBigNumber {

    // Khởi tạo logger để ghi lại lịch sử các bước theo yêu cầu đề bài
    private static final Logger logger = Logger.getLogger(MyBigNumber.class.getName());

    /**
     * Hàm cộng 2 số lớn dưới dạng chuỗi kí tự theo đúng yêu cầu đề bài.
     * Tự động ghi lại các bước tính toán vào log.
     *
     * @param stn1 Chuỗi số thứ nhất (ví dụ: "1234")
     * @param stn2 Chuỗi số thứ hai (ví dụ: "897")
     * @return Chuỗi kết quả phép cộng (ví dụ: "2131")
     */
    public String sum(String stn1, String stn2) {
        return sum(stn1, stn2, null);
    }

    /**
     * Hàm cộng 2 số lớn có thêm listener để truyền các bước tính cho nhóm làm giao diện.
     *
     * @param stn1 Chuỗi số thứ nhất
     * @param stn2 Chuỗi số thứ hai
     * @param listener Đối tượng nhận các bước giải thích (nếu không cần thì truyền null)
     * @return Chuỗi kết quả phép cộng
     */
    public String sum(String stn1, String stn2, IStepListener listener) {
        // Kiểm tra cơ bản: không cho phép tham số là null
        if (stn1 == null || stn2 == null) {
            throw new IllegalArgumentException("Tham số truyền vào không được là null.");
        }

        // Cắt bỏ khoảng trắng thừa nếu có
        stn1 = stn1.trim();
        stn2 = stn2.trim();

        // Nếu chuỗi rỗng thì coi như là số 0
        if (stn1.isEmpty()) {
            stn1 = "0";
        }
        if (stn2.isEmpty()) {
            stn2 = "0";
        }

        // Kiểm tra xem chuỗi có chứa kí tự lạ không (chỉ chấp nhận từ 0 đến 9)
        if (!stn1.matches("\\d+") || !stn2.matches("\\d+")) {
            throw new IllegalArgumentException("Tham số chỉ được chứa các kí số từ 0 đến 9.");
        }

        // Dùng 2 con trỏ i và j để duyệt từ chữ số cuối cùng (bên phải) sang trái
        int i = stn1.length() - 1;
        int j = stn2.length() - 1;

        int carry = 0; // Biến nhớ khi cộng
        int step = 1;  // Đếm thứ tự bước thực hiện
        StringBuilder result = new StringBuilder(); // Lưu kết quả phép cộng

        // Vòng lặp chạy khi vẫn còn chữ số ở ít nhất một trong hai chuỗi
        while (i >= 0 || j >= 0) {
            int digit1 = 0;
            int digit2 = 0;
            boolean hasDigit1 = false;
            boolean hasDigit2 = false;

            // Lấy chữ số của stn1 nếu còn
            if (i >= 0) {
                digit1 = stn1.charAt(i) - '0';
                hasDigit1 = true;
                i--;
            }

            // Lấy chữ số của stn2 nếu còn
            if (j >= 0) {
                digit2 = stn2.charAt(j) - '0';
                hasDigit2 = true;
                j--;
            }

            int currentSum;
            int newCarry;
            int unitDigit;
            String message;

            // Trường hợp 1: Cả hai số đều còn chữ số ở vị trí này
            if (hasDigit1 && hasDigit2) {
                int sumDigits = digit1 + digit2;
                currentSum = sumDigits + carry;
                unitDigit = currentSum % 10;
                newCarry = currentSum / 10;

                // Ghép chữ số vừa tính được vào đầu chuỗi kết quả
                result.insert(0, unitDigit);

                // Diễn giải từng bước theo đúng văn phong học sinh tiểu học cộng
                if (carry > 0) {
                    message = "Bước " + step + ": Lấy " + digit1 + " cộng với " + digit2 + " được " + sumDigits
                            + ". Cộng tiếp với nhớ " + carry + " được " + currentSum
                            + ". Lưu " + unitDigit + " vào kết quả được kết quả mới là \"" + result + "\""
                            + ". Ghi nhớ " + newCarry + ".";
                } else {
                    message = "Bước " + step + ": Lấy " + digit1 + " cộng với " + digit2 + " được " + sumDigits
                            + ". Lưu " + unitDigit + " vào kết quả"
                            + (result.length() > 1 ? " được kết quả mới là \"" + result + "\"" : "")
                            + (newCarry > 0 ? " và nhớ " + newCarry + "." : ". Ghi nhớ 0.");
                }
            } else {
                // Trường hợp 2: Một trong hai số đã duyệt hết chữ số trước
                int singleDigit = hasDigit1 ? digit1 : digit2;
                currentSum = singleDigit + carry;
                unitDigit = currentSum % 10;
                newCarry = currentSum / 10;

                result.insert(0, unitDigit);

                if (carry > 0) {
                    message = "Bước " + step + ": Lấy " + singleDigit + " cộng tiếp với nhớ " + carry + " được " + currentSum
                            + ". Lưu " + unitDigit + " vào kết quả được kết quả mới là \"" + result + "\""
                            + ". Ghi nhớ " + newCarry + ".";
                } else {
                    message = "Bước " + step + ": Lấy " + singleDigit + " lưu vào kết quả"
                            + " được kết quả mới là \"" + result + "\""
                            + ". Ghi nhớ 0.";
                }
            }

            // Ghi log và gọi callback gửi ra ngoài nếu có listener
            logAndNotify(message, listener);

            // Cập nhật số nhớ cho bước tiếp theo
            carry = newCarry;
            step++;
        }

        // Sau khi đã duyệt hết 2 chuỗi, nếu vẫn còn nhớ thì ghi thêm vào đầu
        if (carry > 0) {
            result.insert(0, carry);
            String message = "Bước " + step + ": Còn nhớ " + carry + " nên ghi " + carry
                    + " vào đầu kết quả được kết quả mới là \"" + result + "\".";
            logAndNotify(message, listener);
        }

        String finalResult = result.toString();
        logger.info("Kết quả của phép tính: " + stn1 + " + " + stn2 + " = " + finalResult);

        return finalResult;
    }

    // Hàm tiện ích để vừa ghi log vừa gửi cho giao diện
    private void logAndNotify(String message, IStepListener listener) {
        logger.info(message);
        if (listener != null) {
            listener.onStep(message);
        }
    }

    // Hàm main để chạy thử nghiệm nhanh
    public static void main(String[] args) {
        MyBigNumber app = new MyBigNumber();

        System.out.println("=== Chạy thử phép tính trong đề bài: sum(\"1234\", \"897\") ===");
        String result = app.sum("1234", "897");
        System.out.println("Kết quả cuối cùng: " + result);

        System.out.println("\n=== Thử nghiệm truyền các bước sang giao diện (Console/UI) ===");
        app.sum("999", "1", stepMessage -> {
            System.out.println("[Giao diện nhận được] -> " + stepMessage);
        });
    }
}
