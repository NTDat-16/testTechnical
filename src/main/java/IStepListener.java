/**
 * Interface IStepListener định nghĩa callback lắng nghe từng bước thực hiện của thuật toán cộng.
 * Được thiết kế để đóng gói và bàn giao cho các nhóm khác phát triển giao diện (GUI/Console/Web)
 * có thể nhận và hiển thị trực quan các bước tính toán theo thời gian thực.
 */
@FunctionalInterface
public interface IStepListener {
    /**
     * Được gọi sau mỗi bước thực hiện phép cộng.
     *
     * @param stepMessage Diễn giải chi tiết bước thực hiện (ví dụ: "Bước 1: Lấy 4 cộng với 7 được 11...")
     */
    void onStep(String stepMessage);
}
