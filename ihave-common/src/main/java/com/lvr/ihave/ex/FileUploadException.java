package com.lvr.ihave.ex;

/**
 * 文件上传异常
 */
public class FileUploadException extends ServiceException {
    public FileUploadException() {
        super();
    }
    public FileUploadException(String message) {
        super(message);
    }
    public FileUploadException(String message, Throwable cause) {
        super(message);
    }

}
