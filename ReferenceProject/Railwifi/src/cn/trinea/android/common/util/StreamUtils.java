package cn.trinea.android.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

	public static void CopyStream(InputStream is, OutputStream os) throws IOException {
		// TODO Auto-generated method stub
		int c = 0;
		while((c = is.read())!=-1){
			os.write(c);
		}
		
	}

}
