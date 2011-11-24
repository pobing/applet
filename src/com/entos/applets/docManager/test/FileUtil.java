package com.entos.applets.docManager.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * �� ��: 2008-2-14 12:05:18<br />
 * project: zxj <br />
 * �� �ߣ�wangmingjie<br />
 */
public class FileUtil {
    private static Log log = LogFactory.getLog(FileUtil.class);
    /**
     * ���������ļ��С�
     * 
     * @param dir
     * @param ignoreIfExitst
     *            true ��ʾ����ļ��д��ھͲ��ٴ����ˡ�false�����´�����
     * @throws IOException
     */
    public static void createDir(String dir, boolean ignoreIfExitst)
            throws IOException {
        File file = new File(dir);
        if (ignoreIfExitst && file.exists()) {
            return;
        }
        if (file.mkdir() == false) {
            throw new IOException("Cannot create the directory = " + dir);
        }
    }
    /**
     * ��������ļ���
     * 
     * @param dir
     * @param ignoreIfExitst
     * @throws IOException
     */
    public static void createDirs(String dir, boolean ignoreIfExitst)
            throws IOException {
        File file = new File(dir);
        if (ignoreIfExitst && file.exists()) {
            return;
        }
        if (file.mkdirs() == false) {
            throw new IOException("Cannot create directories = " + dir);
        }
    }
    /**
     * ɾ��һ���ļ���
     * 
     * @param filename
     * @throws IOException
     */
    public static void deleteFile(String filename) throws IOException {
        File file = new File(filename);
        log.trace("Delete file = " + filename);
        if (file.isDirectory()) {
            throw new IOException(
                    "IOException -> BadInputException: not a file.");
        }
        if (file.exists() == false) {
            throw new IOException(
                    "IOException -> BadInputException: file is not exist.");
        }
        if (file.delete() == false) {
            throw new IOException("Cannot delete file. filename = " + filename);
        }
    }
    /**
     * ɾ���ļ��м�����������ļ���
     * 
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile())
            throw new IOException(
                    "IOException -> BadInputException: not a directory.");
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }// if 
        dir.delete();
    }
    public static String getPathSeparator() {
        return java.io.File.pathSeparator;
    }
    public static String getFileSeparator() {
        return java.io.File.separator;
    }
    /**
     * �г�ָ���ļ�Ŀ¼������ļ���Ϣ��
     * 
     * @param dir
     * @return
     * @throws IOException
     */
//    public static List<FileInfo> getFiles(File dir) throws IOException {
//        if (dir.isFile())
//            throw new IOException("BadInputException: not a directory.");
//        if (!dir.exists()) {
//            throw new IOException(" don't exist ");
//        }
//        File[] files = dir.listFiles();
//        int LEN = 0;
//        if (files != null) {
//            LEN = files.length;
//        }
//        List<FileInfo> l = new ArrayList<FileInfo>();
//        long tempFLen = 0; //�ļ����� 
//        for (int i = 0; i < LEN; i++) {
//            FileInfo temp = new FileInfo();
//            temp.setFileName(files[i].getName());
//            temp.setIsDir(files[i].isDirectory());
//            //���ļ����Ұ���. 
//            if (files[i].isFile()) {
//                if(files[i].getName().lastIndexOf(".")!=-1)
//                temp.setFileType(files[i].getName().substring(
//                        files[i].getName().lastIndexOf(".")));
//            }else{
//                temp.setFileType("�ļ���");
//            }
//            tempFLen = files[i].length();
//            temp.setFileLen(tempFLen);
//            if(tempFLen/1024/1024/1024 >0){
//                temp.setFileLength(files[i].length() / 1024/1024/1024 + "G");
//            }else if(tempFLen/1024/1024 >0){
//                temp.setFileLength(files[i].length() / 1024/1024 + "M");
//            }else if(tempFLen/1024 >0){
//                temp.setFileLength(files[i].length() / 1024 + "K");
//            }else{
//                temp.setFileLength(tempFLen+"byte");
//            }
//            
//            temp.setFilePath(files[i].getAbsolutePath().replaceAll("[\\\\]", "/"));
//            temp.setLastModifiedTime(com.work.util.DateUtil
//                    .getDateTime(files[i].lastModified()));
//            temp.setIsHidden(files[i].isHidden());
//            temp.setAuthor(null);
//            temp.setVersion(null);
//            temp.setFileClass(null);
//            temp.setRemark(null);
//            l.add(temp);
//        }
//        return l;
//    }
    /**
     * ��ȡ��Ŀ¼�����ļ��Ĵ�С����������Ŀ¼��
     * 
     * @param dir
     * @return
     * @throws IOException
     */
    public static long getDirLength(File dir) throws IOException {
        if (dir.isFile())
            throw new IOException("BadInputException: not a directory.");
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                // file.getName(); 
                // System.out.println(file.getName()); 
                long length = 0;
                if (file.isFile()) {
                    length = file.length();
                } else {
                    length = getDirLength(file);
                }
                size += length;
            }// for 
        }// if 
        return size;
    }
    /**
     * ���ļ���ա�
     * 
     * @param srcFilename
     * @throws IOException
     */
    public static void emptyFile(String srcFilename) throws IOException {
        File srcFile = new File(srcFilename);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the file: "
                    + srcFile.getAbsolutePath());
        }
        if (!srcFile.canWrite()) {
            throw new IOException("Cannot write the file: "
                    + srcFile.getAbsolutePath());
        }
        FileOutputStream outputStream = new FileOutputStream(srcFilename);
        outputStream.close();
    }
    /**
     * Write content to a fileName with the destEncoding д�ļ���������ļ������ھʹ���һ����
     * 
     * @param content
     *            String
     * @param fileName
     *            String
     * @param destEncoding
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeFile(String content, String fileName,
            String destEncoding) throws FileNotFoundException, IOException {
        File file = null;
        try {
            file = new File(fileName);
            if (!file.exists()) {
                if (file.createNewFile() == false) {
                    throw new IOException("create file '" + fileName
                            + "' failure.");
                }
            }
            if (file.isFile() == false) {
                throw new IOException("'" + fileName + "' is not a file.");
            }
            if (file.canWrite() == false) {
                throw new IOException("'" + fileName + "' is a read-only file.");
            }
        } finally {
            // we dont have to close File here 
        }
        BufferedWriter out = null;
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            out = new BufferedWriter(new OutputStreamWriter(fos, destEncoding));
            out.write(content);
            out.flush();
        } catch (FileNotFoundException fe) {
            log.error("Error", fe);
            throw fe;
        } catch (IOException e) {
            log.error("Error", e);
            throw e;
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException ex) {
            }
        }
    }
    /**
     * ��ȡ�ļ������ݣ������ļ��������ַ�������ʽ���ء�
     * 
     * @param fileName
     * @param srcEncoding
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readFile(String fileName, String srcEncoding)
            throws FileNotFoundException, IOException {
        File file = null;
        try {
            file = new File(fileName);
            if (file.isFile() == false) {
                throw new IOException("'" + fileName + "' is not a file.");
            }
        } finally {
            // we dont have to close File here 
        }
        BufferedReader reader = null;
        try {
            StringBuffer result = new StringBuffer(1024);
            FileInputStream fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, srcEncoding));
            char[] block = new char[512];
            while (true) {
                int readLength = reader.read(block);
                if (readLength == -1)
                    break;// end of file 
                result.append(block, 0, readLength);
            }
            return result.toString();
        } catch (FileNotFoundException fe) {
            log.error("Error", fe);
            throw fe;
        } catch (IOException e) {
            log.error("Error", e);
            throw e;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
            }
        }
    }
    /*
     * 1 ABC 2 abC Gia su doc tu dong 1 lay ca thay 5 dong => 1 --> 5 3 ABC
     */
    public static String[] getLastLines(File file, int linesToReturn)
            throws IOException, FileNotFoundException {
        final int AVERAGE_CHARS_PER_LINE = 250;
        final int BYTES_PER_CHAR = 2;
        RandomAccessFile randomAccessFile = null;
        StringBuffer buffer = new StringBuffer(linesToReturn
                * AVERAGE_CHARS_PER_LINE);
        int lineTotal = 0;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            long byteTotal = randomAccessFile.length();
            long byteEstimateToRead = linesToReturn * AVERAGE_CHARS_PER_LINE
                    * BYTES_PER_CHAR;
            long offset = byteTotal - byteEstimateToRead;
            if (offset < 0) {
                offset = 0;
            }
            randomAccessFile.seek(offset);
            // log.debug("SKIP IS ::" + offset); 
            String line = null;
            String lineUTF8 = null;
            while ((line = randomAccessFile.readLine()) != null) {
                lineUTF8 = new String(line.getBytes("ISO8859_1"), "UTF-8");
                lineTotal++;
                buffer.append(lineUTF8).append("\n");
            }
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException ex) {
                }
            }
        }
        String[] resultLines = new String[linesToReturn];
        BufferedReader in = null;
        try {
            in = new BufferedReader(new StringReader(buffer.toString()));
            int start = lineTotal /* + 2 */- linesToReturn; // Ex : 55 - 10 = 45 
            // ~ offset 
            if (start < 0)
                start = 0; // not start line 
            for (int i = 0; i < start; i++) {
                in.readLine(); // loop until the offset. Ex: loop 0, 1 ~~ 2 
                // lines 
            }
            int i = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                resultLines[i] = line;
                i++;
            }
        } catch (IOException ie) {
            log.error("Error" + ie);
            throw ie;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }
        return resultLines;
    }
    /**
     * �����ļ�������
     * 
     * @param srcFilename
     * @param destFilename
     * @param overwrite
     * @throws IOException
     */
    public static void copyFile(String srcFilename, String destFilename,
            boolean overwrite) throws IOException {
        File srcFile = new File(srcFilename);
        // �����ж�Դ�ļ��Ƿ���� 
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the source file: "
                    + srcFile.getAbsolutePath());
        }
        // �ж�Դ�ļ��Ƿ�ɶ� 
        if (!srcFile.canRead()) {
            throw new IOException("Cannot read the source file: "
                    + srcFile.getAbsolutePath());
        }
        File destFile = new File(destFilename);
        if (overwrite == false) {
            // Ŀ���ļ����ھͲ����� 
            if (destFile.exists())
                return;
        } else {
            // ���Ҫ�����Ѿ����ڵ�Ŀ���ļ��������ж��Ƿ�Ŀ���ļ���д�� 
            if (destFile.exists()) {
                if (!destFile.canWrite()) {
                    throw new IOException("Cannot write the destination file: "
                            + destFile.getAbsolutePath());
                }
            } else {
                // �����ھʹ���һ���µĿ��ļ��� 
                if (!destFile.createNewFile()) {
                    throw new IOException("Cannot write the destination file: "
                            + destFile.getAbsolutePath());
                }
            }
        }
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        byte[] block = new byte[1024];
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(
                    destFile));
            while (true) {
                int readLength = inputStream.read(block);
                if (readLength == -1)
                    break;// end of file 
                outputStream.write(block, 0, readLength);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }
        }
    }
    /**
     * �����ļ�������
     * 
     * @param srcFile
     * @param destFile
     * @param overwrite
     *            �Ƿ񸲸�Ŀ���ļ�
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile, boolean overwrite)
            throws IOException {
        // �����ж�Դ�ļ��Ƿ���� 
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the source file: "
                    + srcFile.getAbsolutePath());
        }
        // �ж�Դ�ļ��Ƿ�ɶ� 
        if (!srcFile.canRead()) {
            throw new IOException("Cannot read the source file: "
                    + srcFile.getAbsolutePath());
        }
        if (overwrite == false) {
            // Ŀ���ļ����ھͲ����� 
            if (destFile.exists())
                return;
        } else {
            // ���Ҫ�����Ѿ����ڵ�Ŀ���ļ��������ж��Ƿ�Ŀ���ļ���д�� 
            if (destFile.exists()) {
                if (!destFile.canWrite()) {
                    throw new IOException("Cannot write the destination file: "
                            + destFile.getAbsolutePath());
                }
            } else {
                // �����ھʹ���һ���µĿ��ļ��� 
                if (!destFile.createNewFile()) {
                    throw new IOException("Cannot write the destination file: "
                            + destFile.getAbsolutePath());
                }
            }
        }
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        byte[] block = new byte[1024];
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(
                    destFile));
            while (true) {
                int readLength = inputStream.read(block);
                if (readLength == -1)
                    break;// end of file 
                outputStream.write(block, 0, readLength);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }
        }
    }
    /**
     * �����ļ�����Դ�ļ��п����ļ���Ŀ���ļ��С� <br>
     * ����Դ�ļ��к�Ŀ���ļ��У���󶼲�Ҫ���ļ�·�����ţ����磺c:/aa��ȷ��c:/aa/����
     * 
     * @param srcDirName
     *            Դ�ļ������� ,���磺c:/test/aa ����c:\\test\\aa
     * @param destDirName
     *            Ŀ���ļ�������,���磺c:/test/aa ����c:\\test\\aa
     * @param overwrite
     *            �Ƿ񸲸�Ŀ���ļ���������ļ���
     * @throws IOException
     */
    public static void copyFiles(String srcDirName, String destDirName,
            boolean overwrite) throws IOException {
        File srcDir = new File(srcDirName);// ����Դ�ļ��� 
        // �����ж�Դ�ļ����Ƿ���� 
        if (!srcDir.exists()) {
            throw new FileNotFoundException(
                    "Cannot find the source directory: "
                            + srcDir.getAbsolutePath());
        }
        File destDir = new File(destDirName);
        if (overwrite == false) {
            if (destDir.exists()) {
                // do nothing 
            } else {
                if (destDir.mkdirs() == false) {
                    throw new IOException(
                            "Cannot create the destination directories = "
                                    + destDir);
                }
            }
        } else {
            // ���Ǵ��ڵ�Ŀ���ļ��� 
            if (destDir.exists()) {
                // do nothing 
            } else {
                // create a new directory 
                if (destDir.mkdirs() == false) {
                    throw new IOException(
                            "Cannot create the destination directories = "
                                    + destDir);
                }
            }
        }
        // ѭ������Դ�ļ���Ŀ¼������ļ����������ļ��У���Ȼ���俽����ָ����Ŀ���ļ������档 
        File[] srcFiles = srcDir.listFiles();
        if (srcFiles == null || srcFiles.length < 1) {
            // throw new IOException ("Cannot find any file from source 
            // directory!!!"); 
            return;// do nothing 
        }
        // ��ʼ�����ļ� 
        int SRCLEN = srcFiles.length;
        for (int i = 0; i < SRCLEN; i++) {
            // File tempSrcFile = srcFiles[i]; 
            File destFile = new File(destDirName + File.separator
                    + srcFiles[i].getName());
            // ע�⹹���ļ�����ʱ���ļ����ַ����в��ܰ����ļ�·���ָ���";". 
            // log.debug(destFile); 
            if (srcFiles[i].isFile()) {
                copyFile(srcFiles[i], destFile, overwrite);
            } else {
                // ��������еݹ���ã��Ϳ���ʵ�����ļ��еĿ��� 
                copyFiles(srcFiles[i].getAbsolutePath(), destDirName
                        + File.separator + srcFiles[i].getName(), overwrite);
            }
        }
    }
    /**
     * ѹ���ļ���ע�⣺�����ļ����ƺ����ĵ����ۻ����롣
     * @param srcFilename
     * @param destFilename
     * @param overwrite
     * @throws IOException
     */
    public static void zipFile(String srcFilename, String destFilename,
            boolean overwrite) throws IOException {
        
        File srcFile = new File(srcFilename);
        // �����ж�Դ�ļ��Ƿ���� 
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the source file: "
                    + srcFile.getAbsolutePath());
        }
        // �ж�Դ�ļ��Ƿ�ɶ� 
        if (!srcFile.canRead()) {
            throw new IOException("Cannot read the source file: "
                    + srcFile.getAbsolutePath());
        }
        if(destFilename==null || destFilename.trim().equals("")){
            destFilename = srcFilename+".zip";
        }else{
            destFilename += ".zip";
        }
        File destFile = new File(destFilename);
        if (overwrite == false) {
            // Ŀ���ļ����ھͲ����� 
            if (destFile.exists())
                return;
        } else {
            // ���Ҫ�����Ѿ����ڵ�Ŀ���ļ��������ж��Ƿ�Ŀ���ļ���д�� 
            if (destFile.exists()) {
                if (!destFile.canWrite()) {
                    throw new IOException("Cannot write the destination file: "
                            + destFile.getAbsolutePath());
                }
            } else {
                // �����ھʹ���һ���µĿ��ļ��� 
                if (!destFile.createNewFile()) {
                    throw new IOException("Cannot write the destination file: "
                            + destFile.getAbsolutePath());
                }
            }
        }
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        byte[] block = new byte[1024];
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
            zipOutputStream = new ZipOutputStream(outputStream);
            
            zipOutputStream.setComment("ͨ��java����ѹ����");
            ZipEntry  zipEntry = new ZipEntry(srcFile.getName());
            zipEntry.setComment(" zipEntryͨ��java����ѹ����");
            zipOutputStream.putNextEntry(zipEntry);
            while (true) {
                int readLength = inputStream.read(block);
                if (readLength == -1)
                    break;// end of file 
                zipOutputStream.write(block, 0, readLength);
            }
            zipOutputStream.flush();
            zipOutputStream.finish();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException ex) {
                    // just ignore 
                }
            }           
        }
    }
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileUtil.createDirs("d:/logs/aaaaaaaa/spring.log", false);
        FileUtil.zipFile("d:/logs/���� work.log", null, true);
        System.out.println(getFileSeparator());
        String[] temp = FileUtil.getLastLines(new File("d:/logs/work.log"), 5);
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }
    }
}