package com.jwebmp.plugins.angularfileupload;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import io.vertx.ext.web.FileUpload;

/**
 * Interceptor for when a file is uploaded.
 * <p>
 * Occurs after placed into the session storage (remember to clear session storage properties
 */
public interface OnFileUploadInterceptor<J extends OnFileUploadInterceptor<J>> extends IDefaultService<J>
{
    /**
     * The name of the uploader to use - identifies which interceptor to use
     *
     * @return
     */
    default String name()
    {
        return "";
    }

    /**
     * Occurs when a file has been successfully uploaded
     *
     * @param file
     */
    void onUploadCompleted(FileUpload file, byte[] bytes);
}
