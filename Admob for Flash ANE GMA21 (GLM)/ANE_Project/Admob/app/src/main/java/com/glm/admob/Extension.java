package com.glm.admob;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class Extension implements FREExtension {
    public static ExtensionCalls context;
    @Override
    public FREContext createContext(String extId) {
        context = new ExtensionCalls();
        context.initialize();
        return context;
    }


    @Override
    public void dispose()
    {
        context.dispose();
        context = null;
    }

    @Override
    public void initialize()
    {

    }
}