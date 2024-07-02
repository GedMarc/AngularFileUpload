import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions;
import com.guicedee.vertx.spi.VertxRouterConfigurator;
import com.jwebmp.plugins.angularfileupload.implementations.AngularFileUploadRouterConfiguration;
import com.jwebmp.plugins.angularfileupload.implementations.AngularFileUploadScanModule;

module com.jwebmp.angular.fileupload {
    uses com.jwebmp.plugins.angularfileupload.OnFileUploadInterceptor;
    exports com.jwebmp.plugins.angularfileupload;

    requires com.jwebmp.core.angular;
    requires guiced.vertx;
    requires io.vertx;
    requires com.jwebmp.core;
    requires static lombok;
    requires org.apache.commons.io;

    provides VertxRouterConfigurator with AngularFileUploadRouterConfiguration;
    provides IGuiceScanModuleInclusions with AngularFileUploadScanModule;

    opens com.jwebmp.plugins.angularfileupload to com.google.guice, com.fasterxml.jackson.databind, com.jwebmp.core;
    opens com.jwebmp.plugins.angularfileupload.implementations to com.google.guice, com.fasterxml.jackson.databind, com.jwebmp.core;
}