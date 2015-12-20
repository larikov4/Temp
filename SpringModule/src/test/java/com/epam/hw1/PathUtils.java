package com.epam.hw1;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class is responsible for getting absolute path for files which is in classpath.
 *
 * @author Yevhen_Larikov
 */
public class PathUtils {

    private PathUtils(){

    }

    public static String getFileAbsolutePathFromClassPath(String path) throws URISyntaxException {
        String absolutePath = PathUtils.class.getClassLoader().getResource(path).getFile();
        return new URI(absolutePath).getPath();
    }
}
