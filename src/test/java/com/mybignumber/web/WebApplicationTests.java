package com.mybignumber.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Kiểm thử tự động ứng dụng Web Spring Boot (Task 2)
 */
@SpringBootTest
@AutoConfigureMockMvc
class WebApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Kiểm tra trang chủ index hiển thị bình thường")
    void testTrangChuHienThi() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("MyBigNumber Web")));
    }

    @Test
    @DisplayName("Kiểm tra tính năng POST /calculate với ví dụ sum('1234', '897') = '2131'")
    void testTinhToanWeb() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("stn1", "1234")
                        .param("stn2", "897"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", "2131"))
                .andExpect(content().string(containsString("2131")))
                .andExpect(content().string(containsString("Bước 1: Lấy 4 cộng với 7 được 11.")))
                .andExpect(content().string(containsString("Bước 4: Lấy 1 cộng tiếp với nhớ 1 được 2.")));
    }

    @Test
    @DisplayName("Kiểm tra báo lỗi khi người dùng nhập dữ liệu không hợp lệ")
    void testBaoLoiKhiNhapChuCai() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("stn1", "12a4")
                        .param("stn2", "897"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(content().string(containsString("Tham số chỉ được chứa các kí số từ 0 đến 9")));
    }
}
