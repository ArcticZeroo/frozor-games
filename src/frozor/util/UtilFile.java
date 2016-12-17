package frozor.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class UtilFile {
    public static void deleteDirectory(File file){
        if(!file.isDirectory()) return;
        deleteDirectoryFiles(file);

        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDirectoryFiles(File file){
        if(!file.isDirectory()) return;

        File[] files = file.listFiles();
        for(File dirFile : files){
            if(dirFile.isDirectory()){
                deleteDirectoryFiles(dirFile);
            }

            try {
                FileUtils.forceDelete(dirFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
