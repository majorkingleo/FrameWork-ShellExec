REM cl /I d:\jdk1.3\include /I d:\jdk1.3\include\win32 -c jshortcut.cpp

REM link /nologo /incremental:no /fixed:no /nod /dll /release /machine:ix86 /out:..\..\jshortcut.dll /def:jshortcut.def jshortcut.obj advapi32.lib shell32.lib ole32.lib uuid.lib libcmt.lib kernel32.lib

REM erase ..\..\jshortcut.exp ..\..\jshortcut.lib

REM cl "-IC:/Program Files/Java/jdk1.6.0_21/include" "-IC:/Program Files/Java/jdk1.6.0_21/include/win32" -LD HSWShellExec.c -FeHSWShellExec_amd64.dll Advapi32.lib shell32.lib ole32.lib

REM setenv /x64
cl "-IC:/Program Files/Java/jdk1.6.0_21/include" "-IC:/Program Files/Java/jdk1.6.0_21/include/win32" -LD HSWShellExec.c -FeHSWShellExec_amd64.dll shell32.lib

REM setenv /x86
REM cl "-IC:/Program Files/Java/jdk1.6.0_21/include" "-IC:/Program Files/Java/jdk1.6.0_21/include/win32" -LD HSWShellExec.c -FeHSWShellExec_x86.dll shell32.lib

REM setenv /ia64
REM cl "-IC:/Program Files/Java/jdk1.6.0_21/include" "-IC:/Program Files/Java/jdk1.6.0_21/include/win32" -LD HSWShellExec.c -FeHSWShellExec_ia64.dll shell32.lib

del *.lib *.exp *.obj