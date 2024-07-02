package com.jwebmp.plugins.angularfileupload;

import org.junit.jupiter.api.Test;

class AngularFileUploadTest
{

    @Test
    void init()
    {
        AngularFileUpload test = new AngularFileUpload("test");
        System.out.println(test.toString(true));

    }
}