package ru.mail.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.mail.cache.common.DefaultSettings;
import ru.mail.cache.common.ZipUtils;
import ru.mail.cache.invoker.FileInvoker;
import ru.mail.cache.service.Service;
import ru.mail.cache.service.ServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileInvoker.class, File.class, ZipUtils.class})
public class CacheProxyInFileTest {

    private static final String TEXT = "TEXT";
    private static final Date DATE = new Date();

    @Mock
    private File fileMock;

    @Mock
    private FileOutputStream fileOutputStreamMock;

    @Test
    public void inFileWithFullListSize() throws Exception {
        mockCreateFile();
        List list = invoke(getInFileMethod());
        assertEquals(list.size(), 50);
    }

    @Test
    public void inFileWithoutPrefix() throws Exception {
        mockCreateFile();
        invoke(getInFileMethod());
        PowerMockito.verifyNew(File.class, VerificationModeFactory.times(0))
                .withArguments(Mockito.contains("Cache-"));
    }

    @Test
    public void inFileWithPrefix() throws Exception {
        mockCreateFile();
        mockZip();
        invoke(getInFileZipPrefix());
        PowerMockito.verifyNew(File.class, VerificationModeFactory.times(2))
                .withArguments(Mockito.contains("Cache-"));
    }

    @Test
    public void inFileWithoutZip() throws Exception {
        mockCreateFile();
        invoke(getInFileMethod());
        PowerMockito.verifyNew(File.class, VerificationModeFactory.times(0))
                .withArguments(Mockito.contains(".zip"));
    }

    @Test
    public void inFileWithZip() throws Exception {
        mockCreateFile();
        mockZip();
        invoke(getInFileZipPrefix());
        PowerMockito.verifyStatic(ZipUtils.class);
        ZipUtils.zipFile(Mockito.anyString());
    }

    private Method getInFileMethod() throws NoSuchMethodException {
        return Service.class.getMethod("inFile", String.class, Date.class);
    }

    private Method getInFileZipPrefix() throws NoSuchMethodException {
        return Service.class.getMethod("inFileZipPrefix", String.class, Date.class);
    }

    private List invoke(Method method) throws IOException {
        Service proxy = new ServiceImpl();
        return (List)new FileInvoker().invoke(proxy, method, new Object[]{TEXT, DATE}, new DefaultSettings());
    }

    private void mockCreateFile() throws Exception {
        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(fileMock);
        PowerMockito.whenNew(FileOutputStream.class).withAnyArguments().thenReturn(fileOutputStreamMock);
        Mockito.when(fileMock.delete()).thenReturn(true);
    }

    private void mockZip() {
        PowerMockito.mockStatic(ZipUtils.class);
    }
}
