package com.epam.hw1.web.controller;

import com.epam.hw1.web.helper.FileUploadHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest {

    @Mock
    private FileUploadHelper helper;

    @Mock
    private MultipartFile file;

    @Mock
    private InputStream inputStream;

    @InjectMocks
    private FileUploadController controller;

    @Test
    public void shouldReturnUploadFileView(){
        assertEquals(FileUploadController.UPLOAD_FILE_VIEW, controller.uploadForm().getViewName());
    }

    @Test
    public void shouldReturnExceptionViewWhenFileIsEmpty() throws IOException {
        when(file.isEmpty()).thenReturn(true);

        assertEquals(FileUploadController.EXCEPTION_VIEW, controller.handleFormUpload(file).getViewName());
    }

    @Test
    public void shouldReturnSuccessViewAndInvokeHelper() throws IOException {
        when(file.isEmpty()).thenReturn(false);
        when(file.getInputStream()).thenReturn(inputStream);

        assertEquals(FileUploadController.SUCCESS_VIEW, controller.handleFormUpload(file).getViewName());
        verify(helper).fillStorageFromJsonFile(inputStream);
    }
}
