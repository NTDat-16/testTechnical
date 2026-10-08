# Ứng Dụng Web Cộng 2 Số Lớn - MyBigNumber Web (Task 2)

* **Ứng viên:** Nguyễn Tấn Đạt  
* **GitHub:** [NTDat-16](https://github.com/NTDat-16)  
* **Repository:** [https://github.com/NTDat-16/testTechnical](https://github.com/NTDat-16/testTechnical)  
* **Nhánh Git hiện tại:** `web`  

---

## 1. Giới thiệu Task 2

Đây là nhánh **`web`** thực hiện **TASK 2** theo yêu cầu đề bài:
* Xây dựng ứng dụng Web hoàn chỉnh bằng **Spring Boot 3**, **Thymeleaf**, và **Bootstrap 5**.
* **Tái sử dụng kết quả Task 1 như một thư viện (gói .jar):**
  Nhánh `web` sử dụng trực tiếp file thư viện `libs/MyBigNumber-0.0.1.jar` (được build và đóng gói từ nhánh `core`) mà không cần phải sao chép lại mã nguồn của Task 1.
* **Hiển thị tiến trình thực hiện phép toán:**
  Trên giao diện có khu vực hiển thị chi tiết từng bước tính toán (Bước 1, Bước 2, Bước 3,...) mô phỏng cách học sinh tiểu học cộng từ phải sang trái.

---

## 2. Cấu trúc thư mục nhánh `web`

```
testTechnical/ (Nhánh 'web')
├── pom.xml                               # Cấu hình Spring Boot & nạp thư viện libs/MyBigNumber-0.0.1.jar
├── README.md                             # Hướng dẫn chạy ứng dụng Web Task 2
├── .gitignore                            # Bỏ qua thư mục target/ và các file tạm
├── libs/
│   └── MyBigNumber-0.0.1.jar             # [THƯ VIỆN TASK 1] Đóng gói từ nhánh 'core'
└── src/
    ├── main/
    │   ├── java/com/mybignumber/web/
    │   │   ├── WebApplication.java       # Lớp khởi động Spring Boot
    │   │   ├── controller/
    │   │   │   └── BigNumberController.java  # Controller điều hướng giao diện & REST API
    │   │   └── service/
    │   │       └── BigNumberService.java     # Gọi MyBigNumber từ file jar và lấy tiến trình
    │   └── resources/
    │       ├── application.properties        # Cấu hình cổng 8080, Thymeleaf UTF-8
    │       └── templates/
    │           └── index.html            # Giao diện Web thiết kế bằng Thymeleaf & Bootstrap 5
    └── test/java/com/mybignumber/web/
        └── WebApplicationTests.java      # Kiểm thử tự động giao diện Web
```

---

## 3. Cách cấu hình nạp file `.jar` từ thư mục `libs/`

Trong file `pom.xml`, thư viện Task 1 được nạp trực tiếp qua thẻ `systemPath`:

```xml
<!-- TÁI SỬ DỤNG KẾT QUẢ TASK 1 DƯỚI DẠNG GÓI THƯ VIỆN .JAR -->
<dependency>
    <groupId>com.mybignumber</groupId>
    <artifactId>core</artifactId>
    <version>0.0.1</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/MyBigNumber-0.0.1.jar</systemPath>
</dependency>
```

Và trong code Java (`BigNumberService.java`), ta gọi trực tiếp lớp `MyBigNumber` từ file JAR này:

```java
import com.mybignumber.MyBigNumber;

// Gọi hàm tính toán và thu thập các bước
MyBigNumber myBigNumber = new MyBigNumber();
String sum = myBigNumber.sum(stn1, stn2, steps::add);
```

---

## 4. Hướng dẫn biên dịch và chạy ứng dụng Web

### 4.1. Chạy kiểm thử tự động (Unit Test):
```bash
mvn clean test
```

### 4.2. Khởi động máy chủ Web:
```bash
mvn spring-boot:run
```

Sau khi màn hình hiển thị:
```text
Started WebApplication in ... seconds
```
Mở trình duyệt bất kỳ (Chrome, Edge, Firefox) và truy cập vào địa chỉ:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 5. Các tính năng trên trang Web

1. **Form nhập liệu:**
   * Cho phép nhập 2 số lớn tùy ý với độ dài không giới hạn.
   * Có các nút bấm mẫu: **"Ví dụ đề bài (1234 + 897)"**, **"Nhớ liên tục (999... + 1)"**, **"Số cực lớn (50 chữ số)"**.
2. **Hiển thị kết quả:**
   * Hộp thông báo màu xanh hiển thị rõ `stn1 + stn2 = result` và độ dài chữ số.
3. **Hiển thị tiến trình thực hiện phép toán (Yêu cầu trọng tâm Task 2):**
   * Hiển thị tổng số bước tính toán.
   * Danh sách từng bước (Bước 1, Bước 2, Bước 3,...) với diễn giải chuẩn xác theo thuật toán học sinh lớp 3.
4. **Bắt lỗi dữ liệu:**
   * Tự động báo lỗi nếu nhập sai định dạng (có chứa chữ cái, số âm, hoặc để trống).
5. **Hỗ trợ REST API:**
   * Có sẵn endpoint `POST /api/calculate` trả về kết quả dạng JSON.

---

## 6. Cách chuyển đổi giữa 2 nhánh Git (`core` và `web`)

* **Để xem Task 1 (Mã nguồn thuật toán lõi & kiểm thử):**
  ```bash
  git checkout core
  ```
* **Để xem Task 2 (Ứng dụng Web Spring Boot gọi file .jar của Task 1):**
  ```bash
  git checkout web
  ```
