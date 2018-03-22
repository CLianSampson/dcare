package com.dcare.common.util;

import java.util.ArrayList;
import java.util.List;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.log4j.Logger;
import org.json.JSONException;

public class AppPushUtil {
	private static Logger logger = Logger.getLogger(AppPushUtil.class);
	
	
	public static void push(String deviceToken, String message) throws JSONException, CommunicationException, KeystoreException, InvalidDeviceTokenFormatException{
		int badge = 1; // 图标小红圈的数值
        String sound = "default"; // 铃音
        String msgCertificatePassword = "123";//导出证书时设置的密码
        
        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);

        //java必须要用导出p12文件  php的话是pem文件
        String certificatePath = "/Users/apple/Desktop/p12.p12";
        boolean sendCount = true;

        PushNotificationPayload payload = new PushNotificationPayload();
        payload.addAlert(message); // 消息内容
        payload.addBadge(badge);


        //payload.addCustomAlertBody(msgEX);
        if (null == sound || "".equals(sound)) {
            payload.addSound(sound);
        }

        PushNotificationManager pushManager = new PushNotificationManager();
        // false：表示的是产品测试推送服务 true：表示的是产品发布推送服务
        pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
                certificatePath, msgCertificatePassword, false));
        List<PushedNotification> notifications = new ArrayList<PushedNotification>();
        // 开始推送消息
        if (sendCount) {
            Device device = new BasicDevice();
            device.setToken(deviceToken);
            PushedNotification notification = pushManager.sendNotification(
                    device, payload, true);
            notifications.add(notification);
        } else {
            List<Device> devices = new ArrayList<Device>();
            for (String token : tokens) {
                devices.add(new BasicDevice(token));
            }
            notifications = pushManager.sendNotifications(payload, devices);
        }

        List<PushedNotification> failedNotification = PushedNotification
                .findFailedNotifications(notifications);
        List<PushedNotification> successfulNotification = PushedNotification
                .findSuccessfulNotifications(notifications);
        int failed = failedNotification.size();
        int successful = successfulNotification.size();
        logger.info("ios推送消息==========成功数：" + successful);
        logger.info("ios推送消息失败数：" + failed);
        
        pushManager.stopConnection();
        logger.info("ios消息推送完毕");
	}
	
	
	public static void main(String[] args) throws JSONException, CommunicationException, KeystoreException, InvalidDeviceTokenFormatException {
		
		 	int badge = 1; // 图标小红圈的数值
	        String sound = "default"; // 铃音
	        String msgCertificatePassword = "123";//导出证书时设置的密码
	                            //90fb73e94659a1822caa51ca079734de6a7e60e44f260a1bfd1326bb4648d734
//	        String deviceToken = "be631a1f2348ccb77cdf3bb334d55916ca9fc80f"; //手机设备token号
	        
//	        45124a4cf9f5e272d395f6392456e5ab7185d2ae6e98ba2f8426fe09f60e785b
	        
	        
	        String deviceToken = "c89d6aa170695528a4a0bcc7c56176cfd4607b16b8f62c8aa473e56a19135ae0";
	        
	        String message = "testpushmessagetoiosdevice11111111111";

	        List<String> tokens = new ArrayList<String>();
	        tokens.add(deviceToken);

	        //java必须要用导出p12文件  php的话是pem文件
	        String certificatePath = "/Users/apple/Desktop/p12.p12";
	        boolean sendCount = true;

	        PushNotificationPayload payload = new PushNotificationPayload();
	        payload.addAlert(message); // 消息内容
	        payload.addBadge(badge);


	        //payload.addCustomAlertBody(msgEX);
	        if (null == sound || "".equals(sound)) {
	            payload.addSound(sound);
	        }

	        PushNotificationManager pushManager = new PushNotificationManager();
	        // false：表示的是产品测试推送服务 true：表示的是产品发布推送服务
	        pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
	                certificatePath, msgCertificatePassword, false));
	        List<PushedNotification> notifications = new ArrayList<PushedNotification>();
	        // 开始推送消息
	        if (sendCount) {
	            Device device = new BasicDevice();
	            device.setToken(deviceToken);
	            PushedNotification notification = pushManager.sendNotification(
	                    device, payload, true);
	            notifications.add(notification);
	        } else {
	            List<Device> devices = new ArrayList<Device>();
	            for (String token : tokens) {
	                devices.add(new BasicDevice(token));
	            }
	            notifications = pushManager.sendNotifications(payload, devices);
	        }

	        List<PushedNotification> failedNotification = PushedNotification
	                .findFailedNotifications(notifications);
	        List<PushedNotification> successfulNotification = PushedNotification
	                .findSuccessfulNotifications(notifications);
	        int failed = failedNotification.size();
	        int successful = successfulNotification.size();
	        System.out.println("zsl==========成功数：" + successful);
	        System.out.println("zsl==========失败数：" + failed);
	        
	        
	        PushedNotification failinfo = failedNotification.get(0);
//	        PushedNotification sucessinfo = successfulNotification.get(0);
	        
	        System.out.println(failinfo);
//	        System.out.println(sucessinfo);
	        
	        pushManager.stopConnection();
	        System.out.println("zsl==========消息推送完毕");

	

	}

}
