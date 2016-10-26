package com.milkytech.service;

import java.io.File;

import com.milkytech.engine2.ModelDelegate;

public interface ModelParser {
    ModelDelegate parseModel(String dsl);

    ModelDelegate parseModel(File file);
}
