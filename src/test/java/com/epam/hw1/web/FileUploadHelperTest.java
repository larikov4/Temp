package com.epam.hw1.web;

import com.epam.hw1.PathUtils;
import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.web.helper.FileUploadHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
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
    public void shouldReturnFalseWhenPassedFileIsNotExists() throws FileNotFoundException {
       assertFalse(helper.fillStorageFromJsonFile(new FileInputStream(new File(""))));
    }

    @Test
    public void shouldReturnTrueAndInvokeFacadeMethodsWhenPassedValidFile() throws FileNotFoundException {
        String absolutePath = PathUtils.getFileAbsolutePathFromClassPath("entities.json");
        assertFalse(helper.fillStorageFromJsonFile(new FileInputStream(new File(absolutePath))));

        verify(bookingFacade).createEvent(any());
        verify(bookingFacade).createUser(any());
    }

}
