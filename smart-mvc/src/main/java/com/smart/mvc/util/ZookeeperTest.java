package com.smart.mvc.util;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;

public class ZookeeperTest {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String connectionInfo = "127.0.0.1:2181";
//        CuratorFramework client =
//                CuratorFrameworkFactory.newClient(
//                        "127.0.0.1:2181",
//                        5000,
//                        3000,
//                        retryPolicy);

        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionInfo)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .namespace("test")
                        .build();
        client.start();
        String root ="/lock-test";
        Stat stat = client.checkExists().forPath(root);
        if(stat == null) {
            client.create().withMode(CreateMode.PERSISTENT).forPath(root);
        }else {
            client.create().withMode(CreateMode.PERSISTENT).forPath(root+"1");
        }
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(root + "/lock_");
        String lockpath = client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(root+"/lock_");
        List<String> strings = client.getChildren().forPath(root);
        String[] node = strings.toArray(new String[strings.size()]);
        Arrays.sort(node);
        if(node.length>0){
            if(!lockpath.equals(root+"/"+node[0])){

            }
        }


    }


}
