import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Bộ kiểm thử tự động (Unit Test) cho lớp MyBigNumber sử dụng JUnit 5.
 * Kiểm thử toàn diện các trường hợp biên, số có độ dài khác nhau,
 * phép nhớ liên tiếp, số cực lớn (hơn 100 chữ số), và cơ chế callback cho giao diện.
 */
public class MyBigNumberTest {

    private MyBigNumber myBigNumber;

    @BeforeEach
    void setUp() {
        myBigNumber = new MyBigNumber();
    }

    @Test
    @DisplayName("TC01: Test ca ví dụ mẫu trong đề bài sum('1234', '897') = '2131'")
    void testExampleFromSpecification() {
        String result = myBigNumber.sum("1234", "897");
        assertEquals("2131", result, "Phép cộng 1234 + 897 phải bằng 2131");
    }

    @ParameterizedTest(name = "TC02: {0} + {1} = {2}")
    @CsvSource({
            "123, 456, 579",
            "111, 222, 333",
            "500, 500, 1000",
            "999, 999, 1998"
    })
    @DisplayName("TC02: Hai số có cùng độ dài")
    void testSameLengthNumbers(String stn1, String stn2, String expected) {
        assertEquals(expected, myBigNumber.sum(stn1, stn2));
    }

    @ParameterizedTest(name = "TC03: {0} + {1} = {2}")
    @CsvSource({
            "987654, 12, 987666",
            "12, 987654, 987666",
            "1, 9999, 10000",
            "9999, 1, 10000",
            "1000000, 5, 1000005",
            "5, 1000000, 1000005"
    })
    @DisplayName("TC03: Hai số có độ dài khác nhau (số trước dài hơn và số sau dài hơn)")
    void testDifferentLengthNumbers(String stn1, String stn2, String expected) {
        assertEquals(expected, myBigNumber.sum(stn1, stn2));
    }

    @Test
    @DisplayName("TC04: Nhớ liên tiếp qua nhiều hàng (Carry propagation)")
    void testCarryPropagation() {
        assertEquals("1000", myBigNumber.sum("999", "1"));
        assertEquals("10000000000", myBigNumber.sum("9999999999", "1"));
        assertEquals("100000000000000000000", myBigNumber.sum("99999999999999999999", "1"));
    }

    @ParameterizedTest(name = "TC05: {0} + {1} = {2}")
    @CsvSource({
            "0, 0, 0",
            "0, 123, 123",
            "456, 0, 456",
            "4, 7, 11",
            "9, 9, 18"
    })
    @DisplayName("TC05: Các số có 1 chữ số và số 0")
    void testSingleDigitsAndZero(String stn1, String stn2, String expected) {
        assertEquals(expected, myBigNumber.sum(stn1, stn2));
    }

    @Test
    @DisplayName("TC06: Cộng 2 số siêu lớn (vượt quá giới hạn long/BigInteger thông thường)")
    void testVeryLargeNumbers() {
        // Tạo chuỗi 100 số 9: "999...9"
        String stn1 = "9".repeat(100);
        String stn2 = "1";
        // Kết quả phải là 1 kèm theo 100 số 0
        String expected = "1" + "0".repeat(100);

        String result = myBigNumber.sum(stn1, stn2);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("TC07: Kiểm tra callback listener nhận đầy đủ các bước thực hiện")
    void testStepListenerReceivesAllSteps() {
        List<String> recordedSteps = new ArrayList<>();
        String result = myBigNumber.sum("1234", "897", recordedSteps::add);

        assertEquals("2131", result);
        assertEquals(4, recordedSteps.size(), "Phép tính 1234 + 897 phải có đúng 4 bước diễn giải");

        // Kiểm tra nội dung bước 1 và bước 2 đúng format đề bài
        assertTrue(recordedSteps.get(0).contains("Bước 1: Lấy 4 cộng với 7 được 11. Lưu 1 vào kết quả và nhớ 1."));
        assertTrue(recordedSteps.get(1).contains("Bước 2: Lấy 3 cộng với 9 được 12. Cộng tiếp với nhớ 1 được 13. Lưu 3 vào kết quả được kết quả mới là \"31\". Ghi nhớ 1."));
    }

    @Test
    @DisplayName("TC08: Kiểm tra xử lý ngoại lệ khi tham số là null hoặc chứa ký tự không hợp lệ")
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum(null, "123"));
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("123", null));
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("12a3", "456"));
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("-123", "456"));
    }
}
