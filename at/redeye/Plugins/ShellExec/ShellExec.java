package at.redeye.Plugins.ShellExec;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;

/******************************************************************************/
/*                                                                            */
/*                                                       FILE: ShellExec.java */
/*                                                                            */
/* Extremely simple class which "executes" a document                         */
/* ==================================================                         */
/*                                                                            */
/* V0.01   06-NOV-1999    Peter Tellenbach, http://www.heimetli.ch            */
/*                                                                            */
/* Copyright (c) 1999 Peter Tellenbach, Heimetli Software AG                  */
/* All rights reserved.                                                       */
/*                                                                            */
/* Redistribution and use in source and binary forms, with or without         */
/* modification, are permitted provided that the following conditions are     */
/* met:                                                                       */
/*                                                                            */
/* - Redistributions of source code must retain the above copyright notice,   */
/*   this list of conditions and the following disclaimer.                    */
/* - Redistributions in binary form must reproduce the above copyright        */
/*   notice, this list of conditions and the following disclaimer in the      */
/*   documentation and/or other materials provided with the distribution.     */
/* - Neither the names of Peter Tellenbach, Heimetli Software AG or any       */
/*   contributors may be used to endorse or promote products derived from     */
/*   this software without specific prior written permission.                 */
/*                                                                            */
/* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS        */
/* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED  */
/* TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR */
/* PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR          */
/* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,      */
/* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,        */
/* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR         */
/* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF     */
/* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING       */
/* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS         */
/* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.               */
/*                                                                            */
/******************************************************************************/
public class ShellExec {

    public static boolean library_loaded = false;
    
    public ShellExec()
    {
        init(this.getClass());
    }
    
    public native int execute(String document);

// Load our native library from PATH or CLASSPATH when this class is loaded.
    protected static void init(Class loader) {
        
        if( library_loaded )
            return;
        
        // The CLASSPATH searching code below was written by Jim McBeath
        // and contributed to the jRegistryKey project,
        // after which it was modified and used here.
        String libname = "HSWShellExec_" + System.getProperty("os.arch") + ".dll";

        try {
            System.loadLibrary("libname");
            library_loaded = true;
            return;
        } catch( UnsatisfiedLinkError ex ) {
            
        } catch( AccessControlException ex ) {
            
        }
                
        try {
            String appDir = System.getProperty("HSWShellExec_HOME");
            // allow application to specify our JNI location
            if (appDir != null) {
                // application told us to look in $JSHORTCUT_HOME for our dll
                File f = new File(appDir, libname);
                String path = f.getAbsolutePath();
                System.load(path);	// load JNI code
                library_loaded = true;
            } else {
                // No specified location for DLL, look through PATH
                System.loadLibrary("HSWShellExec");
                library_loaded = true;
            }
        } catch (UnsatisfiedLinkError ex) {
            // didn't find it in our PATH, look for it in CLASSPATH
            String cp = System.getProperty("java.class.path");
            boolean foundIt = false;
            while (cp.length() > 0) {
                int x = cp.indexOf(File.pathSeparator);
                String dir;
                if (x >= 0) {
                    dir = cp.substring(0, x);
                    cp = cp.substring(x + 1);
                } else {
                    dir = cp;
                    cp = "";
                }
                if (dir.length() > 4
                        && dir.substring(dir.length() - 4).toLowerCase().equals(".jar")) {
                    // If the classpath contains a jar file,
                    // then we look in the directory
                    // containing the jar file.
                    x = dir.lastIndexOf(File.separator);
                    if (x > 0) {
                        dir = dir.substring(0, x);
                    } else {
                        dir = ".";
                    }
                }
                File f = new File(dir, libname);
                if (f.exists()) {
                    String path = f.getAbsolutePath();
                    System.load(path);	// load JNI code
                    foundIt = true;
                    break;
                }
            }


            if (!foundIt) {
                try {                    

                    InputStream in = loader.getResourceAsStream("/at/redeye/Plugins/ShellExec/" + libname);
                    if (in == null) {
                        throw new Exception("libname: " + libname + " not found");
                    }
                    File tmplib = File.createTempFile("HSWShellExec-", ".dll");
                    tmplib.deleteOnExit();
                    OutputStream out = new FileOutputStream(tmplib);
                    byte[] buf = new byte[1024];
                    for (int len; (len = in.read(buf)) != -1;) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    System.load(tmplib.getAbsolutePath());
                    library_loaded = true;
                    foundIt = Boolean.TRUE;

                    System.out.println(libname + " loaded via tmp generated pathname: " + tmplib.getAbsolutePath());

                } catch (Exception e) {
                    foundIt = Boolean.FALSE;
                }
            }

            if (!foundIt) {
                // we did not find it in CLASSPATH
                System.out.println("failed loading jshortcut.dll");

                throw ex;	// throw the can't-find-library exception
            }
        }
    }
}
