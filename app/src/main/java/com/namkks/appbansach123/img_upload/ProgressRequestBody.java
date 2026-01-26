package com.namkks.appbansach123.img_upload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {

    private final RequestBody requestBody;
    private final ProgressListener listener;

    public ProgressRequestBody(RequestBody requestBody, ProgressListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long contentLength = contentLength();

        Sink forwardingSink = new ForwardingSink(sink) {
            long bytesWritten = 0;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                bytesWritten += byteCount;
                listener.onProgress(bytesWritten, contentLength);
            }
        };

        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }
}

