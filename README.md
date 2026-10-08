# Bài Test Kỹ Thuật: Cộng 2 Số Lớn (Add 2 Numbers) - MyBigNumber

* **Ứng viên:** Nguyễn Tấn Đạt  
* **GitHub:** [NTDat-16](https://github.com/NTDat-16)  
* **Repository:** [https://github.com/NTDat-16/testTechnical](https://github.com/NTDat-16/testTechnical)  
* **Phiên bản hoàn thành:** `0.0.1` (đã gắn Git tag `0.0.1`)  

---

## 1. Lời mở đầu & Cách tiếp cận bài toán

Chào anh/chị tuyển dụng, đây là bài làm của em cho bài toán **Cộng 2 số lớn (dưới dạng chuỗi kí tự)** theo tài liệu yêu cầu **Add2Num**.

### Ý tưởng giải quyết:
Em mô phỏng lại đúng cách học sinh tiểu học (lớp 3) thực hiện phép cộng trên giấy:
1. Đặt 2 số thẳng hàng và duyệt từ phải sang trái (bắt đầu từ hàng đơn vị).
2. Ở mỗi vị trí, lấy chữ số của từng số ra cộng lại với nhau, cộng thêm cả số nhớ (nếu có từ bước trước).
3. Lấy chữ số hàng đơn vị của tổng ghi vào kết quả, còn phần chục thì lưu lại làm số nhớ cho bước tiếp theo.
4. Ghi lại lịch sử chi tiết từng bước tính toán bằng `Logger` của Java theo đúng định dạng mẫu trong đề bài.
5. Để bàn giao cho nhóm khác làm giao diện (UI) hoặc ứng dụng Console, em có tạo thêm một interface `IStepListener` đơn giản để bên giao diện có thể nhận các bước tính toán và hiển thị cho người dùng.

---

## 2. Diễn giải thuật toán qua ví dụ `sum("1234", "897")`

Giả sử thực hiện phép tính `sum("1234", "897")`, các bước thực hiện như sau:

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

## 3. Cấu trúc thư mục dự án

Em tổ chức dự án theo chuẩn Maven, tách riêng mã nguồn chính và mã nguồn kiểm thử vào 2 thư mục khác nhau theo đúng khuyến nghị của đề bài:

```
testTechnical/
├── pom.xml                               # Cấu hình Maven và thư viện JUnit 5
├── README.md                             # Hướng dẫn chi tiết bài làm
├── .gitignore                            # Bỏ qua thư mục target/ và các file tạm của IDE
└── src/
    ├── main/
    │   └── java/
    │       ├── MyBigNumber.java          # Lớp lõi xử lý thuật toán cộng và ghi log
    │       └── IStepListener.java        # Interface gửi các bước cho nhóm làm giao diện
    └── test/
        └── java/
            └── MyBigNumberTest.java      # Bộ kiểm thử Unit Test bằng JUnit 5
```

---

## 4. Hướng dẫn clone mã nguồn về máy theo đúng quy ước

Theo quy ước trong đề bài, người chấm có thể clone dự án về thư mục theo đường dẫn sau:

### 4.1. Trên Windows:
Mở PowerShell hoặc Command Prompt và gõ các lệnh:

```powershell
# Tạo thư mục theo đúng quy ước đề bài (ổ D: hoặc ổ C:)
mkdir -p D:\Projects\github.com\NTDat-16
cd D:\Projects\github.com\NTDat-16

# Clone source code từ GitHub
git clone https://github.com/NTDat-16/testTechnical.git

# Di chuyển vào thư mục dự án
cd testTechnical

# Xem đúng phiên bản 0.0.1 đã nộp
git checkout 0.0.1
```
*(Nếu máy không có ổ D:, anh/chị có thể thay bằng `C:\Projects\github.com\NTDat-16\testTechnical`)*

### 4.2. Trên macOS / Linux:
```bash
mkdir -p ~/Projects/github.com/NTDat-16
cd ~/Projects/github.com/NTDat-16

git clone https://github.com/NTDat-16/testTechnical.git
cd testTechnical
git checkout 0.0.1
```

---

## 5. Hướng dẫn biên dịch và chạy chương trình

Anh/chị có thể chạy dự án bằng **Maven** hoặc bằng lệnh **javac / java** thuần:

### Cách 1: Sử dụng Maven (khuyên dùng, nhanh và tiện)

1. **Chạy toàn bộ các ca Unit Test:**
   ```bash
   mvn test
   ```
   *(Maven sẽ tự động tải JUnit 5, chạy 20 test cases và báo `BUILD SUCCESS`)*

2. **Chạy hàm `main` mẫu của chương trình:**
   ```bash
   mvn compile exec:java -Dexec.mainClass="MyBigNumber"
   ```

3. **Đóng gói ra file `.jar`:**
   ```bash
   mvn clean package
   ```
   *(File jar sẽ được tạo trong thư mục `target/testTechnical-0.0.1.jar`)*

---

### Cách 2: Sử dụng dòng lệnh `javac` và `java` thuần (không cần Maven)

1. **Biên dịch mã nguồn:**
   ```bash
   mkdir -p bin
   javac -encoding UTF-8 -d bin src/main/java/*.java
   ```

2. **Chạy chương trình:**
   ```bash
   java -cp bin MyBigNumber
   ```

---

## 6. Các trường hợp kiểm thử (Unit Test Cases)

Em đã viết 20 ca kiểm thử tự động trong file `src/test/java/MyBigNumberTest.java` để bao quát các trường hợp có thể xảy ra:

| STT | Tên ca test | Dữ liệu đầu vào | Kết quả mong muốn | Mục đích kiểm tra |
| :---: | :--- | :---: | :---: | :--- |
| **TC01** | Test ví dụ đề bài | `"1234"`, `"897"` | `"2131"` | Đảm bảo chạy đúng ví dụ mẫu của tài liệu |
| **TC02** | Hai số cùng độ dài | `"123"`, `"456"`<br>`"500"`, `"500"`<br>`"999"`, `"999"` | `"579"`<br>`"1000"`<br>`"1998"` | Kiểm tra khi hai số có số chữ số bằng nhau |
| **TC03** | Hai số khác độ dài | `"987654"`, `"12"`<br>`"12"`, `"987654"`<br>`"1"`, `"9999"` | `"987666"`<br>`"987666"`<br>`"10000"` | Kiểm tra khi số trước dài hơn số sau hoặc ngược lại |
| **TC04** | Phép nhớ liên tiếp | `"999"`, `"1"`<br>`"9999999999"`, `"1"` | `"1000"`<br>`"10000000000"` | Kiểm tra số nhớ chạy liên tục qua nhiều hàng |
| **TC05** | Số 0 và số 1 chữ số | `"0"`, `"0"`<br>`"0"`, `"123"`<br>`"4"`, `"7"` | `"0"`<br>`"123"`<br>`"11"` | Kiểm tra các trường hợp biên với số 0 |
| **TC06** | Số siêu lớn | 100 chữ số 9 cộng `"1"` | 1 kèm 100 chữ số 0 | Đảm bảo không bị tràn bộ nhớ kiểu `long` |
| **TC07** | Test giao diện nhận bước | `"1234"`, `"897"` | Trả về đủ 4 bước diễn giải | Đảm bảo nhóm làm UI nhận được đầy đủ các bước |
| **TC08** | Kiểm tra bắt lỗi đầu vào | `null`, `"12a3"`, `"-123"` | Ném `IllegalArgumentException` | Bắt lỗi nếu người dùng truyền chuỗi sai quy cách |

---

## 7. Cách nhóm khác gọi hàm để làm giao diện (UI)

Nếu bàn giao cho nhóm khác làm giao diện (JavaFX, Swing hoặc Console), các bạn có thể gọi hàm như sau:

```java
MyBigNumber myBigNumber = new MyBigNumber();

// Cách 1: Chỉ lấy kết quả (hệ thống vẫn tự ghi log)
String ketQua = myBigNumber.sum("1234", "897");

// Cách 2: Lấy từng bước diễn giải để in ra màn hình hoặc hiển thị lên giao diện
myBigNumber.sum("1234", "897", new IStepListener() {
    @Override
    public void onStep(String message) {
        // Ví dụ: hiển thị lên JTextArea hoặc in ra Console
        System.out.println(message);
    }
});

// Hoặc viết ngắn gọn bằng biểu thức Lambda (Java 8 trở lên):
myBigNumber.sum("1234", "897", stepMsg -> myTextArea.append(stepMsg + "\n"));
```

---

## 8. Lời cảm ơn

Em xin chân thành cảm ơn quý anh/chị tuyển dụng đã dành thời gian đọc và đánh giá bài làm của em. Rất mong nhận được những góp ý quý báu từ anh/chị để em tiếp tục học hỏi và hoàn thiện bản thân hơn.
