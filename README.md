# Dự án Add 2 Numbers (A2N) - MyBigNumber

> **Mã tài liệu:** Add2Num  
> **Tên tài liệu:** Project Add 2 numbers  
> **Phiên bản:** 0.0.1  
> **Tác giả:** Nguyễn Tấn Đạt ([NTDat-16](https://github.com/NTDat-16))  
> **Repository:** [https://github.com/NTDat-16/testTechnical](https://github.com/NTDat-16/testTechnical)  

---

## 1. Giới thiệu bài toán (Introduction)

Dự án cài đặt thuật toán cộng hai số lớn (Large Numbers) được biểu diễn dưới dạng chuỗi kí tự (`String`), mô phỏng trực quan và chính xác các bước cộng mà **học sinh Tiểu học (lớp 3)** đã học và thực hiện:
- Duyệt đồng thời hai chuỗi số từ phải sang trái (từ hàng đơn vị, hàng chục, hàng trăm,...).
- Lấy ra từng ký tự (character) tại mỗi vị trí, chuyển đổi thành chữ số (digit).
- Cộng từng chữ số kèm theo số nhớ (carry) từ hàng trước đó chuyển sang (nếu có).
- Lưu chữ số hàng đơn vị của tổng vào kết quả và ghi nhớ phần chục (nếu tổng $\ge 10$).
- Ghi nhận lại lịch sử chi tiết từng bước thực hiện phép toán thông qua cơ chế **Logging** tiêu chuẩn (`java.util.logging.Logger`).
- Thiết kế module hóa với giao diện callback `IStepListener`, sẵn sàng **đóng gói bàn giao cho nhóm khác làm giao diện (GUI/Console/Web)** để hiển thị trực quan các bước tính toán lên ứng dụng lớn hơn.

---

## 2. Mô phỏng thuật toán với ví dụ `sum("1234", "897")`

Giả sử thực hiện lời gọi hàm `sum("1234", "897")`, quá trình tính toán diễn ra tuần tự như sau:

```
      1 2 3 4
   +    8 9 7
   ----------
   =  2 1 3 1
```

- **Bước 1 (Hàng đơn vị):**
  - Lấy `4` cộng với `7` được `11`.
  - Lưu `1` vào kết quả và nhớ `1`.
  - Kết quả tạm thời: `"1"`.
- **Bước 2 (Hàng chục):**
  - Lấy `3` cộng với `9` được `12`. Cộng tiếp với nhớ `1` được `13`.
  - Lưu `3` vào kết quả được kết quả mới là `"31"`. Ghi nhớ `1`.
- **Bước 3 (Hàng trăm):**
  - Lấy `2` cộng với `8` được `10`. Cộng tiếp với nhớ `1` được `11`.
  - Lưu `1` vào kết quả được kết quả mới là `"131"`. Ghi nhớ `1`.
- **Bước 4 (Hàng nghìn):**
  - Chuỗi `"897"` đã hết chữ số. Lấy chữ số `1` của chuỗi thứ nhất cộng tiếp với nhớ `1` được `2`.
  - Lưu `2` vào kết quả được kết quả mới là `"2131"`. Ghi nhớ `0`.
- **Kết thúc:**
  - Hai chuỗi đã duyệt hết và số nhớ bằng `0`.
  - Kết quả cuối cùng trả về: `"2131"`.

---

## 3. Cấu trúc thư mục dự án (Project Structure)

Dự án được tổ chức theo chuẩn Apache Maven, tách biệt hoàn toàn mã nguồn chính và mã nguồn kiểm thử (Unit Testing) vào các thư mục riêng biệt theo khuyến nghị của đề bài:

```
testTechnical/
├── pom.xml                               # File cấu hình Maven & JUnit 5
├── README.md                             # Hướng dẫn chi tiết biên dịch & kiểm thử
├── .gitignore                            # Cấu hình bỏ qua file biên dịch, IDE
└── src/
    ├── main/
    │   └── java/
    │       ├── MyBigNumber.java          # Lớp lõi xử lý thuật toán cộng 2 số lớn & Logging
    │       └── IStepListener.java        # Interface callback bàn giao cho nhóm làm UI/Console
    └── test/
        └── java/
            └── MyBigNumberTest.java      # Bộ kiểm thử tự động (Unit Test) toàn diện bằng JUnit 5
```

---

## 4. Hướng dẫn Clone mã nguồn theo quy ước đề bài

Theo quy ước đề bài, người khác đóng vai trò kiểm thử sẽ clone dự án về thư mục có cấu trúc:
`<Thư mục gốc>\Projects\<Tên Git Server>\<youraccount>\<projectname>`

### 4.1. Trên hệ điều hành Windows:
Mở PowerShell hoặc Command Prompt và thực hiện:

```powershell
# Tạo thư mục theo đúng quy ước (Ví dụ trên ổ D: hoặc C:)
mkdir -p D:\Projects\github.com\NTDat-16
cd D:\Projects\github.com\NTDat-16

# Clone dự án từ GitHub
git clone https://github.com/NTDat-16/testTechnical.git

# Di chuyển vào thư mục dự án
cd testTechnical

# Kiểm tra phiên bản 0.0.1 qua git tag
git checkout 0.0.1
```
*(Nếu máy tính không có ổ D:, bạn có thể thay thế bằng ổ `C:\Projects\github.com\NTDat-16\testTechnical`)*

### 4.2. Trên macOS hoặc Linux:
Mở Terminal và thực hiện:

```bash
mkdir -p ~/Projects/github.com/NTDat-16
cd ~/Projects/github.com/NTDat-16

git clone https://github.com/NTDat-16/testTechnical.git
cd testTechnical
git checkout 0.0.1
```

---

## 5. Yêu cầu môi trường (Prerequisites)

- **Java Development Kit (JDK):** Phiên bản 11 trở lên (khuyên dùng Java 17 hoặc Java 21).
- **Apache Maven:** Phiên bản 3.6+ (nếu muốn biên dịch và chạy test bằng Maven).

Kiểm tra phiên bản cài đặt trên máy bằng lệnh:
```bash
java -version
mvn -version
```

---

## 6. Hướng dẫn biên dịch và chạy ứng dụng

### Cách 1: Sử dụng Maven (Khuyên dùng - Tiện lợi & Nhanh chóng)

1. **Chạy toàn bộ các ca kiểm thử Unit Test:**
   ```bash
   mvn test
   ```
   *(Maven sẽ tự động tải JUnit 5, biên dịch mã nguồn và chạy toàn bộ 20 test cases. Kết quả hiển thị `BUILD SUCCESS`).*

2. **Biên dịch và đóng gói thành file JAR:**
   ```bash
   mvn clean package
   ```
   *(File JAR hoàn chỉnh sẽ nằm trong thư mục `target/testTechnical-0.0.1.jar`).*

3. **Chạy phương thức `main` mẫu của chương trình:**
   ```bash
   mvn compile exec:java -Dexec.mainClass="MyBigNumber"
   ```

---

### Cách 2: Sử dụng lệnh `javac` và `java` thuần (Không cần cài Maven)

1. **Biên dịch mã nguồn chính:**
   ```bash
   # Tạo thư mục chứa file class đã biên dịch
   mkdir -p bin

   # Biên dịch các file Java với mã hóa UTF-8
   javac -encoding UTF-8 -d bin src/main/java/*.java
   ```

2. **Chạy chương trình chính:**
   ```bash
   java -cp bin MyBigNumber
   ```

---

## 7. Bảng kịch bản kiểm thử (Unit Testing Specification)

Bộ kiểm thử được viết trong `src/test/java/MyBigNumberTest.java` sử dụng **JUnit 5**, bao gồm 20 kịch bản kiểm thử tự động bao quát đầy đủ các tình huống:

| Mã TC | Tên kịch bản kiểm thử | Dữ liệu đầu vào (`stn1`, `stn2`) | Kết quả mong đợi | Mục đích kiểm thử |
| :---: | :--- | :---: | :---: | :--- |
| **TC01** | Test ví dụ mẫu trong đề bài | `"1234"`, `"897"` | `"2131"` | Xác nhận thuật toán hoạt động chính xác theo đúng yêu cầu đề bài |
| **TC02** | Hai số có cùng độ dài | `"123"`, `"456"`<br>`"111"`, `"222"`<br>`"500"`, `"500"`<br>`"999"`, `"999"` | `"579"`<br>`"333"`<br>`"1000"`<br>`"1998"` | Kiểm tra cộng hai số có số lượng chữ số bằng nhau, có và không có nhớ |
| **TC03** | Hai số có độ dài khác nhau | `"987654"`, `"12"`<br>`"12"`, `"987654"`<br>`"1"`, `"9999"`<br>`"9999"`, `"1"` | `"987666"`<br>`"987666"`<br>`"10000"`<br>`"10000"` | Kiểm tra tính đối xứng khi số thứ nhất dài hơn số thứ hai và ngược lại |
| **TC04** | Phép nhớ liên tiếp (Carry propagation) | `"999"`, `"1"`<br>`"9999999999"`, `"1"`<br>`"99...9"` (20 chữ số 9) + `"1"` | `"1000"`<br>`"10000000000"`<br>`"100...0"` (1 kèm 20 số 0) | Kiểm tra lan truyền số nhớ qua nhiều hàng liên tục |
| **TC05** | Số 0 và số có 1 chữ số | `"0"`, `"0"`<br>`"0"`, `"123"`<br>`"456"`, `"0"`<br>`"4"`, `"7"` | `"0"`<br>`"123"`<br>`"456"`<br>`"11"` | Kiểm thử giá trị biên số 0 và cộng các chữ số đơn lẻ |
| **TC06** | Hai số cực lớn (Big Numbers) | `"99...9"` (100 chữ số 9), `"1"` | `"100...0"` (1 kèm 100 số 0) | Kiểm tra giới hạn số học, chứng minh không bị tràn bộ nhớ kiểu `long` |
| **TC07** | Kiểm thử Observer Listener | `"1234"`, `"897"`, `listener` | `"2131"`, nhận đúng 4 bước | Đảm bảo callback `IStepListener` bắn đủ các bước phục vụ nhóm UI |
| **TC08** | Xử lý ngoại lệ đầu vào không hợp lệ | `null`, `"12a3"`, `"-123"` | Ném `IllegalArgumentException` | Kiểm thử tính an toàn (Defensive programming) |

---

## 8. Hướng dẫn tích hợp cho nhóm phát triển giao diện (UI Integration)

Để đóng gói và bàn giao cho nhóm khác làm giao diện (ứng dụng GUI với JavaFX / Swing, hoặc Console App), nhóm giao diện có thể gọi hàm theo 2 cách:

### Cách 1: Chỉ lấy kết quả (vẫn tự động ghi Log)
```java
MyBigNumber myBigNumber = new MyBigNumber();
String result = myBigNumber.sum("1234", "897");
// result = "2131"
```

### Cách 2: Nhận từng bước diễn giải theo thời gian thực để hiển thị lên màn hình giao diện
```java
MyBigNumber myBigNumber = new MyBigNumber();

myBigNumber.sum("1234", "897", new IStepListener() {
    @Override
    public void onStep(String stepMessage) {
        // Cập nhật lên JTextArea / ListView / Web Console
        myTextArea.append(stepMessage + "\n");
    }
});

// Hoặc viết ngắn gọn bằng Lambda expression (Java 8+):
myBigNumber.sum("1234", "897", step -> myTextArea.append(step + "\n"));
```

---

## 9. Lịch sử phiên bản (Version Release)

- **0.0.1 (08/10/2026):**
  - Hoàn thành lớp lõi `MyBigNumber` và phương thức `String sum(String stn1, String stn2)`.
  - Cài đặt hệ thống ghi log chi tiết từng bước bằng `java.util.logging.Logger`.
  - Bổ sung interface `IStepListener` sẵn sàng tích hợp với ứng dụng giao diện.
  - Xây dựng bộ Unit Test 20 kịch bản bằng JUnit 5.
  - Đóng gói chuẩn Maven và hoàn thiện tài liệu hướng dẫn `README.md`.
