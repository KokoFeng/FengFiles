package com.feng.files;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import java.io.File;
import java.io.FileNotFoundException;
public class ShareProvider extends ContentProvider {
 public boolean onCreate(){return true;}
 private File resolve(Uri u) throws FileNotFoundException {try {String p=u.getQueryParameter("path"); File f=new File(p).getCanonicalFile(); File root=android.os.Environment.getExternalStorageDirectory().getCanonicalFile(); if(!f.getPath().startsWith(root.getPath()+"/")&&!f.equals(root))throw new FileNotFoundException(); return f;}catch(Exception e){throw new FileNotFoundException();}}
 public String getType(Uri u){String t=android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(android.webkit.MimeTypeMap.getFileExtensionFromUrl(resolveQuiet(u)).toLowerCase());return t==null?"application/octet-stream":t;}
 private String resolveQuiet(Uri u){try{return resolve(u).getName();}catch(Exception e){return "";}}
 public Cursor query(Uri u,String[] projection,String selection,String[] args,String order){try{File f=resolve(u);MatrixCursor c=new MatrixCursor(new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE});c.addRow(new Object[]{f.getName(),f.length()});return c;}catch(Exception e){return null;}}
 public ParcelFileDescriptor openFile(Uri u,String mode)throws FileNotFoundException{return ParcelFileDescriptor.open(resolve(u),ParcelFileDescriptor.MODE_READ_ONLY);}
 public Uri insert(Uri u,ContentValues v){throw new UnsupportedOperationException();}
 public int delete(Uri u,String s,String[] a){return 0;}
 public int update(Uri u,ContentValues v,String s,String[] a){return 0;}
}