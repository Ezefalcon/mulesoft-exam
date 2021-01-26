package service;


import domain.Directory;
import domain.File;
import org.junit.Before;
import org.junit.Test;
import service.exceptions.CharacterLimitExceededException;
import service.exceptions.DirectoryAlreadyExistsException;
import service.exceptions.DirectoryNotFoundException;
import service.exceptions.FileAlreadyExistsException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommandProcessorImplTest {

    Directory mainDirectory;

    CommandProcessor commandProcessor;

    @Before
    public void init() {
        mainDirectory = new Directory("mulesoft");
        Directory src = new Directory("src");
        src.add(new File("main.java"));
        Directory java = new Directory("java");
        src.add(java);
        mainDirectory.add(src);

        commandProcessor = new CommandProcessorImpl(mainDirectory);
    }

    @Test
    public void pwd_shouldOutputCurrentDirectory() {
        String pwd = commandProcessor.pwd();
        assertEquals("mulesoft", pwd);
    }

    @Test
    public void ls_shouldOutputAllFilesAndDirectoriesOrdered() {
        List<String> directoriesAndFiles = commandProcessor.ls("-r");
        assertEquals("mulesoft", directoriesAndFiles.get(0));
        assertEquals("mulesoft/src", directoriesAndFiles.get(1));
        assertEquals("main.java", directoriesAndFiles.get(2));
        assertEquals("mulesoft/src/java", directoriesAndFiles.get(3));
    }

    @Test
    public void ls_shouldOutputFilesAndDirectoriesOfCurrentDir() {
        List<String> directoriesAndFiles = commandProcessor.ls("");
        assertEquals("src", directoriesAndFiles.get(0));
    }

    @Test
    public void mkdir_shouldCreateANewDirectory() {
        CommandProcessor commandProcessor2 = new CommandProcessorImpl(new Directory("root2"));
        commandProcessor2.mkdir("asdf");
        List<String> ls = commandProcessor2.ls("");
        assertEquals(1, ls.size());
    }

    @Test(expected = DirectoryAlreadyExistsException.class)
    public void mkdir_shouldThrowDirectoryAlreadyExistsException() {
        CommandProcessor commandProcessor2 = new CommandProcessorImpl(new Directory("root2"));
        commandProcessor2.mkdir("asdf");
        commandProcessor2.mkdir("asdf");
    }

    @Test
    public void cd_shouldMoveToDirectory() {
        commandProcessor.cd("src");
        String pwd = commandProcessor.pwd();
        assertEquals("mulesoft/src", pwd);
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void cd_shouldThrowDirectoryNotFoundException() {
        commandProcessor.cd("asdasd");
    }

    @Test
    public void touch_shouldCreateFile() {
        CommandProcessor commandProcessor2 = new CommandProcessorImpl(new Directory("root2"));
        commandProcessor2.touch("asdf");
        assertEquals(commandProcessor2.ls("").size(), 1);
    }

    @Test(expected = CharacterLimitExceededException.class)
    public void touch_shouldThrowCharacterLimitExceededException() {
        commandProcessor.touch("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
    }


    @Test(expected = FileAlreadyExistsException.class)
    public void touch_shouldThrowFileAlreadyExists() {
        CommandProcessor commandProcessor2 = new CommandProcessorImpl(new Directory("root2"));
        commandProcessor2.touch("asdf");
        commandProcessor2.touch("asdf");
    }
}
