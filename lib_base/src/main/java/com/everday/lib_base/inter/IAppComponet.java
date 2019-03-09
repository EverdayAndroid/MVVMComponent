package com.everday.lib_base.inter;

import android.app.Application;

public interface IAppComponet {
    void initAppliction(Application application);

    void initializationFailed(Object obj);
}
