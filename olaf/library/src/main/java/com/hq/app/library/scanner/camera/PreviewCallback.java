package com.hq.app.library.scanner.camera;

import com.hq.app.library.scanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
}
