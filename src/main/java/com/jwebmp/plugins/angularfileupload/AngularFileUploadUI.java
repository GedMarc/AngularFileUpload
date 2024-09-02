package com.jwebmp.plugins.angularfileupload;

import com.jwebmp.core.Component;
import com.jwebmp.core.base.html.Button;
import com.jwebmp.core.base.html.DivSimple;

public class AngularFileUploadUI extends DivSimple<AngularFileUploadUI>
{
    private final String fileUploadId;
    private Component<?, ?, ?, ?, ?> uploadButton;

    public AngularFileUploadUI(String fileUploadId)
    {
        this.fileUploadId = fileUploadId;
        addClass("file-upload");
        setText("{{fileName || \"No file uploaded yet.\"}}");
        uploadButton = new Button<>("Upload");
        setRenderTextBeforeChildren(true);
    }

    public Component<?, ?, ?, ?, ?> getUploadButton()
    {
        return uploadButton;
    }

    public AngularFileUploadUI setUploadButton(Component<?, ?, ?, ?, ?> uploadButton)
    {
        this.uploadButton = uploadButton;
        return this;
    }

    @Override
    protected void init()
    {
        add(uploadButton);
        uploadButton.addAttribute("(click)", "fileUpload" + fileUploadId + ".click()");
        super.init();
    }
}
