/* Copyright (c) 2014, Julian Uy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.uyjulian.minecraft.XrayMod;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class XrayModUpdateChecker implements Runnable {
	private final String UpdateURL = "http://pastebin.com/raw.php?i=341fB8ZD";
	private final String CurrentVersion = UyjuliansXrayModMain.currentVersion;
	@Override
	public void run() {
		Boolean foundNewVersion = false;
		while (foundNewVersion == false) {
			InputStream currentInputStream = null;
			ByteArrayOutputStream currentByteArrayOutputStream = null;
			try {
				URL currentURL = new URL(UpdateURL);
				currentInputStream = currentURL.openStream();
				currentByteArrayOutputStream = new ByteArrayOutputStream();
				copy(currentInputStream, currentByteArrayOutputStream);
				String NewVersion = new String(currentByteArrayOutputStream.toByteArray()).trim();
				if (!(NewVersion.equals(CurrentVersion)) && (NewVersion.length() <= 100)) {
					UyjuliansXrayModMain.putLineInChat("§c§lUpdate available§r");
					foundNewVersion = true;
					
				}
				Thread.sleep((1000 * 60) * 2);
			}
			catch (Exception currentException) {
				currentException.printStackTrace();
			}
			finally {
				closeQuietly(currentInputStream);
			}
		}
	}
	
	private static long copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
	{
		byte[] arrayOfByte = new byte[4096];
		long l = 0L;
		int i = 0;
		while ((i = paramInputStream.read(arrayOfByte)) != -1)
		{
			paramOutputStream.write(arrayOfByte, 0, i);
			l += i;
		}
		return l;
	}

	private static void closeQuietly(Closeable paramCloseable)
	{
		try
		{
			if (paramCloseable != null)
				paramCloseable.close();
		}
		catch (Exception currentException){}
	}

}
