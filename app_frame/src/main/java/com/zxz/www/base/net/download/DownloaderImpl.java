//package com.zxz.www.base.net.download;
//
//
//import android.util.Log;
//
//import com.zxz.www.base.app.BaseApp;
//import com.zxz.www.base.utils.FileUtil;
//
//import org.wlf.filedownloader.DownloadFileInfo;
//import org.wlf.filedownloader.FileDownloadConfiguration;
//import org.wlf.filedownloader.FileDownloader;
//import org.wlf.filedownloader.listener.OnDetectBigUrlFileListener;
//import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
//
///**
// * Created by igola on 2017/9/19.
// */
//
//public class DownloaderImpl extends Downloader{
//
//    private static final String TAG = "DownloaderImpl";
//
//    private static boolean IS_INIT = false;
//
//    private OnFileDownloadStatusListener mOnFileDownloadStatusListener;
//
//    public DownloaderImpl(String downloadUrl, String saveFileName) {
//        super(downloadUrl,saveFileName);
//        if (!IS_INIT) {
//            // 1.create FileDownloadConfiguration.Builder
//            FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(BaseApp.getContext());
//            builder.configFileDownloadDir(FileUtil.getInstance().getDownLoadPath());
//            FileDownloadConfiguration configuration = builder.build(); // build FileDownloadConfiguration with the builder
//            FileDownloader.init(configuration);
//            IS_INIT = true;
//        }
//        mOnFileDownloadStatusListener = new OnFileDownloadStatusListener() {
//            @Override
//            public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
//
//            }
//
//            @Override
//            public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
//
//            }
//
//            @Override
//            public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
//
//            }
//
//            @Override
//            public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {
//                if (mDownloadListener != null) {
//                    float downLoadTime = downloadFileInfo.getDownloadedSizeLong() / downloadSpeed / 1024;
//                    float faction =  downLoadTime/(downLoadTime + remainingTime);
//                    faction = Math.min(1, faction);
//                    mDownloadListener.onDownLoad(faction);
//                }
//            }
//
//            @Override
//            public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
//
//            }
//
//            @Override
//            public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
//                if (mDownloadListener != null) {
//                    mDownloadListener.onDownLoad(100);
//                }
//            }
//
//            @Override
//            public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
//
//            }
//        };
//    }
//
//    @Override
//    public void starDownload() {
//        FileDownloader.detect(mDownloadUrl, new OnDetectBigUrlFileListener() {
//            @Override
//            public void onDetectNewDownloadFile(String url, String fileName, String saveDir, long fileSize) {
//                FileDownloader.createAndStart(url, mFileDir, mFileDir);
//            }
//            @Override
//            public void onDetectUrlFileExist(String url) {
//                FileDownloader.start(url);
//            }
//            @Override
//            public void onDetectUrlFileFailed(String url, DetectBigUrlFileFailReason failReason) {
//                Log.i(TAG, failReason.toString());
//                if (mDownloadListener != null) {
//                    mDownloadListener.onDownLoad(-1);
//                }
//            }
//        });
//        FileDownloader.registerDownloadStatusListener(mOnFileDownloadStatusListener);
//    }
//
//    @Override
//    public void stopDownload() {
//        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);
//        FileDownloader.delete(mDownloadUrl, true, null);
//    }
//
//}
