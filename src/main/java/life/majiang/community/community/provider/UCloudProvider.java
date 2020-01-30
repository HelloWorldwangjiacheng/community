package life.majiang.community.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.proxySuffix}")
    private String proxySuffix;

    @Value("${ucloud.ufile.expires}")
    private Integer expires;
    // Bucket相关API的授权器
//    BucketAuthorization BUCKET_AUTHORIZER = new UfileBucketLocalAuthorization(publicKey, privateKey);


    public String upload(InputStream fileStream, String mimeType, String fileName) {

        String generatedFileName = "";
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }

        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            ObjectConfig config = new ObjectConfig(region, proxySuffix);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
            if (response != null && response.getRetCode() == 0){
                String url = UfileClient.object(objectAuthorization,config)
                        .getDownloadUrlFromPrivateBucket(generatedFileName,bucketName,expires).createUrl();
                return url;
            }else{
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }

    }

}
