package com.uphyca.stetho_realm;

import com.facebook.stetho.inspector.database.DatabaseFilesProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RealmFilesProvider implements DatabaseFilesProvider {
    private final File folder;
    private final Pattern databaseNamePattern;

    public RealmFilesProvider(File folder, Pattern databaseNamePattern) {
        this.folder = folder;
        this.databaseNamePattern = databaseNamePattern;
    }

    @Override
    public List<File> getDatabaseFiles() {
		List<File> files = new ArrayList<>();
		findRealmFiles(folder, databaseNamePattern, files);
		return files;
    }

	public static void findRealmFiles(File baseDir, Pattern pattern, List fileList) {
		String tempName = null;
		String[] filelist = baseDir.list();
		for (int i = 0; i < filelist.length; i++) {
			File readfile = new File(baseDir, filelist[i]);
			if(readfile.isDirectory()) {
				findRealmFiles(readfile, pattern, fileList);
			} else if(readfile.isFile()){
				tempName =  readfile.getName();
				if (pattern.matcher(tempName).matches()) {
					fileList.add(readfile.getAbsoluteFile());
				}
			}
		}
	}
}
