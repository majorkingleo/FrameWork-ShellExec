/******************************************************************************/
/*                                                                            */
/*                                                       FILE: HSWShellExec.c */
/*                                                                            */
/* C DLL which calls ShellExecute                                             */
/* ==============================                                             */
/*                                                                            */
/* Returns 1 on success, 0 on error                                           */
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

#include <windows.h>
#include <shellapi.h>
#include "ShellExec.h"

/**************************************************************************************/
 JNIEXPORT jint JNICALL Java_at_redeye_Plugins_ShellExec_ShellExec_execute( JNIEnv *e, jobject o, jstring command )
/**************************************************************************************/
{
 const jbyte *cmd = (*e)->GetStringUTFChars( e, command, NULL ) ;

 HINSTANCE inst = ShellExecute( 0, "open", (const char *)cmd, NULL, NULL, SW_SHOW ) ;

 (*e)->ReleaseStringUTFChars( e, command, cmd ) ;

 return( (UINT)inst > 32 ) ;
}
