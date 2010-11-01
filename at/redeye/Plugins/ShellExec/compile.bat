REM cl /I d:\jdk1.3\include /I d:\jdk1.3\include\win32 -c jshortcut.cpp

REM link /nologo /incremental:no /fixed:no /nod /dll /release /machine:ix86 /out:..\..\jshortcut.dll /def:jshortcut.def jshortcut.obj advapi32.lib shell32.lib ole32.lib uuid.lib libcmt.lib kernel32.lib

REM erase ..\..\jshortcut.exp ..\..\jshortcut.lib

cl "-IC:/Program Files/Java/jdk1.6.0_21/include" "-IC:/Program Files/Java/jdk1.6.0_21/include/win32" -LD jshortcut.cpp -Fejshortcut_amd64.dll Advapi32.lib shell32.lib ole32.lib
