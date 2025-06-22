package sys.librarymanage.ApiResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiResponse<T> {
    private int code;        // 状态码（200、400、404等）
    private String message;  // 提示信息
    private T data;          // 实际返回数据

    // 构造器
    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 快捷工厂方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "操作成功", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}