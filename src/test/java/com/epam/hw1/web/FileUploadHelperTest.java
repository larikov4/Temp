package com.epam.hw1.web;

import com.epam.hw1.PathUtils;
import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.web.helper.FileUploadHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class FileUploadHelperTest {
    public static final String EXCEPTION_MESSAGE = "exception message";

    @Mock
    private BookingFacade bookingFacade;

    @InjectMocks
    private FileUploadHelper helper = new FileUploadHelper();

    @Mock
    private InputStream inputStream;

    @Test
    public void shouldReturnFalseWhenPassedParameterIsNull(){
       assertFalse(helper.fillStorageFromJsonFile(null));
    }

    @Test
    public void shouldReturnFalseWhenPassedFileIsClosed() throws IOException, URISyntaxException {
        String absolutePath = PathUtils.getFileAbsolutePathFromClassPath("entities.json");
        FileInputStream fileInputStream = new FileInputStream(new File(absolutePath));
        fileInputStream.close();
        assertFalse(helper.fillStorageFromJsonFile(fileInputStream));
    }

    @Test
    public void shouldReturnTrueAndInvokeFacadeMethodsWhenPassedValidFile() throws FileNotFoundException, URISyntaxException {
        String absolutePath = PathUtils.getFileAbsolutePathFromClassPath("entities.json");
        assertTrue(helper.fillStorageFromJsonFile(new FileInputStream(new File(absolutePath))));

        verify(bookingFacade).createEvent(any());
        verify(bookingFacade).createUser(any());
    }

}
