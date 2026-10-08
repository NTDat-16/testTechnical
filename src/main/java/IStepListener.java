/**
 * Interface lắng nghe từng bước tính toán của phép cộng.
 * Được tạo ra để bàn giao cho các bạn làm giao diện (UI) hoặc ứng dụng Console
 * có thể lấy các bước giải thích và hiển thị lên màn hình.
 */
public interface IStepListener {
    /**
     * Hàm được gọi mỗi khi tính xong một bước.
     * @param message Câu diễn giải bước tính toán (ví dụ: "Bước 1: Lấy 4 cộng 7...")
     */
    void onStep(String message);
}
