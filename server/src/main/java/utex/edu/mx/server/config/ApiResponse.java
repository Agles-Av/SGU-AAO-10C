package utex.edu.mx.server.config;
public class ApiResponse<T> {

    private final int status;
    private final T data;
    private final String message;

    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }


    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, data, message);
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(201, data, message);
    }


    public static <T> ApiResponse<T> badRequest(String message) {
        // En caso de error, 'data' suele ser nulo o un objeto de error específico.
        return new ApiResponse<>(400, null, message);
    }


    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }
}