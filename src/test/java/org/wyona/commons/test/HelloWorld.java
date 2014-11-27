package org.wyona.commons.test;

import org.wyona.commons.io.FileUtil;
import org.wyona.commons.io.Path;
import org.wyona.commons.io.PathUtil;

import java.io.File;

/**
 *
 */
public class HelloWorld {

    /**
     *
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Path path = new Path("/hello/world.txt");
        System.out.println("Path: " + path);
        System.out.println("Parent: " + path.getParent());
        System.out.println("Parent of parent: " + path.getParent().getParent());
        System.out.println("Parent of parent of parent: " + path.getParent().getParent().getParent());

        System.out.println("Concat with PathUtil: " + PathUtil.concat("/home/user/tmp", "../../"));
        System.out.println("Concat with PathUtil: " + PathUtil.concat("/home/user/tmp/", "../../hugo.txt"));

        System.out.println("Concat with FileUtil: " + FileUtil.concat("/home/user/tmp", "../.."));
        System.out.println("Resolve with FileUtil: " + FileUtil.resolve(new File("/home/user/tmp"), new File("../..")));
        System.out.println("Resolve with FileUtil: " + FileUtil.resolve(new File("/home/user/tmp/"), new File("../../hugo.txt")));

        System.out.println("Name without suffix: \"" + PathUtil.getNameWithoutSuffix("/foo/bar.txt") + "\"");
        System.out.println("Name without suffix: \"" + PathUtil.getNameWithoutSuffix("/foo/.txt") + "\"");
        System.out.println("Name without suffix: \"" + PathUtil.getNameWithoutSuffix("/foo/bar") + "\"");



        //String excludes = null;
        //String excludes = ".Trashes";
        String excludes = ".Trashes,.Spotlight-V100,.fseventsd,MANUALS,SOFTWARE,LACIE,Cube Backup,.TemporaryItems,.com.apple.timemachine.donotpresent,Littledisk_713519.dbd,Littledisk_713519.txt,Harddisk-dbnp_713520.dbd,Harddisk-dbnp_713520.txt,Desktop DF,Desktop DB,.DS_Store,LaCie.ini,Render Files"; // INFO: Comma separated list of files and diretories to be excluded from synchronization. Also see http://hostilefork.com/2009/12/02/trashes-fseventsd-and-spotlight-v100/
        boolean ignoreHidden = true;
        String source = "/Volumes/Apache Oakland 2009 Original";
        String destination = "/Volumes/Apache Oakland 2009 Original_2";
        System.out.println("Try to sync the two directories '" + source + "' and '" + destination + "' ...");
        new org.wyona.commons.io.Sync().synchronize(new File(source), new File(destination), excludes, ignoreHidden);
        //new org.wyona.commons.io.Sync().synchronize(new File("/Volumes/Cube Backup"), new File("/Volumes/Apache Oakland 2009 Original"), excludes, ignoreHidden);
    }
}
