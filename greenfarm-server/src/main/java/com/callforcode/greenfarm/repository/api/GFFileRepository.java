package com.callforcode.greenfarm.repository.api;

import java.io.File;
import java.util.Set;

public interface GFFileRepository {

    int upload(String url);

    File download(String url);
    
    Set<String> getUrl();
}
