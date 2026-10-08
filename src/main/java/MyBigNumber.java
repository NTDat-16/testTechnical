import java.util.logging.Logger;

/**
 * Lớp MyBigNumber cài đặt thuật toán cộng 2 số lớn được biểu diễn dưới dạng chuỗi,
 * mô phỏng chính xác thuật toán cộng từng chữ số từ phải sang trái như học sinh Tiểu học (lớp 3).
 *
 * <p>Phiên bản: 0.0.1</p>
 * <p>Tác giả: Nguyễn Tấn Đạt (NTDat-16)</p>
 */
public class MyBigNumber {

    private static final Logger logger = Logger.getLogger(MyBigNumber.class.getName());

    /**
     * Hàm cộng 2 số lớn dưới dạng chuỗi kí tự theo đúng yêu cầu đề bài.
     * Tự động ghi lại lịch sử từng bước thực hiện bằng Logger.
     *
     * @param stn1 Chuỗi số thứ nhất (ví dụ: "1234")
     * @param stn2 Chuỗi số thứ hai (ví dụ: "897")
     * @return Chuỗi biểu diễn kết quả phép cộng (ví dụ: "2131")
     */
    public String sum(String stn1, String stn2) {
        return sum(stn1, stn2, null);
    }

    /**
     * Hàm cộng 2 số lớn dưới dạng chuỗi có hỗ trợ callback listener.
     * Giúp bàn giao cho nhóm phát triển giao diện (GUI/Console/Web) dễ dàng
     * hiển thị trực quan các bước tính toán cho người dùng.
     *
     * @param stn1 Chuỗi số thứ nhất
     * @param stn2 Chuỗi số thứ hai
     * @param listener Đối tượng nhận thông báo diễn giải từng bước (có thể null nếu không cần)
     * @return Chuỗi biểu diễn kết quả phép cộng
     */
    public String sum(String stn1, String stn2, IStepListener listener) {
        // Kiểm tra dữ liệu đầu vào cơ bản (defensive programming)
        if (stn1 == null || stn2 == null) {
            throw new IllegalArgumentException("Tham số truyền vào không được là null.");
        }

        // Xử lý chuỗi rỗng: nếu rỗng xem như là "0"
        if (stn1.trim().isEmpty()) {
            stn1 = "0";
        }
        if (stn2.trim().isEmpty()) {
            stn2 = "0";
        }

        // Kiểm tra định dạng: chỉ chứa các ký số 0-9
        if (!stn1.matches("\\d+") || !stn2.matches("\\d+")) {
            throw new IllegalArgumentException("Tham số chỉ được chứa các kí số hợp lệ (0-9). stn1='" + stn1 + "', stn2='" + stn2 + "'");
        }

        int len1 = stn1.length();
        int len2 = stn2.length();
        int i1 = len1 - 1;
        int i2 = len2 - 1;

        int carry = 0;
        int step = 1;
        StringBuilder resultBuilder = new StringBuilder();

        // Duyệt đồng thời từ phải sang trái
        while (i1 >= 0 || i2 >= 0) {
            int digit1 = 0;
            int digit2 = 0;
            boolean hasDigit1 = false;
            boolean hasDigit2 = false;

            if (i1 >= 0) {
                digit1 = stn1.charAt(i1) - '0';
                hasDigit1 = true;
                i1--;
            }

            if (i2 >= 0) {
                digit2 = stn2.charAt(i2) - '0';
                hasDigit2 = true;
                i2--;
            }

            int currentSum;
            int newCarry;
            int unitDigit;
            String message;

            if (hasDigit1 && hasDigit2) {
                int sumDigits = digit1 + digit2;
                currentSum = sumDigits + carry;
                unitDigit = currentSum % 10;
                newCarry = currentSum / 10;
                resultBuilder.insert(0, unitDigit);

                if (carry > 0) {
                    message = "Bước " + step + ": Lấy " + digit1 + " cộng với " + digit2 + " được " + sumDigits
                            + ". Cộng tiếp với nhớ " + carry + " được " + currentSum
                            + ". Lưu " + unitDigit + " vào kết quả được kết quả mới là \"" + resultBuilder + "\""
                            + ". Ghi nhớ " + newCarry + ".";
                } else {
                    message = "Bước " + step + ": Lấy " + digit1 + " cộng với " + digit2 + " được " + sumDigits
                            + ". Lưu " + unitDigit + " vào kết quả"
                            + (resultBuilder.length() > 1 ? " được kết quả mới là \"" + resultBuilder + "\"" : "")
                            + (newCarry > 0 ? " và nhớ " + newCarry + "." : ". Ghi nhớ 0.");
                }
            } else {
                int singleDigit = hasDigit1 ? digit1 : digit2;
                currentSum = singleDigit + carry;
                unitDigit = currentSum % 10;
                newCarry = currentSum / 10;
                resultBuilder.insert(0, unitDigit);

                if (carry > 0) {
                    message = "Bước " + step + ": Lấy " + singleDigit + " cộng tiếp với nhớ " + carry + " được " + currentSum
                            + ". Lưu " + unitDigit + " vào kết quả được kết quả mới là \"" + resultBuilder + "\""
                            + ". Ghi nhớ " + newCarry + ".";
                } else {
                    message = "Bước " + step + ": Lấy " + singleDigit + " lưu vào kết quả"
                            + " được kết quả mới là \"" + resultBuilder + "\""
                            + ". Ghi nhớ 0.";
                }
            }

            // Ghi nhận log và thông báo tới listener
            logAndNotify(message, listener);

            carry = newCarry;
            step++;
        }

        // Nếu sau khi duyệt hết cả 2 số vẫn còn nhớ (carry > 0)
        if (carry > 0) {
            resultBuilder.insert(0, carry);
            String message = "Bước " + step + ": Còn nhớ " + carry + " nên ghi " + carry
                    + " vào đầu kết quả được kết quả mới là \"" + resultBuilder + "\".";
            logAndNotify(message, listener);
        }

        String finalResult = resultBuilder.toString();
        logger.info("Kết quả của phép tính: " + stn1 + " + " + stn2 + " = " + finalResult);

        return finalResult;
    }

    private void logAndNotify(String message, IStepListener listener) {
        logger.info(message);
        if (listener != null) {
            listener.onStep(message);
        }
    }

    /**
     * Phương thức main chạy thử nghiệm và kiểm tra với ví dụ sum("1234", "897")
     */
    public static void main(String[] args) {
        MyBigNumber myBigNumber = new MyBigNumber();

        System.out.println("=== CHẠY PHÉP CỘNG sum(\"1234\", \"897\") THEO ĐỀ BÀI ===");
        String result = myBigNumber.sum("1234", "897");
        System.out.println("Kết quả cuối cùng: " + result);

        System.out.println("\n=== DEMO GIAO DIỆN / CONSOLE NHẬN TỪNG BƯỚC QUA LISTENER ===");
        myBigNumber.sum("999", "1", stepMessage -> {
            System.out.println("[GUI / Console Display] -> " + stepMessage);
        });
    }
}
