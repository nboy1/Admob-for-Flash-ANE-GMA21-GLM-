package {
	
	import flash.events.Event;
    import flash.events.EventDispatcher;
import com.glm.admob.*;	
	
	
    public class Ads {
		
		private var admob:Admob; 
		
		  
		// App Open Ad code
		public static function AppOpenAd():void {
            
            Admob.Ads().ShowAppOpen("ca-app-pub-3940256099942544/9257395921");
        }
		//End App Open Ad
		
		//Banner code
        public static function ShowBannerAd():void {
             
            Admob.Ads().ShowAdaptiveBanner("ca-app-pub-3940256099942544/9214589741","BOTTOM");
        }
		public static function HideBannerAd():void {
             
            Admob.Ads().HideAdaptiveBanner();
        }
		//EndBanner
		
                          ////////////////////////////////////////////////////		
		
		//Interstitial code
		public static function LoadInterstitialAd():void {
             
            Admob.Ads().LoadInterstitial("ca-app-pub-3940256099942544/8691691433");
        }
		public static function ShowInterstitialAd():void {
             
            Admob.Ads().ShowInterstitial();
        }
		//End Interstitial
		
		
					      ////////////////////////////////////////////////////		

		
		//Rewarded code
		public static function LoadRewardedAd():void {
            
            Admob.Ads().LoadRewardedVideo("ca-app-pub-3940256099942544/5224354917");
        }
		public static function ShowRewardedAd():void {
            
            Admob.Ads().ShowRewardedVideo();
        }

		//End Rewarded
    }
}
