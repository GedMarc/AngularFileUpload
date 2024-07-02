package com.jwebmp.plugins.angularfileupload.implementations;

import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions;

import java.util.Set;

public class AngularFileUploadScanModule implements IGuiceScanModuleInclusions<AngularFileUploadScanModule>
{
    @Override
    public Set<String> includeModules()
    {
        return Set.of("com.jwebmp.angular.fileupload");
    }
}
