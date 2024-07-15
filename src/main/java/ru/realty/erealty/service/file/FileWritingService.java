package ru.realty.erealty.service.file;

import java.io.File;
import java.io.IOException;

public interface FileWritingService {

    void writeFile(File file) throws IOException;
}
