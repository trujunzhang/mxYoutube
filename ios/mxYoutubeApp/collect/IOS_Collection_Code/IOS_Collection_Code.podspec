
Pod::Spec.new do |s|
  s.name     = 'IOS_Collection_Code'
  s.version  = '0.1.0'
  s.license  = 'MIT'
  s.summary  = 'IOS_Collection_Code aims to be a drop-in replacement for UITabBarController.' 
  s.description = 'IOS_Collection_Code aims to be a drop-in replacement of UITabBarController with the intention of letting developers easily customise its appearance. JBTabBar uses .'
  s.homepage = 'http://www.github.com/wanghaogithub720'
  s.author   = { 'Jin Budelmann' => 'jin@bitcrank.com' }
  s.source   = { :git => 'https://github.com/wanghaogithub720/mxYoutube.git', :tag => '0.1.0' }
  s.platform = :ios
  s.source_files = 'Pod/Classes/*.{h,m}'
  #s.resources = "Pod/Assets/IOS_Collection_Code.bundle"
  s.requires_arc = true
end

