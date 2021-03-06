package org.thiki.kanban.foundation.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by xubt on 7/6/16.
 */
public class FileUtil {
    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    public static String readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\r\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

    /***
     * 覆盖原来的内容；
     *
     * @param filePath 文件的路径
     * @param content  保存的内容；
     * @return
     */
    public static boolean saveFile(String filePath, String content) {
        boolean successful = true;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(new File(filePath), false);
            fout.write(content.getBytes());
        } catch (FileNotFoundException e1) {
            successful = false;
        } catch (IOException e) {
            successful = false;
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                }
            }
        }
        return successful;
    }

    /**
     * 写入txt文件，可以在原文件内容的基础上追加内容(并判断目录是否存在，不存在则生成目录)
     *
     * @param fileName 写入文件内容
     * @param value    文件名字；
     * @throws IOException
     */
    public static void WriteFile(String fileName, String value) {
        File file = null;
        try {
            file = new File(fileName);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);
            out.write(value.getBytes("utf-8"));
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void forceMakeDirectory(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message = "File " + directory + " exists and is "
                        + "not a directory. Unable to addAcceptCriteria directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                // Double-check that some other thread or process hasn't made
                // the directory in the background
                if (!directory.isDirectory()) {
                    String message = "Unable to addAcceptCriteria directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: "
                            + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    public static boolean isFilesEqual(File firstFile, File secondFile) throws IOException {
        FileInputStream firstFileInputStream = new FileInputStream(firstFile);
        DataInputStream firstFileDataInputStream = new DataInputStream(firstFileInputStream);
        FileInputStream secondFileInputStream = new FileInputStream(secondFile);
        DataInputStream secondDataInputStream = new DataInputStream(secondFileInputStream);

        byte firstFileByte = firstFileDataInputStream.readByte();
        byte secondFileByte = secondDataInputStream.readByte();
        return firstFileByte == secondFileByte;
    }

    public static File convert(String filePath, MultipartFile multipartFile) throws IOException {
        FileUtil.forceMakeDirectory(new File(filePath));
        File file = new File(filePath + multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return file;
    }

    public static String urlResourceToString(UrlResource urlResource) throws IOException {
        InputStream inputStream = urlResource.getInputStream();
        return StringUtils.newStringUtf8(Base64.encodeBase64(IOUtils.toByteArray(inputStream), false));
    }

    public static String fileString(File file) throws IOException {
        UrlResource urlResource = new UrlResource(file.toURI());
        return urlResourceToString(urlResource);
    }
}
