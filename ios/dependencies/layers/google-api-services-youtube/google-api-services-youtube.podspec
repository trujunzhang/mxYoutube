Pod::Spec.new do |s|
  s.name     = 'google-api-services-youtube'
  s.version  = '0.1.0'
  s.license  = 'MIT'
  s.summary  = 'google-api-services-youtube aims to be a drop-in replacement for UITabBarController.' 
  s.description = 'google-api-services-youtube aims to be a drop-in replacement of UITabBarController with the intention of letting developers easily customise its appearance. JBTabBar uses .'
  s.homepage = 'http://www.github.com/wanghaogithub720'
  s.author   = { 'Jin Budelmann' => 'jin@bitcrank.com' }
  s.source   = { :git => 'https://github.com/wanghaogithub720/mxYoutube.git', :tag => '0.1.0' }
  s.platform = :ios
  s.source_files = 'Pod/Classes/*.{h,m}'
  s.resources = "Pod/Assets/*/*.*"
  s.requires_arc = true
  s.dependency 'Google-API-Client/YouTube'
  s.dependency 'Google-API-Client/YouTubeAnalytics'
end