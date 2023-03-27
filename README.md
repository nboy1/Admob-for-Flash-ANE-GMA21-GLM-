# Admob-for-Flash-ANE-GMA21-GLM-
Admob for Flash ANE GMA21 (GLM) "adobe air native extension" Android and IOS

Admob ANE for Flash Air
==============================

0. [Admob ANE Description](#admob-ane-description)
1. [Admob ANE For Air Features](#admob-ane-for-air-features)
2. [Quick Start](#quick-start)    
	1.[import Admob ANE ](#1import-admob-ane)   
	2.[Add New AppOpen Ads in your adobe Air App](#2add-new-appopen-ads-in-your-adobe-air-App)  
	3.[Get Banner Size](#3get-banner-size)      
	4.[Add New Anchored adaptive banner in your adobe Air App](#4add-new-anchored-adaptive-banner-in-your-adobe-air-app)    
	5.[Remove Banner](#5remove-banner)     
	6.[Load and Show Interstitial Ads](#6load-and-show-interstitial-ads)    
    7.[Admob Rewarded Video Ads](#7admob-rewarded-video-ads)       
	8.[Admob Rewarded Video Events FullScreenContentCallback](#8admob-rewarded-video-events-fullscreencontentcallback)    
	9.[Child-directed setting (COPPA) (GDPR) (EEA)](#9child-directed-setting-coppa-gdpr-eea)    
	10.[Add test Device](#10add-test-device)    
	11.[Android permission config](#11android-permission-config)    
	12.[IOS permission config](#12ios-permission-config)   
	13.[ANE ID](#13ane-id)  
3. [Version](#version)    
	1.[SDK version](#1sdk-version)   
	2.[Update Date](#2update-date)  
4. [Links](#links)
5. [License](#license)




## Admob ANE Description
this New 2023 Adobe Air Native Extention(ANE) for Admob with latest Admob SDK GMA-21+ for IOS and Android.
this Admob ANE offer easy way to integrat Admob Ads with your AIR Games and Apps  with few lines of code.
compliant with 
You can use it free for your Android Apps and IOS Apps.






## Admob ANE For Air Features

- [x] Support AppOpen Ads (New AppOpen Ads from google Admob)
- [x] Support banner(New Anchored adaptive banners from google Admob)
- [x] Support Intersitial
- [x] Support Rewarded Video
- [x] Support landscape and portrait  and autoOrient
- [x] Support AdRequest targeting methods,such as children target,test mode
- [x] Support Adobe AIR SDK 32 and Air 33 and 50+ SDK
- [x] Support android x64,x86,arm，and 32,64 bit support
- [x] Support IOS arm64 support



## Quick Start
#### 1.import Admob ANE 
Add Admob ANE to Adobe Animate or adobe Flash air project build path , add this line of code to import the ANE in your game scripts
```

    import com.glm.admob.*;


```

#### 2.Add New AppOpen Ads in your adobe Air App 

if you are targeting +12 don't use AppOpen ads it's not compliant with Admob Policy last time i've checked.
to Load and Show AppOpen ads add this line of code.
```

	Admob.Ads().ShowAppOpen("your AppOpen ID");
    

```

#### 3.Get Banner Size

Get Banner Height Size to adjust you game with the Banner Height Size, and to leave a specified place for banner ads to meet new google Admob requirements.

use a var to store the banner Height size "it get auto size based on device dimension".
like in this example:

```
	var adaptiveBannerSize = Admob.Ads().getAdaptiveBannerHeight();
    
```



#### 4.Add New Anchored adaptive banner in your adobe Air App 
add this line of code to Load and Show the Banner.
```
	Admob.Ads().ShowAdaptiveBanner("your banner ID","TOP")
    
```
use word "TOP" or "BOTTOM" for banner position, this Admob ANE offer auto orientation based on your Game orientation.



#### 5.Remove Banner 
to remove a banner use this line of code
```
    Admob.Ads().HideAdaptiveBanner();
```



#### 6.Load and Show Interstitial Ads

to Load Admob Interstitial in you game use this line of code
```
	Admob.Ads().LoadInterstitial("your Interstitial ID");
    
```


to Show Admob Interstitial in you game use this line of code
```
  
	Admob.Ads().ShowInterstitial();
    
```



#### 7.Admob Rewarded Video Ads
to Load Admob Rewarded Video Ads in you game use this line of code
    
```
	Admob.Ads().LoadRewardedVideo("your Rewarded Video ID");

```


to Show Admob Rewarded Video Ad in you game use this line of code
```

	Admob.Ads().ShowRewardedVideo();

```




#### 8.Admob Rewarded Video Events FullScreenContentCallback
to get events of rewarded video ads and then you specify if the user completed the video "reward the user" or video dismissed
use this function to get events of your rewardd video.

```


    public function onVideoEvent(evt:Event):void {
		    if (evt.type == "UserEarnedReward") 
			{  
				 //reward the user
			}
			if (evt.type == "AdShowedFullScreenContent") 
			{  
				 
			}
		    if (evt.type == "AdLoaded") 
			{  
				 
			}
			if (evt.type == "AdFailedToLoad") 
			{  
				 
			}
			if (evt.type == "AdFailedToShowFullScreenContent") 
			{  
				 
			}
		    if (evt.type == "AdDismissedFullScreenContent") 
			{  
				 
			}
		
	}



```



#### 9.Child-directed setting (COPPA) (GDPR) (EEA)

this Ane by default target +12 and compliant with "(COPPA)", "tag for child-directed treatment",
if you want to chage setting based on your game targeting use this line of code


```
    Admob.Ads().TAG_FOR_CHILD_DIRECTED_TREATMENT(true);

```
set it to true or false, true mean your game target child and false mean not targeting child. "it's true by default".



- and same for Users under the age of consent :

```
    Admob.Ads().TAG_FOR_UNDER_AGE_OF_CONSENT(true);

```



- to use Ad content filtering use this code "not configured by default" you have to set it if you want to use it

```
    Admob.Ads().TAG_FOR_MAX_AD_CONTENT_RATING("G");

```
change it to "G" or "PG" or "T" or "MA" based on your wants.
   




#### 10.Add test Device
to add a test device to test your ads on you test device, use this line of code

```
    Admob.Ads().Test_Device_ID("your Test Device ID");

```


#### 11.Android permission config


replace ca-app-pub-3940256099942544~3347511713 with your admob ID


```
<android>
        <manifestAdditions><![CDATA[
			<manifest android:installLocation="auto">
					<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
					<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
					<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
					<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
					<uses-permission android:name="com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE" />
					 <application>
						<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />
					      <activity android:name="com.google.android.gms.ads.AdActivity" android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" android:theme="@android:style/Theme.Translucent"/>
						<receiver android:name="com.google.android.gms.measurement.AppMeasurementReceiver" android:enabled="true" android:exported="false" ></receiver>
					       <receiver android:name="com.google.android.gms.measurement.AppMeasurementInstallReferrerReceiver" android:enabled="true" android:exported="true" android:permission="android.permission.INSTALL_PACKAGES" >
							<intent-filter>
							    <action android:name="com.android.vending.INSTALL_REFERRER" />
							</intent-filter>
					        </receiver>

					        <service android:name="com.google.android.gms.measurement.AppMeasurementService" android:enabled="true" android:exported="false" />
					        <service android:name="com.google.android.gms.measurement.AppMeasurementJobService" android:enabled="true" android:exported="false" android:permission="android.permission.BIND_JOB_SERVICE" />
					        <receiver android:name="com.google.android.gms.measurement.AppMeasurementReceiver" android:enabled="true" android:exported="false" ></receiver>

					        <service android:name="com.google.android.gms.measurement.AppMeasurementService" android:enabled="true" android:exported="false" />
						  <meta-data android:name="com.google.android.gms.ads.APPLICATION_ID" android:value="ca-app-pub-3940256099942544~3347511713"/>
					</application>
			</manifest>

		]]></manifestAdditions>
    </android>
```





#### 12.IOS permission config


replace ca-app-pub-3940256099942544~3347511713 with your admob ID


```
 <iPhone>
        <InfoAdditions><![CDATA[
            <key>MinimumOSVersion</key><string>12.0</string>
            <key>UIPrerenderedIcon</key><true/>
            <key>UIDeviceFamily</key>
            <array><string>1</string><string>2</string></array>
            
     <key>GADApplicationIdentifier</key>
        <string>ca-app-pub-1231791771352771~9696996234</string>
                
                <key>NSAppTransportSecurity</key>
    <dict>
        <key>NSAllowsArbitraryLoads</key>
        <true/>
        <key>NSAllowsArbitraryLoadsForMedia</key>
        <true/>
        <key>NSAllowsArbitraryLoadsInWebContent</key>
        <true/>
    </dict>
            ]]></InfoAdditions>
        <requestedDisplayResolution>high</requestedDisplayResolution>
  
</iPhone>
```


 

#### 13.ANE ID
```
<extensionID>com.glm.admob</extensionID>
```

## version
#### 1.SDK version
```
Version 1.0 use Admob sdk V 21.3.0  
```
  

#### 2.Update Date
```
2023.03.27
```
 


 
## Links

admob http://apps.admob.com    

## License
[Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html)



