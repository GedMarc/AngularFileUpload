package com.jwebmp.plugins.angularfileupload;

import com.jwebmp.core.base.angular.client.annotations.angular.NgComponent;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportProvider;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.components.NgInput;
import com.jwebmp.core.base.angular.client.annotations.constructors.NgConstructorParameter;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;
import com.jwebmp.core.base.angular.client.annotations.structures.NgMethod;
import com.jwebmp.core.base.angular.client.services.interfaces.INgComponent;
import com.jwebmp.core.base.html.DivSimple;
import com.jwebmp.core.base.html.inputs.InputFileType;

import java.util.Set;

@NgComponent("angular-file-upload")
@NgField("fileName = '';")
@NgMethod("""
        onFileSelected(event : any) {
                const file:File = event.target.files[0];
                if (file) {
                    this.fileName = file.name;
                    const formData = new FormData();
                    formData.append("thumbnail", file);
                    this.send(formData);
                    const upload$ = this.http.post("/angular-file-uploads", formData);
                    upload$.subscribe();
                }
            }""")
@NgImportReference(value = "HttpClient", reference = "@angular/common/http")
@NgConstructorParameter("private http : HttpClient")
@NgBootImportReference(value = "provideHttpClient", reference = "@angular/common/http")
@NgBootImportProvider("provideHttpClient()")
@NgInput(value = "interceptorName")
@NgInput(value = "id")
//@NgComponentReference(SocketClientService.class)
@NgMethod("""
              send(data: FormData): void {
                                 data.append('action','FileUpload');
                                 data.append('interceptorName',this.interceptorName);
                                 data.append('id',this.id);
                                 data.append('url',JSON.stringify(window.location));
                                 data.append('localStorage',JSON.stringify(window.localStorage));
                                 data.append('sessionStorage',JSON.stringify(window.sessionStorage));
                                 data.append('parameters',JSON.stringify(this.getParametersObject()));
                                 data.append('hashbang',window.location.hash);
                                 //data.append('state',this.routeLocation.getState());
                                 data.append('history',JSON.stringify(history.state));
                                 data.append('datetime',new Date().getUTCDate().toString());
                                 data.append('eventType','FileUpload');
                                 //data.append('headers',{});
                                 data.append('headers.useragent',navigator.userAgent);
                                 data.append('headers.cookieEnabled',JSON.stringify(navigator.cookieEnabled));
                                 data.append('headers.appName',navigator.appName);
                                 data.append('headers.appVersion',navigator.appVersion);
                                 data.append('headers.language',navigator.language);
                             }
        """)
@NgMethod("getParametersObject() : object {\n" +
        "    try {\n" +
        "        var search = location.search.substring(1);\n" +
        "        return JSON.parse('{\"' + decodeURI(search).replace(/\"/g, '\\\\\"').replace(/&/g, '\",\"').replace(/=/g, '\":\"') + '\"}');\n" +
        "    } catch (err) {\n" +
        "        return {};\n" +
        "    }\n" +
        "}\n")
//@NgImportReference(value = "Location", reference = "@angular/common")
@NgImportReference(value = "RouterModule, ParamMap,Router", reference = "@angular/router")
@NgImportReference(value = "ActivatedRoute", reference = "@angular/router")
//@NgConstructorParameter("private routeLocation: Location")
@NgConstructorParameter("private router: Router")
@NgConstructorParameter("private route: ActivatedRoute")
@NgImportReference(value = "FormsModule", reference = "@angular/forms")


public class AngularFileUpload extends DivSimple<AngularFileUpload> implements INgComponent<AngularFileUpload>
{

    @Override
    public Set<String> moduleImports()
    {
        var s = INgComponent.super.moduleImports();
        s.add("FormsModule");
        return s;
    }


    private InputFileType<?> fileInput;
    private AngularFileUploadUI ui;

    public AngularFileUpload()
    {
        this("id");
    }

    public AngularFileUpload(String id)
    {
        fileInput = new InputFileType<>();
        fileInput.addClass("file-input");
        fileInput.addStyle("display", "none");
        fileInput.setID(id);
        fileInput.addAttribute("(change)", "onFileSelected($event)");
        fileInput.getProperties()
                 .put("noName", "true");
        ui = new AngularFileUploadUI(id);
    }

    @Override
    protected void init()
    {
        super.init();
        if (fileInput != null)
        {
            add(fileInput);
            fileInput.addAttribute("#fileUpload" + fileInput.getID(), "");
            fileInput.removeAttribute("#" + getID());
        }
        if (ui != null)
        {
            add(ui);
        }
    }

}
