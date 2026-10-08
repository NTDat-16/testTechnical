package com.mybignumber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử Unit Test cho MyBigNumber bằng JUnit 5.
 * Viết đầy đủ các trường hợp để kiểm tra thuật toán chạy đúng và không bị lỗi.
 */
public class MyBigNumberTest {

    private MyBigNumber myBigNumber;

    @BeforeEach
    void setUp() {
        myBigNumber = new MyBigNumber();
    }

    // 1. Kiểm tra ví dụ mẫu trong đề bài
    @Test
    @DisplayName("TC01: Test ví dụ trong đề bài sum('1234', '897') = '2131'")
    void testViDuDeBai() {
        String result = myBigNumber.sum("1234", "897");
        assertEquals("2131", result, "1234 + 897 phải bằng 2131");
    }

    // 2. Hai số có cùng độ dài chữ số
    @ParameterizedTest(name = "TC02: {0} + {1} = {2}")
    @CsvSource({
            "123, 456, 579",
            "111, 222, 333",
            "500, 500, 1000",
            "999, 999, 1998"
    })
    @DisplayName("TC02: Hai số có cùng độ dài")
    void testHaiSoCungDoDai(String stn1, String stn2, String expected) {
        assertEquals(expected, myBigNumber.sum(stn1, stn2));
    }

    // 3. Hai số có độ dài khác nhau
    @ParameterizedTest(name = "TC03: {0} + {1} = {2}")
    @CsvSource({
            "987654, 12, 987666",
            "12, 987654, 987666",
            "1, 9999, 10000",
            "9999, 1, 10000",
            "1000000, 5, 1000005",
            "5, 1000000, 1000005"
    })
    @DisplayName("TC03: Hai số lệch độ dài (số trước dài hơn hoặc số sau dài hơn)")
    void testHaiSoKhacDoDai(String stn1, String stn2, String expected) {
        assertEquals(expected, myBigNumber.sum(stn1, stn2));
    }

    // 4. Kiểm tra việc nhớ liên tiếp qua nhiều hàng (ví dụ: 999 + 1 = 1000)
    @Test
    @DisplayName("TC04: Kiểm tra phép nhớ liên tiếp qua nhiều hàng")
    void testPhepNhoLienTiep() {
        assertEquals("1000", myBigNumber.sum("999", "1"));
        assertEquals("10000000000", myBigNumber.sum("9999999999", "1"));
        assertEquals("100000000000000000000", myBigNumber.sum("99999999999999999999", "1"));
    }

    // 5. Kiểm tra với số 0 và các số có 1 chữ số
    @ParameterizedTest(name = "TC05: {0} + {1} = {2}")
    @CsvSource({
            "0, 0, 0",
            "0, 123, 123",
            "456, 0, 456",
            "4, 7, 11",
            "9, 9, 18"
    })
    @DisplayName("TC05: Cộng với số 0 và số có 1 chữ số")
    void testSo0VaSo1ChuSo(String stn1, String stn2, String expected) {
        assertEquals(expected, myBigNumber.sum(stn1, stn2));
    }

    // 6. Kiểm tra với số siêu lớn (vượt quá giới hạn kiểu long của Java)
    @Test
    @DisplayName("TC06: Test số lớn hơn 100 chữ số để đảm bảo không bị tràn bộ nhớ")
    void testSoSieuLon() {
        // Tạo chuỗi 100 số 9 liên tiếp
        String stn1 = "9".repeat(100);
        String stn2 = "1";
        // Kết quả sẽ là số 1 và 100 số 0
        String expected = "1" + "0".repeat(100);

        String result = myBigNumber.sum(stn1, stn2);
        assertEquals(expected, result);
    }

    // 7. Kiểm tra interface listener gửi đủ các bước tính toán sang giao diện
    @Test
    @DisplayName("TC07: Kiểm tra listener nhận đủ các bước diễn giải")
    void testListenerChoGiaoDien() {
        List<String> listCacBuoc = new ArrayList<>();
        String result = myBigNumber.sum("1234", "897", listCacBuoc::add);

        assertEquals("2131", result);
        // Với 1234 + 897 sẽ có 4 bước tính
        assertEquals(4, listCacBuoc.size(), "Phép tính 1234 + 897 phải có đúng 4 bước");

        // Kiểm tra câu chữ trong bước 1 và bước 2 đúng format đề bài
        assertTrue(listCacBuoc.get(0).contains("Bước 1: Lấy 4 cộng với 7 được 11. Lưu 1 vào kết quả và nhớ 1."));
        assertTrue(listCacBuoc.get(1).contains("Bước 2: Lấy 3 cộng với 9 được 12. Cộng tiếp với nhớ 1 được 13. Lưu 3 vào kết quả được kết quả mới là \"31\". Ghi nhớ 1."));
    }

    // 8. Kiểm tra ném ngoại lệ khi truyền dữ liệu sai (null hoặc có chữ cái)
    @Test
    @DisplayName("TC08: Kiểm tra bắt lỗi khi tham số không hợp lệ")
    void testKiemTraNgoaiLe() {
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum(null, "123"));
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("123", null));
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("12a3", "456"));
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("-123", "456"));
    }
}
