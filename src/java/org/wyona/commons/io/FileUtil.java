package org.wyona.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import org.apache.log4j.Logger;

/**
 *
 */
public final class FileUtil {

    private static Logger log = Logger.getLogger(FileUtil.class);
    
    private static final int BUFFER_SIZE = 4096*4;

    /**
     * Returns a file by specifying an absolute directory name and a relative file name
     * 
     * @param absoluteDir DOCUMENT ME!
     * @param relativeFile DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public static File file(String absoluteDir, String relativeFile) {
        File file = new File(FilenameUtils.concat(absoluteDir, relativeFile));
        return new File(file.getAbsolutePath()); // INFO: Workaround in order to make sure Java recognizes it as absolute File
    }

    /**
     * Returns an absolute file name by specifying an absolute directory name and a relative file
     * name
     * 
     * @param absoluteFile DOCUMENT ME!
     * @param relativeFile DOCUMENT ME!
     * 
     * @return concatenated filename
     */
    public static String concat(String absoluteFile, String relativeFile) {
        File file = new File(absoluteFile);

        if (file.isFile()) {
            return FilenameUtils.concat(file.getParent(), relativeFile);
        }

        return FilenameUtils.concat(absoluteFile, relativeFile);
    }

    /**
     * If the given file has a relative path, resolve it relative to the given dir.
     * If dir is in fact a file, the resolving will use the parent dir of that file.  
     */
    public static File resolve(File absoluteFile, File relativeFile) {
        return new File(concat(absoluteFile.getPath(), relativeFile.getPath()));
    }

    /**
     *
     */
    static public boolean deleteDirectory(File file) {
        if(file.exists()) {
            File[] files = file.listFiles();
            for(int i = 0; i < files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return file.delete();
    }
    
    /**
    @param filter - array of names to not copy.
    */
    public static boolean copyDirectory(File from, File to, byte[] buffer, String[] filter)
    {
        //
        // System.out.println("copyDirectory("+from+","+to+")");        
    
        if (from == null)
            return false;
        if (!from.exists())
            return true;
        if (!from.isDirectory())
            return false;
    
        if (to.exists())
        {
            //          System.out.println(to + " exists");
            return false;
        }
        if (!to.mkdirs())
        {
            //          System.out.println("can't make" + to);
            return false;
        }           
    
        String[] list = from.list();
    
        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
    
            if (buffer == null)
                buffer = new byte[BUFFER_SIZE]; // reuse this buffer to copy files
    
    nextFile:   for (int i = 0; i < list.length; i++) {
    
                String fileName = list[i];
    
                if (filter != null) {
                    for (int j = 0; j < filter.length; j++) {
                        if (fileName.equals(filter[j]))
                            continue nextFile;
                    }
                }
    
    
                File entry = new File(from, fileName);
    
                //              System.out.println("\tcopying entry " + entry);
    
                if (entry.isDirectory())
                {
                    if (!copyDirectory(entry,new File(to,fileName),buffer,filter))
                        return false;
                }
                else
                {
                    if (!copyFile(entry,new File(to,fileName),buffer))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Copy file
     * @param from Existing source file
     * @param to Destination file
     * @param createDirectories Flag whether directories containing destination file should created when they don't exist yet
     * @return true when file was copied successfully and false otherwise
     */
    public static boolean copyFile(File from, File to, boolean createDirectories) {
        return copyFile(from, to, new byte[1024], createDirectories);
    }

    /**
     * Copy file
     * @param from Existing source file
     * @param to Destination file
     * @param buf Optional buffer, whereas if set to null, then a buffer with a certain default size will be used
     * @return true when file was copied successfully and false otherwise
     */
    public static boolean copyFile(File from, File to, byte[] buf) {
        return copyFile(from, to, buf, false);
    }

    /**
     * Copy file
     * @param from Existing source file
     * @param to Destination file
     * @param buf Optional buffer, whereas if set to null, then a buffer with a certain default size will be used
     * @param createDirectories Flag whether directories containing destination file should created when they don't exist yet
     * @return true when file was copied successfully and false otherwise
     */
    private static boolean copyFile(File from, File to, byte[] buf, boolean createDirectories) {
        if (buf == null) {
            buf = new byte[BUFFER_SIZE];
        }

        if (createDirectories && !to.getParentFile().isDirectory()) {
            to.getParentFile().mkdirs();
            log.warn("Directory '" + to.getParent() + "' has been created.");
        }

        log.debug("Copy file ("+from+","+to+")");
        FileInputStream from_s = null;
        FileOutputStream to_s = null;

        try {
            from_s = new FileInputStream(from);
            to_s = new FileOutputStream(to);

            for (int bytesRead = from_s.read(buf);
                 bytesRead != -1;
                 bytesRead = from_s.read(buf))
                to_s.write(buf,0,bytesRead);

            from_s.close();
            from_s = null;

            to_s.getFD().sync();  // RESOLVE: sync or no sync?
            to_s.close();
            to_s = null;
        }
        catch (IOException ioe) {
            return false;
        }
        finally
        {
            if (from_s != null)
            {
                try { from_s.close(); }
                catch (IOException ioe) {}
            }
            if (to_s != null)
            {
                try { to_s.close(); }
                catch (IOException ioe) {}
            }
        }

        return true;
    }
}
