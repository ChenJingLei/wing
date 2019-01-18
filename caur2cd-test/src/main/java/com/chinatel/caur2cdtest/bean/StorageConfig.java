package com.chinatel.caur2cdtest.bean;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.chinatel.caur2cdtest.properties.OOSCtyunProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:oos-ctyun-credentials.properties")
@Configuration
public class StorageConfig {

    private final OOSCtyunProperties oosCtyunProperties;

    @Autowired
    public StorageConfig(OOSCtyunProperties oosCtyunProperties) {
        this.oosCtyunProperties = oosCtyunProperties;
    }

    @Bean
    public AmazonS3 getS3Bean() {
        AmazonS3 oos = new AmazonS3Client(
                new BasicAWSCredentials(
                        oosCtyunProperties.getAccessKeyID(),
                        oosCtyunProperties.getAccessSecretKey()
                )
        );
        oos.setEndpoint(oosCtyunProperties.getEndPoint());
        return oos;
    }








    /* implement for new version of AWS-JAVA
    @Bean
    public AmazonS3 getS3Bean() {
        AmazonS3 s3;

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("d159640577477cf61ee6", "98e1495d8e85f31c6eab31e5e2e98fe7f9a7a953");
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds));

        // SignatureV2
        ClientConfigurationFactory configFactory = new ClientConfigurationFactory();
        ClientConfiguration config = configFactory.getConfig();
        config.setSignerOverride("S3SignerType");
        builder = builder.withClientConfiguration(config);

        String host = "oos.ctyunapi.cn";
        String endpoint = "http://" + host;
        String clientRegion = null;
        if (!ServiceUtils.isS3USStandardEndpoint(endpoint)) {
            clientRegion = AwsHostNameUtils.parseRegion(host,
                    AmazonS3Client.S3_SERVICE_NAME);
        }

        builder = builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                endpoint, clientRegion));

        builder = builder.withPathStyleAccessEnabled(true);
        s3 = builder.build();

        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
        return s3;
    }
    */
}
