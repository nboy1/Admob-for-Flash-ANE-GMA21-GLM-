package com.glm.admob{
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.geom.Rectangle;
	import flash.system.Capabilities;
	import flash.events.Event;
public class Admob extends EventDispatcher{

	private var extensionContext:ExtensionContext=null;
	private static var instance:Admob;
	

	public static const  test_device_id:String="test_device_id";
	public static const  debug_geography_EEA:String="debug_geography_EEA";
	public static const  tag_for_under_age_of_consent:String="tag_for_under_age_of_consent";
	public static const  tag_for_child_directed_treatment:String="tag_for_child_directed_treatment";
	public static const  tag_for_max_ad_content_rating:String="tag_for_max_ad_content_rating";
	
	public static const  showAppOpen:String="showAppOpen";

	public static const  showAdaptiveBanner:String="showAdaptiveBanner";
	public static const  getAdaptiveBannerSize:String="getAdaptiveBannerHeight";
	public static const  hideAdaptiveBanner:String="hideAdaptiveBanner";
	
	
	public static const  loadInterstitial:String="loadInterstitial";
	public static const  showInterstitial:String="showInterstitial";
	
	
	//Rewarded Videos
	public static const  loadRewardedVideo:String="loadRewardedVideo";
	public static const  showRewardedVideo:String="showRewardedVideo";

     

	public static const  message:String="message";

 


	
	public static function Ads():Admob
		{
			if (instance == null)
			{
				instance=new Admob();
				//trace(version);
			}
			return instance;
		}
		public function Admob()
		{
			extensionContext=ExtensionContext.createExtensionContext("com.glm.admob", null);
			if(extensionContext != null)
         {
            extensionContext.addEventListener(StatusEvent.STATUS, the_events);
         }
		}
		
		public function the_events(e:StatusEvent):void 
		{
			if (e.code == "onAdLoaded") 
			{ //extensionContext.call(message,"working");
				dispatchEvent (new Event ("AdLoaded") ); 
			}
			if (e.code == "onAdFailedToLoad") 
			{ //extensionContext.call(message,"working");
				dispatchEvent (new Event ("AdFailedToLoad") ); 
			}
			if (e.code == "onAdShowedFullScreenContent") 
			{ //extensionContext.call(message,"working");
				dispatchEvent (new Event ("AdShowedFullScreenContent") ); 
			}
			if (e.code == "onAdFailedToShowFullScreenContent") 
			{ //extensionContext.call(message,"working");
				dispatchEvent (new Event ("AdFailedToShowFullScreenContent") ); 
			}
			if (e.code == "onAdDismissedFullScreenContent") 
			{ //extensionContext.call(message,"working");
				dispatchEvent (new Event ("AdDismissedFullScreenContent") ); 
			}
			if (e.code == "onUserEarnedReward") 
			{ //extensionContext.call(message,"working");
				dispatchEvent (new Event ("UserEarnedReward") ); 
			}
		} 
 		 public function Message(Msg:String) : void
        {
         
          
            
                extensionContext.call(message,Msg);
            
        
		}
		 
		  /////////////////***Debug Geography EEA***//////////////////
		 public function Test_Device_ID(ID:String):void
		{
			
                extensionContext.call(test_device_id,ID);
            
 			
		}
		
	    /////////////////***Debug Geography EEA***//////////////////
		 public function Debug_Geography_EEA(TEST_UMP:Boolean):void
		{
			
                extensionContext.call(debug_geography_EEA, TEST_UMP);
            
 			
		}
		
		 public function TAG_FOR_UNDER_AGE_OF_CONSENT(Under_Age_Of_Consent:Boolean):void
		{
			
                extensionContext.call(tag_for_under_age_of_consent, Under_Age_Of_Consent);
            
 			
		}
		
		 public function TAG_FOR_CHILD_DIRECTED_TREATMENT(Child_Directed_Treatment:Boolean):void
		{
			
                extensionContext.call(tag_for_child_directed_treatment, Child_Directed_Treatment);
            
 			
		}
		
		 public function TAG_FOR_MAX_AD_CONTENT_RATING(Max_Ad_Content_Rating:String="'G' or 'PG' or 'T' or 'MA'"):void
		{
			
                extensionContext.call(tag_for_child_directed_treatment, Max_Ad_Content_Rating);
            
 			
		}
		/////////////////////////////////////////////////
		
		
				/////////////////***APP OPEN ADS***//////////////////
		public function ShowAppOpen(AppOpenKey:String="App Open ID"):void
		{
			 
                extensionContext.call(showAppOpen, AppOpenKey);
          
 			
		}
		
		/////////////////***ADAPTIVE BANNER***//////////////////
		public function ShowAdaptiveBanner(AdaptiveBannerKey:String,position:String="TOP or BOTTOM"):void
		{
			
                extensionContext.call(showAdaptiveBanner, AdaptiveBannerKey,position);
          
 			
		}
		
		public function getAdaptiveBannerHeight():int
		{
			 
				 
					var AdaptiveBannerHeight:int=extensionContext.call("getAdaptiveBannerHeight") as int;
					
				 
				return AdaptiveBannerHeight;
			 
		}
		public function HideAdaptiveBanner():void
		{
			 
                extensionContext.call(hideAdaptiveBanner);
          
 			
		}
		///////////////////////////////////////////////////////////
		
		
		
		/////////////////***Interstitial***//////////////////
		
		public function LoadInterstitial(InterstitialKey:String):void
		{
			 
                extensionContext.call(loadInterstitial, InterstitialKey);
             
 			
		}
		
		public function ShowInterstitial():void
		{
			
			extensionContext.call(showInterstitial);
			
		}
		
		///////////////////////////////////////////////////////////
		
		
		
		
		/////////////////***Rewarded Video***//////////////////
		
		 public function LoadRewardedVideo(RewardedVideoKey:String):void
		{
			 
                extensionContext.call(loadRewardedVideo, RewardedVideoKey);
            
 			
		}
		
		public function ShowRewardedVideo():void
		{
			
			extensionContext.call(showRewardedVideo);
			
		}
		
 		    ///////////////////////////////////////////////////////
		
		//////////////////////////***nboy1***////////////////////////
		 
		    ///////////////////////////////////////////////////////
		
		

}

}