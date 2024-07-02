package com.jwebmp.plugins.angularfileupload.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.guicedee.client.CallScoper;
import com.guicedee.client.IGuiceContext;
import com.guicedee.vertx.spi.VertxRouterConfigurator;
import com.jwebmp.core.base.ajax.AjaxCall;
import com.jwebmp.plugins.angularfileupload.OnFileUploadInterceptor;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import static com.guicedee.guicedinjection.interfaces.ObjectBinderKeys.DefaultObjectMapper;

@Log
public class AngularFileUploadRouterConfiguration implements VertxRouterConfigurator
{
    @Override
    public Router builder(Router router)
    {
        router.post("/angular-file-uploads")
              .handler(ctx -> {
                  String localstorageString = ctx.request()
                                                 .getFormAttribute("localStorage");
                  String sessionStorageString = ctx.request()
                                                   .getFormAttribute("sessionStorage");
                  String url = ctx.request()
                                  .getFormAttribute("url");
                  String action = ctx.request()
                                     .getFormAttribute("action");
                  String eventType = ctx.request()
                                        .getFormAttribute("eventType");
                  String interceptorName = ctx.request()
                                              .getFormAttribute("interceptorName");
                  String id = ctx.request()
                                 .getFormAttribute("id");
                  String dateTime = ctx.request()
                                       .getFormAttribute("datetime");
                  String parameters = ctx.request()
                                         .getFormAttribute("parameters");

                  ObjectMapper mapper = IGuiceContext.get(DefaultObjectMapper);
                  CallScoper scoper = IGuiceContext.get(CallScoper.class);
                  scoper.enter();
                  try
                  {
                      AjaxCall<?> call = IGuiceContext.get(AjaxCall.class);
                      //call.setDatetime()
                      call.setLocalStorage(mapper.readValue(localstorageString, new TypeReference<Map<String, String>>()
                      {
                      }));
                      call.setSessionStorage(mapper.readValue(sessionStorageString, new TypeReference<Map<String, String>>()
                      {
                      }));
                      call.setComponentId(id);
                      //call.setParameters()

                      for (FileUpload f : ctx.fileUploads())
                      {
                          ctx.response()
                             .setChunked(true);
                          String fileName = f.uploadedFileName();
                          File file = new File(fileName);
                          byte[] bytes = FileUtils.readFileToByteArray(file);
                          Set<OnFileUploadInterceptor> intercepters = IGuiceContext.loaderToSet(ServiceLoader.load(OnFileUploadInterceptor.class));
                          if (intercepters.isEmpty())
                          {
                              log.warning(
                                      "There are no file upload interceptors to catch this file upload. Create a class that implements " + "OnFileUploadInterceptor to use this file.");
                          }
                          else
                          {
                              for (OnFileUploadInterceptor a : intercepters)
                              {
                                  if (Strings.isNullOrEmpty(interceptorName))
                                  {
                                      interceptorName = "";
                                  }
                                  if (a.name()
                                       .equals(interceptorName))
                                  {
                                      a.onUploadCompleted(f, bytes);
                                  }
                              }
                          }
                      }
                  }
                  catch (IOException e)
                  {
                      throw new RuntimeException(e);
                  }
                  finally
                  {
                      scoper.exit();
                  }
                  ctx.response()
                     .end();
              });
        return router;
    }
}
