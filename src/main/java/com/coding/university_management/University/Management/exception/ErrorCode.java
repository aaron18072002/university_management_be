package com.coding.university_management.University.Management.exception;

public enum ErrorCode {
    USER_EXISTED(409, "Tài khoản đã tồn tại"),
    USER_NOT_EXISTED(404, "Tài khoản không tồn tại"),
    UNCATEGORIZED_EXCEPTION(500, "Lỗi không xác định"),
    VALIDATION_EXCEPTION(400, "Lỗi dữ liệu đầu vào"),
    AUTHENTICATED(401, "Bạn đã nhập sai mật khẩu")
    ;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
