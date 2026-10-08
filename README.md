# Bài Test Kỹ Thuật: Dự Án Cộng 2 Số Lớn (Add 2 Numbers)

* **Ứng viên:** Nguyễn Tấn Đạt  
* **GitHub:** [NTDat-16](https://github.com/NTDat-16)  
* **Repository:** [https://github.com/NTDat-16/testTechnical](https://github.com/NTDat-16/testTechnical)  
* **Phiên bản hoàn thành:** `0.0.1`  

---

## 1. Tổng quan dự án

Dự án được tổ chức theo mô hình **Maven Multi-Module** chuẩn, tách biệt rõ ràng giữa phần lõi thuật toán (Task 1) và phần ứng dụng Web (Task 2):

```
testTechnical/ (Thư mục gốc)
├── pom.xml                               # Parent POM quản lý chung cả 2 module
├── README.md                             # Tài liệu hướng dẫn chi tiết
├── .gitignore                            # Cấu hình bỏ qua thư mục target/ và file rác
├── core/                                 # [TASK 1] Module thuật toán lõi & Unit Test
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/mybignumber/
│       │   ├── MyBigNumber.java          # Lớp lõi xử lý thuật toán cộng 2 số lớn & Logging
│       │   └── IStepListener.java        # Interface callback gửi từng bước diễn giải
│       └── test/java/com/mybignumber/
│           └── MyBigNumberTest.java      # 20 ca kiểm thử tự động bằng JUnit 5
└── web/                                  # [TASK 2] Module ứng dụng Web (Spring Boot)
    ├── pom.xml                           # Tái sử dụng module core như một sub-module/thư viện
    └── src/
        ├── main/
        │   ├── java/com/mybignumber/web/
        │   │   ├── WebApplication.java   # Lớp khởi động Spring Boot
        │   │   ├── controller/
        │   │   │   └── BigNumberController.java  # Controller điều hướng giao diện & REST API
        │   │   └── service/
        │   │       └── BigNumberService.java     # Service kết nối và nhận tiến trình từ module core
        │   └── resources/
        │       ├── application.properties        # Cấu hình cổng 8080, Thymeleaf UTF-8
        │       └── templates/
        │           └── index.html        # Giao diện Web thiết kế bằng Thymeleaf & Bootstrap 5
        └── test/java/com/mybignumber/web/
            └── WebApplicationTests.java  # Kiểm thử tự động giao diện Web và xử lý form
```

---

## 2. TASK 1: Thuật toán lõi cộng 2 số lớn (`core`)

### Ý tưởng giải quyết:
Mô phỏng lại đúng cách học sinh tiểu học (lớp 3) thực hiện phép cộng trên giấy:
1. Đặt 2 số thẳng hàng và duyệt từ phải sang trái (bắt đầu từ hàng đơn vị).
2. Ở mỗi vị trí, lấy chữ số của từng số ra cộng lại với nhau, cộng thêm cả số nhớ (nếu có từ bước trước).
3. Lấy chữ số hàng đơn vị của tổng ghi vào kết quả, còn phần chục thì lưu lại làm số nhớ cho bước tiếp theo.
4. Ghi lại lịch sử chi tiết từng bước tính toán bằng `Logger` của Java theo đúng định dạng mẫu trong đề bài.
5. Cung cấp interface callback `IStepListener` để module khác (giao diện / console) có thể nhận các bước tính toán theo thời gian thực.

### Mô phỏng ví dụ `sum("1234", "897")`:
```
      1 2 3 4
   +    8 9 7
   ----------
   =  2 1 3 1
```
* **Bước 1 (Hàng đơn vị):** Lấy `4` cộng với `7` được `11`. Lưu `1` vào kết quả và nhớ `1`. (Kết quả tạm: `"1"`).
* **Bước 2 (Hàng chục):** Lấy `3` cộng với `9` được `12`. Cộng tiếp với nhớ `1` được `13`. Lưu `3` vào kết quả được kết quả mới là `"31"`. Ghi nhớ `1`.
* **Bước 3 (Hàng trăm):** Lấy `2` cộng với `8` được `10`. Cộng tiếp với nhớ `1` được `11`. Lưu `1` vào kết quả được kết quả mới là `"131"`. Ghi nhớ `1`.
* **Bước 4 (Hàng nghìn):** Chuỗi `"897"` đã hết chữ số. Lấy `1` cộng tiếp với nhớ `1` được `2`. Lưu `2` vào kết quả được kết quả mới là `"2131"`. Ghi nhớ `0`.
* **Kết thúc:** Cả 2 số đều đã duyệt xong và không còn nhớ. Kết quả cuối cùng là `"2131"`.

---

## 3. TASK 2: Ứng dụng Web cộng 2 số lớn (`web`)

Ứng dụng Web được xây dựng hoàn chỉnh bằng:
* **Spring Boot 3:** Nền tảng backend mạnh mẽ, tích hợp máy chủ nhúng Tomcat.
* **Thymeleaf:** Template engine hiển thị dữ liệu động từ backend lên HTML.
* **Bootstrap 5 & Bootstrap Icons:** Giao diện hiện đại, chuẩn Responsive trên cả điện thoại và máy tính.
* **Tái sử dụng Task 1:** Khai báo module `core` trực tiếp trong file `web/pom.xml`:
  ```xml
  <dependency>
      <groupId>com.github.ntdat</groupId>
      <artifactId>core</artifactId>
  </dependency>
  ```

### Các tính năng trên trang Web:
1. **Form nhập liệu thông minh:**
   * Cho phép nhập 2 số lớn tùy ý với độ dài không giới hạn.
   * Có các nút bấm nhanh dữ liệu mẫu: *Ví dụ đề bài (1234 + 897)*, *Nhớ liên tục (999... + 1)*, *Số cực lớn (50 chữ số)*.
   * Nút *Làm mới* để xóa trắng form.
2. **Kiểm tra và bắt lỗi (Validation):**
   * Nếu người dùng nhập chữ cái, số âm hoặc để trống, hệ thống sẽ hiển thị thông báo lỗi màu đỏ rõ ràng trên giao diện.
3. **Hiển thị kết quả:**
   * Hộp kết quả nổi bật, hiển thị công thức `stn1 + stn2 = result` kèm số lượng chữ số của kết quả.
4. **Hiển thị tiến trình thực hiện phép toán (Yêu cầu trọng tâm):**
   * Sử dụng callback `IStepListener` từ module `core` để hứng từng bước tính toán.
   * Hiển thị danh sách các bước dạng thẻ (Card/List item) trực quan:
     * *Bước 1: Lấy 4 cộng với 7 được 11. Lưu 1 vào kết quả và nhớ 1.*
     * *Bước 2: Lấy 3 cộng với 9 được 12. Cộng tiếp với nhớ 1 được 13...*
5. **Hỗ trợ REST API:**
   * Endpoint `POST /api/calculate` trả về định dạng JSON nếu cần gọi từ ứng dụng khác.

---

## 4. Hướng dẫn clone mã nguồn theo quy ước đề bài

Người chấm có thể clone dự án về thư mục theo đúng cấu trúc quy ước trong đề bài:

### 4.1. Trên Windows:
Mở PowerShell hoặc Command Prompt và gõ:

```powershell
# Tạo thư mục theo đúng quy ước đề bài (ổ D: hoặc ổ C:)
mkdir -p D:\Projects\github.com\NTDat-16
cd D:\Projects\github.com\NTDat-16

# Clone source code từ GitHub
git clone https://github.com/NTDat-16/testTechnical.git

# Di chuyển vào thư mục dự án
cd testTechnical
```
*(Nếu máy không có ổ D:, có thể thay thế bằng `C:\Projects\github.com\NTDat-16\testTechnical`)*

### 4.2. Trên macOS / Linux:
```bash
mkdir -p ~/Projects/github.com/NTDat-16
cd ~/Projects/github.com/NTDat-16

git clone https://github.com/NTDat-16/testTechnical.git
cd testTechnical
```

---

## 5. Hướng dẫn biên dịch và chạy ứng dụng

### 5.1. Chạy toàn bộ kiểm thử Unit Test (cả Task 1 và Task 2)
Từ thư mục gốc dự án, gõ lệnh:
```bash
mvn clean test
```
*Maven sẽ biên dịch và chạy thành công cả 20 test cases của `core` và 3 test cases của `web` (`BUILD SUCCESS`).*

---

### 5.2. Khởi động ứng dụng Web (Task 2)
Để khởi động trang Web Spring Boot, gõ lệnh:
```bash
mvn spring-boot:run -pl web
```
Sau khi màn hình hiển thị:
```text
Started WebApplication in ... seconds (process running for ...)
```
Mở trình duyệt web bất kỳ và truy cập vào địa chỉ:
👉 **[http://localhost:8080](http://localhost:8080)**

---

### 5.3. Đóng gói toàn bộ dự án ra file JAR
```bash
mvn clean package
```
* Kết quả module core: `core/target/core-0.0.1.jar` (thư viện có thể mang đi tích hợp dự án khác).
* Kết quả module web: `web/target/web-0.0.1.jar` (ứng dụng web độc lập, có thể chạy bằng `java -jar web/target/web-0.0.1.jar`).

---

## 6. Lời cảm ơn

Em xin chân thành cảm ơn quý anh/chị tuyển dụng đã dành thời gian đọc và đánh giá bài làm của em cho cả Task 1 và Task 2. Em rất mong nhận được những góp ý từ anh/chị để tiếp tục trau dồi và phát triển chuyên môn!
